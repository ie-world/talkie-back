// dto/TopicResponse.java
package com.example.talkie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicResponse {
    private Long id;
    private String name;
    private String type; // subject or situation
}
