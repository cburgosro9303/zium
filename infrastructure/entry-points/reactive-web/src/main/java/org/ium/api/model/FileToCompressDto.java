package org.ium.api.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record FileToCompressDto(
        @NotBlank
        MultipartFile base64,

        @NotNull
        @Min(1)
        @Max(22)
        Integer compressionLevel
) {
}
