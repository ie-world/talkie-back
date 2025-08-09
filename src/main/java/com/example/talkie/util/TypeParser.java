package com.example.talkie.util;

public final class TypeParser {
    private TypeParser() {}

    public static ConversationType parseType(String raw) {
        if (raw == null) throw new IllegalArgumentException("type 파라미터가 필요합니다.");
        String v = raw.trim().toLowerCase();
        switch (v) {
            case "subject": return ConversationType.subject;
            case "situation": return ConversationType.situation;
            default: throw new IllegalArgumentException("type은 subject 또는 situation 이어야 합니다.");
        }
    }
}
