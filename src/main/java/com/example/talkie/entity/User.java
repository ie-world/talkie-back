// com/example/talkie/entity/User.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=15)
    private String username;

    // 테스트 목적: 평문 저장(운영 금지)
    @Column(nullable=false, length=100)
    private String password;
}
