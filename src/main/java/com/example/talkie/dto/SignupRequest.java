// com/example/talkie/dto/SignupRequest.java
package com.example.talkie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequest {
    @NotBlank
    @Size(min=4, max=15, message="ID는 4~15자")
    @Pattern(regexp="^[a-zA-Z0-9]{4,15}$", message="ID는 영문+숫자만 허용")
    private String username;

    @NotBlank
    @Size(min=8, max=15, message="비밀번호는 8~15자")
    @Pattern(
            regexp="^(?:(?=.*[A-Za-z])(?=.*\\d)|(?=.*[A-Za-z])(?=.*[!@#$%^&*()_+\\-={}[\\]|;:'\",.<>/?])|(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}[\\]|;:'\",.<>/?])).{8,15}$",
            message="영문/숫자/특수문자 중 2가지 이상 조합"
    )
    private String password;
}
