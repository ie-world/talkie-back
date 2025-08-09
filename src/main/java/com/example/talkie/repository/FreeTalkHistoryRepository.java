// repository/FreeTalkHistoryRepository.java
package com.example.talkie.repository;

import com.example.talkie.entity.FreeTalkHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeTalkHistoryRepository extends JpaRepository<FreeTalkHistory, Long> {
    List<FreeTalkHistory> findByUsernameOrderByCreatedAtAsc(String username);
}
