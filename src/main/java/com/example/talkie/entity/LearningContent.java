package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="learning_content")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_content_id")
    private Long learningContentId;

    @Column(name = "stage", nullable = false)
    @Enumerated(EnumType.STRING)
    private Stage stage;

    @Column(name = "content_text", nullable = false)
    private String contentText;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "audio_url")
    private String audioUrl;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Stage {
        image, word, sentence
    }
}
