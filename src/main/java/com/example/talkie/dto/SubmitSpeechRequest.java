// dto/SubmitSpeechRequest.java
package com.example.talkie.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SubmitSpeechRequest {
    private MultipartFile audioFile; // 음성 파일
}
