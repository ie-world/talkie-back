package com.example.talkie.service;

import com.example.talkie.dto.ProfileResponse;
import com.example.talkie.entity.User;
import com.example.talkie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    public ProfileResponse getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // joinedAt이 있으면 사용, 없으면 createdAt, 그것도 없으면 오늘 날짜
        LocalDateTime base = user.getJoinedAt() != null ? user.getJoinedAt()
                : (getOrNullCreatedAt(user) != null ? getOrNullCreatedAt(user) : LocalDateTime.now());

        return new ProfileResponse(user.getUsername(), base.toLocalDate());
    }

    // createdAt 필드가 없을 수도 있으니 안전하게 접근
    private LocalDateTime getOrNullCreatedAt(User user) {
        try {
            var f = user.getClass().getDeclaredField("createdAt");
            f.setAccessible(true);
            Object v = f.get(user);
            return (v instanceof LocalDateTime dt) ? dt : null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}
