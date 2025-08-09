// entity/FreeTalkHistory.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "free_talk_history")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class FreeTalkHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeTalkHistoryId;

    private String username;
    private String topicName;

    @Column(length = 1000)
    private String userInput;

    @Column(length = 2000)
    private String aiResponse;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;}
