// entity/FreeTalkSession.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "freetalk_sessions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class FreeTalkSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
