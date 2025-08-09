package com.example.talkie.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.example.talkie.service.LearningService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/learning")
@RequiredArgsConstructor
public class LearningController {
    private final LearningService learningService;

    @PostMapping("/{stage}/start")
    public ResponseEntity<?> startLearning(@RequestHeader("Authorization") String token, @PathVariable String stage) {
        String username = token.substring(7);
        return learningService.startLearning(username, stage);
    }

    @GetMapping("/{stage}/next")
    public ResponseEntity<?> nextLearning(@RequestHeader("Authorization") String token, @PathVariable String stage) {
        String username = token.substring(7);
        return learningService.getNextLearningContent(username, stage);

    }

    @PatchMapping(value = "/record/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitLearningRecord(
            @PathVariable Long id,
            @RequestPart(value = "audio") MultipartFile audioFile
    ) throws IOException {
        return learningService.submitLearningRecord(id, audioFile);
    }
}