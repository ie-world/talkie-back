package com.example.talkie.controller;

import com.example.talkie.dto.ProfileResponse;
import com.example.talkie.service.UserProfileService;
import com.example.talkie.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final JwtUtil jwtUtil;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(@RequestHeader("Authorization") String bearer) {
        String username = jwtUtil.getUsername(bearer); // "Bearer ..." 처리
        return ResponseEntity.ok(userProfileService.getProfile(username));
    }
}
