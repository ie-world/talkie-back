package com.example.talkie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.talkie.entity.LearningContent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LearningContentRepository extends JpaRepository<LearningContent, Integer> {

    @Query(value = "select count(*) from LearningContent where stage = :stage", nativeQuery = true)
    long countByStage(@Param("stage") String stage);

    // 아직 성공(true)하지 않은 콘텐츠 1개
    @Query(value = """
      select * from LearningContent c
       where c.stage = :stage
         and c.learing_content_id not in (
            select lr.learning_content_id
              from LearningRecord lr
              join LearningContent cc on cc.learing_content_id = lr.learning_content_id
             where lr.user_id = :userId
               and lr.success = 1
               and cc.stage = :stage
         )
       order by c.learing_content_id asc
      """, nativeQuery = true)
    List<LearningContentEntity> findFirstUnlearned(@Param("userId") int userId,
                                                   @Param("stage") String stage,
                                                   Pageable pageable);

    // 모두 완료면 랜덤 복습 1개
    @Query(value = "select * from LearningContent where stage=:stage order by rand()", nativeQuery = true)
    List<LearningContentEntity> pickAny(@Param("stage") String stage, Pageable pageable);
}
=======
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
    
    @Query(value = "select count(*) from LearningContent where stage = :stage", nativeQuery = true)
    long countByStage(@Param("stage") String stage);

    // 아직 성공(true)하지 않은 콘텐츠 1개
    @Query(value = """
      select * from LearningContent c
       where c.stage = :stage
         and c.learing_content_id not in (
            select lr.learning_content_id
              from LearningRecord lr
              join LearningContent cc on cc.learing_content_id = lr.learning_content_id
             where lr.user_id = :userId
               and lr.success = 1
               and cc.stage = :stage
         )
       order by c.learing_content_id asc
      """, nativeQuery = true)
    List<LearningContentEntity> findFirstUnlearned(@Param("userId") int userId,
                                                   @Param("stage") String stage,
                                                   Pageable pageable);

    // 모두 완료면 랜덤 복습 1개
    @Query(value = "select * from LearningContent where stage=:stage order by rand()", nativeQuery = true)
    List<LearningContentEntity> pickAny(@Param("stage") String stage, Pageable pageable);
}