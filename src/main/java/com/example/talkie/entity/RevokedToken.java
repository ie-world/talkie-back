package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.Instant;


@Entity
@Table(name="revoked_tokens")
@Getter
@NoArgsConstructor
public class RevokedToken {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=512)
    private String jti; // JWT ID

    @Column(nullable=false)
    private Long exp; // 토큰 만료시각(epoch seconds)

    public RevokedToken(String jti, Long exp) {
        this.jti = jti;
        this.exp = exp; // epoch seconds
    }
}
