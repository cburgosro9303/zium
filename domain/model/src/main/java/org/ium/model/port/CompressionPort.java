package org.ium.model.port;

import org.ium.model.CompressedFile;
import org.ium.model.FileToCompress;
import reactor.core.publisher.Mono;

public interface CompressionPort {

    Mono<CompressedFile> compress(FileToCompress fileToCompress);
}
