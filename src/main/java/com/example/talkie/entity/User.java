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

    // BCrypt 해시는 60자, 넉넉히
    @Column(nullable=false, length=100)
    private String password;
}
