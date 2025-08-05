package com.example.talkie.api;

import org.springframework.web.bind.annotation.GetMapping;

public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "서버 정상 작동 중!";
    }
}
