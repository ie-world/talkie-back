package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="LearningRecord")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_record_id")
    private Long learningRecordId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "learning_content_id", nullable = false)
    private Long learningContentId;

    @Column(name = "success")
    private Boolean success = false;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "accuracy")
    private Integer accuracy;

    @Column(name = "speed")
    private String speed;

    @Column(name = "pause")
    private String pause;

    @Column(name = "feedback_comment")
    private String feedbackComment;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
