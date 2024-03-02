package org.ium.api.controller;

import org.ium.api.model.CompressedFileDto;
import org.ium.api.model.FileToCompressDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface CompressionController {

    Mono<ResponseEntity<CompressedFileDto>> compress(FileToCompressDto dto);
}
