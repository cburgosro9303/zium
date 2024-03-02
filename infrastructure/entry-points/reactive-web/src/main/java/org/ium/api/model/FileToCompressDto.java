package org.ium.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FileToCompressDto(
        @NotBlank
        String base64,

        @NotNull
        @Positive
        Integer compressionLevel
) {
}
