package org.ium.api.controller.impl;

import lombok.RequiredArgsConstructor;
import org.ium.api.controller.CompressionController;
import org.ium.api.mapper.CompressMapper;
import org.ium.api.model.CompressedFileDto;
import org.ium.api.util.BytesUtil;
import org.ium.model.FileToCompress;
import org.ium.model.port.CompressionPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compress")
public class CompressionControllerImpl {

    private final CompressionPort compressionPort;

    @PostMapping(consumes = MediaType.ALL_VALUE)
    public Mono<ResponseEntity<CompressedFileDto>> compress(@RequestPart("file") Mono<FilePart> filepart,
                                                            @RequestPart("compressionLevel") String compressionLevel) {
        return filepart
                .flatMap(file -> file.content()
                        .flatMapSequential(dataBuffer -> Flux.fromIterable(dataBuffer::readableByteBuffers))
                        .collectList()
                        .flatMap(BytesUtil::convert)
                        .map(byteBuffer -> new FileToCompress(byteBuffer, Integer.parseInt(compressionLevel))))
                .flatMap(compressionPort::compress)
                .map(CompressMapper.INSTANCE::toDto)
                .map(ResponseEntity::ok);
    }
}