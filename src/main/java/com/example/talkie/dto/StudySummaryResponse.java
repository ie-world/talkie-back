// src/main/java/com/example/talkie/dto/study/StudySummaryResponse.java
package com.example.talkie.dto;

public record StudySummaryResponse(
        int attendanceDays,     // 출석 일수(날짜 수)
        int todayStudyMinutes,  // 오늘 학습 시간(분)
        int totalStudyMinutes   // 총 학습 시간(분)
) {}
