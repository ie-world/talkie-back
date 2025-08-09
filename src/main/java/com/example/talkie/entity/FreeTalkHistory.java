// entity/FreeTalkHistory.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "freetalk_history")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class FreeTalkHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String topicName;

    @Column(length = 1000)
    private String userInput;

    @Column(length = 2000)
    private String aiResponse;

    private LocalDateTime timestamp;
}
