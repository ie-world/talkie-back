// src/main/java/com/example/talkie/controller/MainController.java
package com.example.talkie.controller;

import com.example.talkie.dto.RecommendResponse;
import com.example.talkie.dto.StudySummaryResponse;
import com.example.talkie.service.RecommendationService;
import com.example.talkie.service.StudySummaryService;
import com.example.talkie.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    private final RecommendationService recommendationService;
    private final StudySummaryService studySummaryService;
    private final JwtUtil jwtUtil;

    // 1) 추천 카드 (진행률 포함)
    @GetMapping("/learn/recommend")
    public ResponseEntity<RecommendResponse> recommend(@RequestHeader("Authorization") String bearer) {
        String username = jwtUtil.getUsername(bearer); // "Bearer ..." 지원
        return ResponseEntity.ok(recommendationService.recommend(username));
    }

    // 2) 학습 기록 요약
    @GetMapping("/study-log/summary")
    public ResponseEntity<StudySummaryResponse> summary(@RequestHeader("Authorization") String bearer) {
        String username = jwtUtil.getUsername(bearer);
        return ResponseEntity.ok(studySummaryService.summary(username));
    }
}
