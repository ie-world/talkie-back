// dto/SelectTopicsRequest.java
package com.example.talkie.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class SelectTopicsRequest {
    private List<Long> topicIds;
}
