// src/main/java/com/example/talkie/service/StudySummaryService.java
package com.example.talkie.service;

import com.example.talkie.dto.StudySummaryResponse;
import com.example.talkie.repository.StudyLogRepository;
import com.example.talkie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StudySummaryService {
    private final StudyLogRepository studyRepo;
    private final UserRepository userRepo;

    public StudySummaryResponse summary(String username) {
        var user = userRepo.findByUsername(username).orElseThrow();
        int userId = Math.toIntExact(user.getUserId());
        int days = studyRepo.countAttendanceDays(userId);
        int today = studyRepo.todayMinutes(userId, LocalDate.now());
        int total = studyRepo.totalMinutes(userId);
        return new StudySummaryResponse(days, today, total);
    }
}
