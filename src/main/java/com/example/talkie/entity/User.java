// src/main/java/com/example/talkie/entity/User.java

package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // 회원 ID (중복 불가)
    @Column(nullable = false, unique = true, length = 15)
    private String username;

    // 비밀번호(BCrypt 해시 길이 고려)
    @Column(nullable = false, length = 60)
    private String password;

    // 가입일(없을 수 있음)
    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
// DB 트리거/디폴트(now)로 채워지는 컬럼
