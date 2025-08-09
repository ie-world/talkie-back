// src/main/java/com/example/talkie/repository/LearningContentRepository.java
package com.example.talkie.repository;

import com.example.talkie.entity.LearningContentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LearningContentRepository extends JpaRepository<LearningContentEntity, Integer> {

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
