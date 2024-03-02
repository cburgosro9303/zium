package org.ium.model;

import java.util.Map;

public record CompressedFile(
        byte[] base64,
        Map<FileCompressionMetadata, String> metadata
) {

    public enum FileCompressionMetadata {
        OLD_BYTES, NEW_BYTES
    }
}
