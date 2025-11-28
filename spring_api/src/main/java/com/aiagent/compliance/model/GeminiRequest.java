// spring_api/src/main/java/com/aiagent/compliance/model/GeminiRequest.java

package com.aiagent.compliance.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder // Lombok builder pattern for easy creation
public class GeminiRequest {
    private List<Content> contents;

    @Data
    @Builder
    public static class Content {
        private List<Part> parts;
    }

    @Data
    @Builder
    public static class Part {
        private String text;
    }
}