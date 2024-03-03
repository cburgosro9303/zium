package org.ium.api.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.ium.api.controller.CompressionController;
import org.ium.api.mapper.CompressMapper;
import org.ium.api.model.CompressedFileDto;
import org.ium.api.util.BytesUtil;
import org.ium.model.CompressedFile;
import org.ium.model.FileToCompress;
import org.ium.model.port.CompressionPort;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

import static org.ium.api.mapper.CompressMapper.INSTANCE;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/compress")
public class CompressionControllerImpl {

    private final CompressionPort compressionPort;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<CompressedFileDto>> compress(@RequestPart("file") Mono<FilePart> filePartMono,
                                                            @RequestPart("compressionLevel") String compressionLevel) {
        try{

        return filePartMono
                .flatMapMany(FilePart::content)
                .map(DataBuffer::asByteBuffer)
                .collectList()
                .flatMap(BytesUtil::convert)
                .flatMap(byteArray -> {
                    int level = Integer.parseInt(compressionLevel);
                    return compressionPort.compress(new FileToCompress(byteArray, level));
                })
                .map(INSTANCE::toDto)
                .map(ResponseEntity::ok);
        }catch (Throwable e){
            log.error("falla en conversion",e);
            return Mono.error(e);
        }
    }
}