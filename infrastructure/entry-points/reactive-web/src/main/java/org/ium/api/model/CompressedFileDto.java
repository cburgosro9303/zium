package org.ium.api.model;

import java.util.Map;

public record CompressedFileDto(
        String base64,
        Map<String, Object> metadata
) {
}
