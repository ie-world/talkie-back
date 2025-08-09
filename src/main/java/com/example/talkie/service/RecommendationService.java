// src/main/java/com/example/talkie/service/RecommendationService.java
package com.example.talkie.service;

import com.example.talkie.dto.RecommendResponse;
import com.example.talkie.entity.LearningContent;
import com.example.talkie.repository.LearningContentRepository;
import com.example.talkie.repository.LearningRecordRepository;
import com.example.talkie.repository.UserRepository;
import com.example.talkie.util.StageText;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final LearningContentRepository contentRepo;
    private final LearningRecordRepository recordRepo;
    private final UserRepository userRepo;

    private static final List<String> STAGES = Arrays.asList("image", "word", "sentence");

    // 내부 전용 클래스(외부 DTO와 이름 충돌 방지)
    private static class Stat {
        final String stage;
        final int learned;
        final int total;
        final double ratio;      // learned/total
        final double weight;     // 추천 가중치
        final Long days;         // 마지막 학습 이후 일수(null 가능)
        final LearningContent next; // 다음 학습 후보(없으면 null)

        Stat(String stage, int learned, int total, double ratio, double weight, Long days, LearningContent next) {
            this.stage = stage;
            this.learned = learned;
            this.total = total;
            this.ratio = ratio;
            this.weight = weight;
            this.days = days;
            this.next = next;
        }
    }

    public RecommendResponse recommend(String username) {
        var user = userRepo.findByUsername(username).orElseThrow();
        int userId = Math.toIntExact(user.getUserId());

        List<Stat> stats = new ArrayList<>();

        for (String stage : STAGES) {
            int total = Math.toIntExact(contentRepo.countByStage(stage));
            int learned = Math.toIntExact(recordRepo.countDone(userId, stage));
            double ratio = (total == 0) ? 0.0 : (double) learned / (double) total;

            // freshness 보정 (마지막 학습 시각 → 일수)
            Long days = null;
            double bonus = 0.0;
            var last = recordRepo.lastStudiedAt(userId, stage);
            if (last != null) {
                long d = Duration.between(last.atZone(ZoneId.systemDefault()).toInstant(), Instant.now()).toDays();
                days = d;
                if (d >= 7) bonus += 25;
                else if (d >= 2) bonus += 10;
            } else {
                bonus += 15; // 한 번도 안 했으면 가산
            }

            double base = Math.max(5, 100 * (1 - ratio));
            double weight = base + bonus;

            // 다음 학습 후보(미완료 1개, 없으면 랜덤 복습 1개)
            var nextList = contentRepo.findFirstUnlearned(userId, stage, PageRequest.of(0, 1));
            LearningContent next = nextList.isEmpty()
                    ? contentRepo.pickAny(stage, PageRequest.of(0, 1)).stream().findFirst().orElse(null)
                    : nextList.get(0);

            stats.add(new Stat(stage, learned, total, ratio, weight, days, next));
        }

        // 가중 랜덤으로 추천 1개 선택
        Stat picked = weightedPick(stats);

        var recommended = toCard(picked);
        var alternatives = stats.stream()
                .filter(s -> !s.stage.equals(picked.stage))
                .map(this::toCard)
                .toList();

        return new RecommendResponse(recommended, alternatives);
    }

    private Stat weightedPick(List<Stat> list) {
        double sum = list.stream().mapToDouble(s -> s.weight).sum();
        double r = Math.random() * sum;
        double acc = 0;
        for (var s : list) {
            acc += s.weight;
            if (r <= acc) return s;
        }
        return list.get(list.size() - 1);
    }

    private RecommendResponse.Card toCard(Stat s) {
        Long nextId = (s.next == null) ? null : s.next.getLearningContentId();
        String nextText = (s.next == null) ? null : s.next.getContentText();
        String nextImg = (s.next == null) ? null : s.next.getImageUrl();
        String title = StageText.title(s.stage);
        String sub = StageText.subtitle(s.stage, s.ratio, s.days);
        return new RecommendResponse.Card(
                s.stage,
                round2(s.ratio),     // progressRatio
                s.learned,           // learnedCount
                s.total,             // totalCount
                nextId,
                nextText,
                nextImg,
                title,
                sub
        );
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
