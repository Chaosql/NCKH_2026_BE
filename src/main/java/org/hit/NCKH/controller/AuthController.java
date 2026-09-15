package org.hit.NCKH.controller;

import org.hit.NCKH.base.RestApiV1;
import org.hit.NCKH.base.VsResponseUtil;
import org.hit.NCKH.constant.UrlConstant;
import org.hit.NCKH.domain.dto.request.*;
import org.hit.NCKH.domain.dto.response.CommonResponseDto;
import org.hit.NCKH.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestApiV1
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "API Login")
  @PostMapping(UrlConstant.Auth.LOGIN)
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
    return VsResponseUtil.success(authService.login(request));
  }

  @Operation(summary = "API Register")
  @PostMapping(UrlConstant.Auth.REGISTER)
  public ResponseEntity<?> register(@Valid @RequestBody UserCreateDto request) {
    authService.register(request);
    return VsResponseUtil.success(new CommonResponseDto(true, "OTP sent to email. Please verify."));
  }

  @Operation(summary = "API Verify Register OTP")
  @PostMapping(UrlConstant.Auth.VERIFY_REGISTER_OTP)
  public ResponseEntity<?> verifyRegisterOtp(@Valid @RequestBody VerifyOtpRequestDto request) {
    authService.verifyRegisterOtp(request);
    return VsResponseUtil.success(new CommonResponseDto(true, "Registration verified successfully."));
  }

  @Operation(summary = "API Forgot Password Send OTP")
  @PostMapping(UrlConstant.Auth.FORGOT_PASSWORD_SEND_OTP)
  public ResponseEntity<?> forgotPasswordSendOtp(@Valid @RequestBody SendOtpRequestDto request) {
    authService.forgotPasswordSendOtp(request);
    return VsResponseUtil.success(new CommonResponseDto(true, "OTP sent to email."));
  }

  @Operation(summary = "API Verify Forgot Password OTP")
  @PostMapping(UrlConstant.Auth.FORGOT_PASSWORD_VERIFY_OTP)
  public ResponseEntity<?> verifyForgotPasswordOtp(@Valid @RequestBody VerifyOtpRequestDto request) {
    authService.verifyForgotPasswordOtp(request);
    return VsResponseUtil.success(new CommonResponseDto(true, "OTP verified successfully."));
  }

  @Operation(summary = "API Reset Password")
  @PostMapping(UrlConstant.Auth.FORGOT_PASSWORD_RESET)
  public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDto request) {
    authService.resetPassword(request);
    return VsResponseUtil.success(new CommonResponseDto(true, "Password reset successfully."));
  }


}
