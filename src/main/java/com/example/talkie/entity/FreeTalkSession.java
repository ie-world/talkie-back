// entity/FreeTalkSession.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "free_talk_session")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class FreeTalkSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeTalkSessionId;

    private String username; // 사용자 식별

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToMany
    @JoinTable(
            name = "session_topics",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> activeTopics;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
