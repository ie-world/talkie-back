// com/example/talkie/dto/LoginRequest.java
package com.example.talkie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    @NotBlank private String username;
    @NotBlank private String password;
}
