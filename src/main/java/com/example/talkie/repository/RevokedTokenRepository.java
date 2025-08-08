// com/example/talkie/repository/RevokedTokenRepository.java
package com.example.talkie.repository;

import com.example.talkie.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long> {
    boolean existsByJti(String jti);
}
