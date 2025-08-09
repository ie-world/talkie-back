// service/FreeTalkService.java
package com.example.talkie.service;

import com.example.talkie.dto.*;
import com.example.talkie.entity.*;
import com.example.talkie.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreeTalkService {
    private final TopicRepository topicRepo;
    private final FreeTalkSessionRepository sessionRepo;
    private final FreeTalkHistoryRepository historyRepo;

    public List<TopicResponse> getTopics(String type) {
        if (!type.equals("subject") && !type.equals("situation")) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        return topicRepo.findByType(type).stream()
                .map(t -> new TopicResponse(t.getId(), t.getName(), t.getType()))
                .collect(Collectors.toList());
    }

    public void selectTopics(String username, List<Long> topicIds) {
        List<Topic> topics = topicRepo.findAllById(topicIds);
        FreeTalkSession session = sessionRepo
                .findByUsernameAndEndTimeIsNull(username)
                .orElse(FreeTalkSession.builder()
                        .username(username)
                        .startTime(LocalDateTime.now())
                        .build());
        session.setActiveTopics(topics);
        sessionRepo.save(session);
    }

    public void changeTopics(String username, List<Long> topicIds) {
        FreeTalkSession session = sessionRepo
                .findByUsernameAndEndTimeIsNull(username)
                .orElseThrow(() -> new IllegalStateException("No active session"));
        session.setActiveTopics(topicRepo.findAllById(topicIds));
        sessionRepo.save(session);
    }

    public void submitSpeech(String username, MultipartFile audioFile) {
        // TODO: STT 호출 -> user_input 추출 + AI API 호출
        String userInput = "[음성 인식 결과 예시]";
        String aiResponse = "[AI 응답 예시]";

        historyRepo.save(FreeTalkHistory.builder()
                .username(username)
                .topicName("TODO: 현재 활성 토픽 이름")
                .userInput(userInput)
                .aiResponse(aiResponse)
                .timestamp(LocalDateTime.now())
                .build());
    }

    public List<FreeTalkHistory> getHistory(String username) {
        return historyRepo.findByUsernameOrderByTimestampAsc(username);
    }

    public void endSession(String username) {
        FreeTalkSession session = sessionRepo
                .findByUsernameAndEndTimeIsNull(username)
                .orElseThrow(() -> new IllegalStateException("No active session"));
        session.setEndTime(LocalDateTime.now());
        sessionRepo.save(session);
    }
}
