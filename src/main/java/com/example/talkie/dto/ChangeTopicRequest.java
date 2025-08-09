// dto/ChangeTopicRequest.java
package com.example.talkie.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class ChangeTopicRequest {
    private List<Long> topicIds;
}
