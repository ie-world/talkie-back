// controller/FreeTalkController.java
package com.example.talkie.controller;

import com.example.talkie.dto.*;
import com.example.talkie.entity.FreeTalkHistory;
import com.example.talkie.service.FreeTalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/freetalk")
@RequiredArgsConstructor
public class FreeTalkController {
    private final FreeTalkService freeTalkService;

    @GetMapping("/topics")
    public ResponseEntity<?> getTopics(@RequestParam String type) {
        return ResponseEntity.ok(freeTalkService.getTopics(type));
    }

    @PostMapping("/select-topics")
    public ResponseEntity<?> selectTopics(@RequestHeader("Authorization") String token,
                                          @RequestBody SelectTopicsRequest req) {
        String username = token.substring(7); // TODO: JWT 파싱
        freeTalkService.selectTopics(username, req.getTopicIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-topic")
    public ResponseEntity<?> changeTopic(@RequestHeader("Authorization") String token,
                                         @RequestBody ChangeTopicRequest req) {
        String username = token.substring(7);
        freeTalkService.changeTopics(username, req.getTopicIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitSpeech(@RequestHeader("Authorization") String token,
                                          @RequestParam MultipartFile audioFile) {
        String username = token.substring(7);
        freeTalkService.submitSpeech(username, audioFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<FreeTalkHistory>> getHistory(@RequestHeader("Authorization") String token) {
        String username = token.substring(7);
        return ResponseEntity.ok(freeTalkService.getHistory(username));
    }

    @PostMapping("/end")
    public ResponseEntity<?> endSession(@RequestHeader("Authorization") String token) {
        String username = token.substring(7);
        freeTalkService.endSession(username);
        return ResponseEntity.ok().build();
    }
}
