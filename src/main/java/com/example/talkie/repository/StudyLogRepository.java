// src/main/java/com/example/talkie/repository/StudyLogRepository.java
package com.example.talkie.repository;

import com.example.talkie.entity.StudyLogEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface StudyLogRepository extends JpaRepository<StudyLogEntity, Integer> {

    @Query(value = "select count(distinct date) from StudyLog where user_id=:userId", nativeQuery = true)
    int countAttendanceDays(@Param("userId") int userId);

    @Query(value = "select coalesce(sum(study_time),0) from StudyLog where user_id=:userId and date=:today", nativeQuery = true)
    Integer todayMinutes(@Param("userId") int userId, @Param("today") LocalDate today);

    @Query(value = "select coalesce(sum(study_time),0) from StudyLog where user_id=:userId", nativeQuery = true)
    Integer totalMinutes(@Param("userId") int userId);
}
