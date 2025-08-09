// com/example/talkie/service/AuthService.java
package com.example.talkie.service;

import com.example.talkie.dto.DeleteAccountRequest;
import com.example.talkie.dto.LoginRequest;
import com.example.talkie.dto.SignupRequest;
import com.example.talkie.entity.RevokedToken;
import com.example.talkie.entity.User;
import com.example.talkie.repository.RevokedTokenRepository;
import com.example.talkie.repository.UserRepository;
import com.example.talkie.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final RevokedTokenRepository revokedTokenRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity<?> signup(SignupRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "이미 존재하는 사용자명입니다."));
        }
        var user = User.builder()
                .username(req.getUsername())
                // 테스트용: 해싱 없이 평문 저장(운영 금지)
                .password(req.getPassword())
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "회원가입이 완료되었습니다."));
    }

    public ResponseEntity<?> checkUsername(String username) {
        boolean exists = userRepository.existsByUsername(username);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    public ResponseEntity<?> login(LoginRequest req) {
        var userOpt = userRepository.findByUsername(req.getUsername());
        if (userOpt.isEmpty()) {
            return unauthorized();
        }
        var user = userOpt.get();

        // 평문 비교
        if (!Objects.equals(req.getPassword(), user.getPassword())) {
            return unauthorized();
        }

        String token = jwtUtil.createToken(user.getUsername());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> me(String username) {
        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "사용자를 찾을 수 없습니다."));
        }
        var user = userOpt.get();
        return ResponseEntity.ok(Map.of("username", user.getUsername()));
    }

    public ResponseEntity<?> logout(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer "))
            return ResponseEntity.badRequest().body(Map.of("message","Invalid token"));

        String jti = jwtUtil.getJti(bearerToken);
        long exp = jwtUtil.getExpiry(bearerToken).getEpochSecond();
        if (!revokedTokenRepository.existsByJti(jti)) {
            revokedTokenRepository.save(new RevokedToken(jti, exp));
        }
        return ResponseEntity.ok(Map.of("message", "로그아웃되었습니다."));
    }

    public ResponseEntity<?> deleteAccount(String username, DeleteAccountRequest req) {
        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "사용자를 찾을 수 없습니다."));
        }
        var user = userOpt.get();
        // 평문 비교
        if (!Objects.equals(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "비밀번호가 일치하지 않습니다."));
        }
        userRepository.delete(user);
        return ResponseEntity.ok(Map.of("message", "계정이 삭제되었습니다."));
    }

    private ResponseEntity<Map<String,String>> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "아이디 또는 비밀번호가 잘못되었습니다."));
    }
}
