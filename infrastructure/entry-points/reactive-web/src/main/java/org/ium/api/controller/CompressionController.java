package org.ium.api.controller;

import org.ium.api.model.CompressedFileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface CompressionController {

    Mono<ResponseEntity<CompressedFileDto>> compress(FilePart file, String compressionLevel);

}
