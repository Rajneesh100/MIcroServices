package com.skillshare.login_service.login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    private static class OtpData {
        private final String otp;
        private final LocalDateTime expiryTime;

        public OtpData(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
    @Autowired
    private JavaMailSender mailSender;
    private ConcurrentHashMap<String, OtpData> otpStorage = new ConcurrentHashMap<>();

    // Method to generate OTP
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    // Method to send email
    public void sendOtp(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shareskillotpservice@gmail.com"); // Replace with your email
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        mailSender.send(message);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        otpStorage.put(toEmail, new OtpData(otp, expiryTime));

    }

    public String sendOtpToEmail(String email) {
        // Generate OTP
        String otp = this.generateOtp();

        // Send OTP to the provided email
        this.sendOtp(email, otp);

        return "OTP sent to " + email;
    }
    public boolean validateOtp(String email, String inputOtp) {
        OtpData otpData = otpStorage.get(email);
        if (otpData == null) {
            return false; // No OTP found for the email
        }

        if (otpData.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStorage.remove(email); // Remove expired OTP
            return false; // OTP has expired
        }

        boolean success= otpData.getOtp().equals(inputOtp);
        otpStorage.remove(email); // Remove Used OTP
        return success;

    }



    @Scheduled(fixedRate = 300000) // Run every 5 min
    public void cleanExpiredOtps() {
        otpStorage.entrySet().removeIf(entry ->
                entry.getValue().getExpiryTime().isBefore(LocalDateTime.now())
        );
    }

}
