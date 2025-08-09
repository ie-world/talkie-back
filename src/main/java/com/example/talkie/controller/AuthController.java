// com/example/talkie/controller/AuthController.java
package com.example.talkie.controller;

import com.example.talkie.dto.DeleteAccountRequest;
import com.example.talkie.dto.LoginRequest;
import com.example.talkie.dto.SignupRequest;
import com.example.talkie.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest req) {
        return authService.signup(req);
    }

    // /api/auth/check-username?username=abc
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsernameByQuery(@RequestParam String username) {
        return authService.checkUsername(username);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }

    // 로그인 필요
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return authService.me(username);
    }

    // 토큰 블랙리스트 등록
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        return authService.logout(token);
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteAccount(@RequestBody @Valid DeleteAccountRequest req) {
        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return authService.deleteAccount(username, req);
    }
}
