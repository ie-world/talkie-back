package com.example.talkie.dto;

import java.time.LocalDate;

public record ProfileResponse(
        String username,     // 회원 ID
        LocalDate joinedDate // 가입 날짜 (LocalDate)
) {}
