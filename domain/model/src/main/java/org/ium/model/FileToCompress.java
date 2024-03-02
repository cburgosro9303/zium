package org.ium.model;

public record FileToCompress(
        String base64,
        Integer compressionLevel
) {
}
