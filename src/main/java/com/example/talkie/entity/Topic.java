// entity/Topic.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topics")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 예: 음식, 운동 ...

    @Column(nullable = false)
    private String type; // "subject" 또는 "situation"
}
