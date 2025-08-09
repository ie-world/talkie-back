// src/main/java/com/example/talkie/repository/LearningRecordRepository.java
package com.example.talkie.repository;

import com.example.talkie.entity.LearningRecordEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LearningRecordRepository extends JpaRepository<LearningRecordEntity, Integer> {

    // 완료( success=true ) 개수
    @Query(value = """
      select count(*) from LearningRecord lr
      join LearningContent c on c.learing_content_id = lr.learning_content_id
      where lr.user_id=:userId and c.stage=:stage and lr.success=1
      """, nativeQuery = true)
    long countDone(@Param("userId") int userId, @Param("stage") String stage);

    // 마지막 학습 시각
    @Query(value = """
      select max(lr.recorded_at) from LearningRecord lr
      join LearningContent c on c.learing_content_id = lr.learning_content_id
      where lr.user_id=:userId and c.stage=:stage
      """, nativeQuery = true)
    LocalDateTime lastStudiedAt(@Param("userId") int userId, @Param("stage") String stage);
}
