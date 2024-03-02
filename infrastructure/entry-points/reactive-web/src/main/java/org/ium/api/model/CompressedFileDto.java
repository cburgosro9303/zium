package org.ium.api.model;

import java.util.Map;

public record CompressedFileDto(
        byte[] base64,
        Map<String, String> metadata
) {
}
