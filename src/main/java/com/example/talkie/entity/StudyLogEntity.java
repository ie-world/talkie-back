// src/main/java/com/example/talkie/entity/StudyLogEntity.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "StudyLog")
@Getter @Setter
public class StudyLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_log_id")
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "study_time")
    private Integer studyTime; // 분
}
