package com.example.talkie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CommonService {
    public String saveAudioFile(MultipartFile audioFile) throws IOException {
        //TODO: 외부 storage에 업로드 해야함
        String uploadDir = "./files/audios";
        String newFilename = UUID.randomUUID() + ".webm";
        Path filePath = Paths.get(uploadDir, newFilename);

        if (audioFile.isEmpty()) {
            throw new IOException("Empty File received");
        }

        Files.createDirectories(filePath.getParent());
        System.out.println("new path:" + filePath);
        try (InputStream inputStream = audioFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return filePath.toString();
    }
}