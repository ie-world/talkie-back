package com.example.talkie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.talkie.entity.LearningContent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LearningContentRepository extends JpaRepository<LearningContent, Long> {
    // 리스트로 받은 id 제외하고 지정된 개수만큼 가져오기 - 제외 조건이 있는 버전
    @Query(value = """
        SELECT * 
        FROM learning_content 
        WHERE stage = :stage
          AND learning_content_id NOT IN :excludedIds
        ORDER BY RAND()
        LIMIT :limit
        """, nativeQuery = true)
    List<LearningContent> findRandomContentsExcludingByStage(
            @Param("excludedIds") List<Long> excludedIds,
            @Param("stage") String stage,
            @Param("limit") int limit
    );

    // 리스트로 받은 id 제외하고 지정된 개수만큼 가져오기 - 제외 조건이 없는 버전
    @Query(value = """
    SELECT * 
    FROM learning_content 
    WHERE stage = :stage
    ORDER BY RAND()
    LIMIT :limit
    """, nativeQuery = true)
    List<LearningContent> findRandomContentsByStage(
            @Param("stage") String stage,
            @Param("limit") int limit
    );
}