package com.example.talkie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stat {
    private String stage;         // "word", "image", "sentence"
    private double progressRatio; // 0~1 비율
    private int learnedCount;     // 완료 개수
    private int totalCount;       // 전체 개수
    private Long nextContentId;   // 다음 학습할 콘텐츠 ID
    private String nextPreviewText; // 단어나 문장 미리보기
    private String nextImageUrl;  // 이미지 URL (이미지 학습일 때만)
    private String title;         // 추천 카드 타이틀
    private String subTitle;      // 부제 (필요 시)
}
