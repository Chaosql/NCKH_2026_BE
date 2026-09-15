package org.hit.NCKH.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.NCKH.constant.ErrorMessage;
import org.hit.NCKH.domain.dto.request.*;
import org.hit.NCKH.domain.dto.response.CommonResponseDto;
import org.hit.NCKH.domain.dto.response.LoginResponseDto;
import org.hit.NCKH.domain.dto.response.TokenRefreshResponseDto;
import org.hit.NCKH.domain.enums.Role;
import org.hit.NCKH.domain.entity.User;
import org.hit.NCKH.exception.InternalServerException;
import org.hit.NCKH.exception.InvalidException;
import org.hit.NCKH.exception.NotFoundException;
import org.hit.NCKH.exception.UnauthorizedException;
import org.hit.NCKH.repository.UserRepository;
import org.hit.NCKH.security.UserPrincipal;
import org.hit.NCKH.security.jwt.JwtTokenProvider;
import org.hit.NCKH.service.AuthService;
import org.hit.NCKH.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final OtpService otpService;
  private final StringRedisTemplate redisTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public LoginResponseDto login(LoginRequestDto request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
      String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);
      return new LoginResponseDto(accessToken, refreshToken, userPrincipal.getId(), authentication.getAuthorities());
    } catch (InternalAuthenticationServiceException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME);
    } catch (BadCredentialsException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
    }
  }

  @Override
  public TokenRefreshResponseDto refresh(TokenRefreshRequestDto request) {
    return null;
  }

  @Override
  public CommonResponseDto logout(HttpServletRequest request) {
    return null;
  }

  @Override
  public void register(UserCreateDto request) {
    if (!request.getPassword().equals(request.getConfirmPassword())) {
      throw new InvalidException("Password confirmation does not match.");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new InvalidException("Email already exists.");
    }
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new InvalidException("Username already exists.");
    }

    try {
      String userJson = objectMapper.writeValueAsString(request);
      String redisKey = "TEMP_USER:" + request.getEmail();
      redisTemplate.opsForValue().set(redisKey, userJson, 5, TimeUnit.MINUTES);
    } catch (Exception e) {
      log.error("Failed to register user to Redis", e);
      throw new InternalServerException("Internal server error.");
    }

    String otpCode = otpService.generateOtp(request.getEmail());
    otpService.sendOtp(request.getEmail(), otpCode);
  }

  @Override
  public void verifyRegisterOtp(VerifyOtpRequestDto request) {
    boolean isValid = otpService.validateOtp(request.getEmail(), request.getOtpCode());
    if (!isValid) {
      throw new InvalidException("OTP is incorrect or expired.");
    }
    String redisKey = "TEMP_USER:" + request.getEmail();
    String userJson = redisTemplate.opsForValue().get(redisKey);

    if (userJson == null) {
      throw new InvalidException("Registration session expired.");
    }

    try {
      UserCreateDto userDto = objectMapper.readValue(userJson, UserCreateDto.class);
      if (userRepository.existsByEmail(userDto.getEmail())) {
        throw new InvalidException("Email already exists.");
      }

      Role userRole = Role.USER;

      User newUser = User.builder()
              .username(userDto.getUsername())
              .email(userDto.getEmail())
              .password(passwordEncoder.encode(userDto.getPassword()))
              .role(userRole)
              .build();

      userRepository.save(newUser);
      redisTemplate.delete(redisKey);

    } catch (Exception e) {
      log.error("Failed to create user on verify OTP", e);
      throw new InternalServerException("Internal server error.");
    }
  }

  @Override
  public void forgotPasswordSendOtp(SendOtpRequestDto request) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new NotFoundException("Email not found."));

    String otpCode = otpService.generateOtp(request.getEmail());
    otpService.sendOtp(request.getEmail(), otpCode);
  }

  @Override
  public void verifyForgotPasswordOtp(VerifyOtpRequestDto request) {
    boolean isValid = otpService.validateOtp(request.getEmail(), request.getOtpCode());
    if (!isValid) {
      throw new InvalidException("OTP is incorrect or expired.");
    }
    String resetTicketKey = "RESET_TICKET:" + request.getEmail();
    redisTemplate.opsForValue().set(resetTicketKey, "VALID", 5, TimeUnit.MINUTES);
  }

  @Override
  public void resetPassword(ResetPasswordRequestDto request) {
    String resetTicketKey = "RESET_TICKET:" + request.getEmail();
    if (!Boolean.TRUE.equals(redisTemplate.hasKey(resetTicketKey))) {
      throw new UnauthorizedException("Reset session expired. Please request OTP again.");
    }

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new NotFoundException("Email not found."));

    if (!request.getNewPassword().equals(request.getConfirmPassword())) {
      throw new InvalidException("Password confirmation does not match.");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);
    redisTemplate.delete(resetTicketKey);
  }

}
