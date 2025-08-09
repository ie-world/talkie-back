// src/main/java/com/example/talkie/util/StageText.java
package com.example.talkie.util;

public final class StageText {
    private StageText(){}

    public static String title(String stage) {
        return switch (stage) {
            case "image" -> "그림을 말해볼까요?";
            case "word" -> "단어를 말해볼까요?";
            case "sentence" -> "문장을 말해볼까요?";
            default -> "학습을 시작해볼까요?";
        };
    }

    public static String subtitle(String stage, Double progressRatio, Long days) {
        if (days != null) {
            if (days >= 7) return "일주일 넘게 쉬었어요. 가볍게 재시작!";
            if (days >= 2) return "최근 2일 쉬었어요";
        }
        if (progressRatio == null) return null;
        if (progressRatio == 0) return "처음부터 차근차근 시작!";
        if (progressRatio < 0.3) return "오늘은 새로운 내용을 익혀봐요";
        if (progressRatio < 0.7) return "좋아요! 계속 이어가요";
        return "거의 다 왔어요. 마무리해볼까요?";
    }
}
