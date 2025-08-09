package com.example.talkie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ClovaService {
    @Value("${secret_key}")
    private String secretKey;
    private String clovaUrl = "https://clovaspeech-gw.ncloud.com";
    private String aiServerUrl = "http://43.200.6.47:8000";

    public ResponseEntity<String> speechToText(String audioPath, String originalText) throws IOException {
        byte[] audioBytes = Files.readAllBytes(Paths.get(audioPath));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CLOVASPEECH-API-KEY", secretKey);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(audioBytes, headers);

        return restTemplate.postForEntity(
                clovaUrl + "/recog/v1/stt?lang=Kor&assessment=true&utterance={originalText}&graph=true",
                requestEntity,
                String.class,
                originalText
        );
    }

    public ResponseEntity<String> getFeedback(Map<String, Object> sttResult) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(sttResult, headers);

        return restTemplate.postForEntity(
                aiServerUrl + "/api/feedback",
                requestEntity,
                String.class
        );
    }
}