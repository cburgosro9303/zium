package org.ium.model;

public record FileToCompress(
        byte[] byteBuffer,
        Integer compressionLevel
) {
}
