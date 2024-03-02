package org.ium.model;

import java.util.Map;

public record CompressedFile(
        String base64,
        Map<String, Object> metadata
) {
}
