// repository/TopicRepository.java
package com.example.talkie.repository;

import com.example.talkie.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByType(String type);
}
