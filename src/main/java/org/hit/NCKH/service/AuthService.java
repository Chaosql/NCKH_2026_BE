package org.hit.NCKH.service;

import org.hit.NCKH.domain.dto.request.LoginRequestDto;
import org.hit.NCKH.domain.dto.request.TokenRefreshRequestDto;
import org.hit.NCKH.domain.dto.response.CommonResponseDto;
import org.hit.NCKH.domain.dto.response.LoginResponseDto;
import org.hit.NCKH.domain.dto.response.TokenRefreshResponseDto;
import org.hit.NCKH.domain.dto.request.UserCreateDto;
import org.hit.NCKH.domain.dto.request.VerifyOtpRequestDto;
import org.hit.NCKH.domain.dto.request.SendOtpRequestDto;
import org.hit.NCKH.domain.dto.request.ResetPasswordRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

  LoginResponseDto login(LoginRequestDto request);

  TokenRefreshResponseDto refresh(TokenRefreshRequestDto request);

  CommonResponseDto logout(HttpServletRequest request);

  void register(UserCreateDto request);

  void verifyRegisterOtp(VerifyOtpRequestDto request);

  void forgotPasswordSendOtp(SendOtpRequestDto request);

  void verifyForgotPasswordOtp(VerifyOtpRequestDto request);

  void resetPassword(ResetPasswordRequestDto request);

}
