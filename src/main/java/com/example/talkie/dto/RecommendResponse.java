// src/main/java/com/example/talkie/dto/recommend/RecommendResponse.java
package com.example.talkie.dto;

import java.util.List;

public record RecommendResponse(Card recommended, List<Card> alternatives) {
    public record Card(
            String stage,           // image | word | sentence
            double progressRatio,   // 0.0 ~ 1.0
            int learnedCount,       // 완료 개수
            int totalCount,         // 전체 개수
            Integer nextContentId,  // 다음 학습 컨텐츠 id (없으면 null)
            String nextPreviewText, // 단어/문장 텍스트, 그림이면 null
            String nextImageUrl,    // 그림이면 url, 아니면 null
            String title,           // 카드 타이틀
            String subTitle         // 보조 문구(없으면 null)
    ) {}
}
