package com.example.talkie.service;

import com.example.talkie.entity.User;
import com.example.talkie.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.talkie.entity.LearningContent;
import com.example.talkie.entity.LearningRecord;

import com.example.talkie.repository.LearningContentRepository;
import com.example.talkie.repository.LearningRecordRepository;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LearningService {
    private final UserRepository userRepository;
    private final LearningContentRepository learningContentRepository;
    private final LearningRecordRepository learningRecordRepository;

    private final CommonService commonService;
    private final ClovaService clovaService;

    public ResponseEntity<?> startLearning(String username, String  stage) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "사용자를 찾을 수 없습니다."));
        }
        User user = userOpt.get();

        // 유저가 이전에 학습한 콘텐츠
        List<Long> excludedIds = learningRecordRepository.findLearningContentIdsByUserId(user.getUserId());

        // 제외하고 랜덤 10개
        List<LearningContent> learningContents = getRandomContentsByStage(excludedIds, stage);

        // 유저의 content record로 저장
        List<LearningRecord> records = learningContents.stream()
                .map(c -> {
                    LearningRecord lr = new LearningRecord();
                    lr.setUserId(user.getUserId());
                    lr.setLearningContentId(c.getLearningContentId()); // 또는 getLearningContentId() 필드명에 맞게
                    return lr;
                })
                .toList();

        learningRecordRepository.saveAll(records);

        return getNextLearningContent(username, stage);
    }

    public ResponseEntity<?> getNextLearningContent(String username, String stage) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "사용자를 찾을 수 없습니다."));
        }
        User user = userOpt.get();

        LearningRecord learningRecord = learningRecordRepository.findOneByUserIdAndAndStage(user.getUserId(), stage);
        LearningContent learningContent = learningContentRepository.findById(learningRecord.getLearningContentId()).get();

        return ResponseEntity.ok(learningContent);
    }

    public ResponseEntity<?> submitLearningRecord(Long recordId, MultipartFile audioFile) throws IOException {
        // 녹음 파일 저장
        String audioPath = commonService.saveAudioFile(audioFile);

        LearningRecord learningRecord = learningRecordRepository.findById(recordId).get();
        LearningContent learningContent = learningContentRepository.findById(learningRecord.getLearningContentId()).get();

        // clova speech api로 녹음 여부 판단
        ResponseEntity<String> sttResponse = clovaService.speechToText(audioPath, learningContent.getContentText());
        System.out.println("sttResponse:"+ sttResponse);
        HttpStatusCode statusCode = sttResponse.getStatusCode();
        if (statusCode.value() == 400) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("재녹음을 해주세요");
        } else {
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> sttResultMap =  objectMapper.readValue(sttResponse.getBody(), Map.class);

            sttResultMap.put("target_text", learningContent.getContentText());
            sttResultMap.put("result_text", sttResultMap.get("text"));

            // ai로 전달
            ResponseEntity<String> feedbackResponse = clovaService.getFeedback(sttResultMap);

            Map<String, Object> feedbackResultMap =  objectMapper.readValue(feedbackResponse.getBody(), Map.class);

            // 파일 주소 및 피드백 등 저장
            learningRecord.setSuccess(true);
            learningRecord.setAudioUrl(audioPath);
            learningRecord.setFeedbackComment((String) feedbackResultMap.get("feedback_text"));
            learningRecord.setRecordedAt(LocalDateTime.now());
            learningRecordRepository.save(learningRecord);

            // 반환
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("feedback", (String)(feedbackResultMap.get("feedback_text")));

            return ResponseEntity.ok(responseMap);
        }
    }

    private List<LearningContent> getRandomContentsByStage(List<Long> excludedIds, String stage) {
        int limit = 10;

        if (excludedIds == null || excludedIds.isEmpty()) {
            // 제외 조건 없이 stage만 필터
            return learningContentRepository.findRandomContentsByStage(stage, limit);
        } else {
            // 제외 조건 적용
            return learningContentRepository.findRandomContentsExcludingByStage(excludedIds, stage, limit);
        }
    }
}