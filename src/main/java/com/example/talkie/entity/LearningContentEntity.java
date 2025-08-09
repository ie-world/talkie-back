// src/main/java/com/example/talkie/entity/LearningContentEntity.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LearningContent")
@Getter @Setter
public class LearningContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learing_content_id") // 스키마 오타 그대로
    private Integer id;

    @Column(name = "stage", nullable = false) // ENUM('image','word','sentence')
    private String stage;

    @Column(name = "content_text")
    private String contentText;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "audio_url", nullable = false)
    private String audioUrl;
}
