package com.example.talkie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.talkie.entity.LearningRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LearningRecordRepository extends JpaRepository<LearningRecord, Long> {

    // 특정 userId에 해당하는 learningContentId 목록만 가져오기
    @Query("SELECT lr.learningContentId FROM LearningRecord lr WHERE lr.userId = :userId")
    List<Long> findLearningContentIdsByUserId(@Param("userId") Long userId);

    @Query(value = """
    SELECT lr.* 
    FROM learning_record lr
    JOIN learning_content lc ON lr.learning_content_id = lc.learning_content_id
    WHERE lr.user_id = :userId
      AND lr.success = false
      AND lc.stage = :stage
    LIMIT 1
    """, nativeQuery = true)
    LearningRecord findOneByUserIdAndAndStage(
            @Param("userId") Long userId,
            @Param("stage") String stage
    );
}
