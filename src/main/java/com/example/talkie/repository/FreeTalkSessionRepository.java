// repository/FreeTalkSessionRepository.java
package com.example.talkie.repository;

import com.example.talkie.entity.FreeTalkSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreeTalkSessionRepository extends JpaRepository<FreeTalkSession, Long> {
    Optional<FreeTalkSession> findByUsernameAndEndTimeIsNull(String username);
}
