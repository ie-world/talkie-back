// com/example/talkie/entity/RevokedToken.java
package com.example.talkie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="revoked_tokens")
@Getter @NoArgsConstructor
public class RevokedToken {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=512)
    private String jti; // JWT ID

    @Column(nullable=false)
    private Long exp; // epoch seconds

    public RevokedToken(String jti, Long exp) {
        this.jti = jti;
        this.exp = exp;
    }
}
