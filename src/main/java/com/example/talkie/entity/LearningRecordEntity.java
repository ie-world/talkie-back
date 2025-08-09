// src/main/java/com/example/talkie/entity/LearningRecordEntity.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "LearningRecord")
@Getter @Setter
public class LearningRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_record_id")
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "learning_content_id", nullable = false)
    private Integer learningContentId;

    @Column(name = "success", nullable = false)
    private Boolean success;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;
}
