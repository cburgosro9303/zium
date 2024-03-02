package org.ium.api.controller.impl;

import lombok.RequiredArgsConstructor;
import org.ium.api.controller.CompressionController;
import org.ium.api.mapper.CompressMapper;
import org.ium.api.model.CompressedFileDto;
import org.ium.api.model.FileToCompressDto;
import org.ium.model.port.CompressionPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compress")
public class CompressionControllerImpl implements CompressionController {

    private final CompressionPort compressionPort;

    @PostMapping
    public Mono<ResponseEntity<CompressedFileDto>> compress(@RequestBody FileToCompressDto dto) {

        return Mono.just(dto)
                .map(CompressMapper.INSTANCE::toModel)
                .flatMap(compressionPort::compress)
                .map(CompressMapper.INSTANCE::toDto)
                .map(ResponseEntity::ok);

    }
}
