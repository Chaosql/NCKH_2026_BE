package org.hit.NCKH.service;

public interface OtpService {
    String generateOtp(String email);
    void sendOtp(String email, String otpCode);
    boolean validateOtp(String email, String otpCode);
}
