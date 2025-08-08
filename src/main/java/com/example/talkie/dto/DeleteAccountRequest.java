// com/example/talkie/dto/DeleteAccountRequest.java
package com.example.talkie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteAccountRequest {
    @NotBlank
    private String password;
}
