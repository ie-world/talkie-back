package com.example.talkie.controller;

import com.example.talkie.dto.DeleteAccountRequest;
import com.example.talkie.dto.LoginRequest;
import com.example.talkie.dto.SignupRequest;
import com.example.talkie.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        return authService.signup(req);
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<?> checkUsername(@PathVariable String username) {
        return authService.checkUsername(username);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        // Bearer 토큰에서 username 추출하는 로직이 필요합니다
        String username = token.substring(7); // "Bearer " 이후의 문자열
        return authService.getUser(username);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String username = token.substring(7);
        return authService.logout(username);
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteAccount(
            @RequestHeader("Authorization") String token,
            @RequestBody DeleteAccountRequest req) {
        String username = token.substring(7);
        return authService.deleteAccount(username, req);
    }
}