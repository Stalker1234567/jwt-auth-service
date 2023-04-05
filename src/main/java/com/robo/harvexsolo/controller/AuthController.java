package com.robo.harvexsolo.controller;

import com.fitbur.bytebuddy.utility.RandomString;
import com.robo.harvexsolo.dto.AuthRequestDto;
import com.robo.harvexsolo.dto.AuthResponseDto;
import com.robo.harvexsolo.dto.RegistrationRequestDto;
import com.robo.harvexsolo.model.User;
import com.robo.harvexsolo.service.impl.AuthServiceImpl;
import com.robo.harvexsolo.service.impl.UserForgotPasswordServiceImpl;
import com.sun.mail.imap.ResyncData;
import com.sun.mail.imap.Utility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;
    private final JavaMailSender javaMailSender;
    private final UserForgotPasswordServiceImpl userForgotPasswordService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegistrationRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(HttpServletRequest request) {
        String email = request.getParameter("email");
        String token = RandomString.make(30); // This is a test token generation
            userForgotPasswordService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getResyncUIDSet((ResyncData) request) + "/reset-password?token=" + token;

        return "forgot-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(HttpServletRequest request) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        User customer = userForgotPasswordService.getByResetPasswordToken(token);

        return "reset-password";
    }
}
