package com.supermarketmanagement.api.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarketmanagement.api.Model.Custom.Login.ForgotPasswordRequest;
import com.supermarketmanagement.api.Model.Custom.Login.LoginRequestDto;
import com.supermarketmanagement.api.Model.Custom.Login.ResetPasswordRequest;
import com.supermarketmanagement.api.ServiceImp.AuthServiceImp;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthServiceImp authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.verifyOtpAndResetPassword(request));
    }
}
