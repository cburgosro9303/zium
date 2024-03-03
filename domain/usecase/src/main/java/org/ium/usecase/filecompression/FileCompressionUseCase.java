package org.ium.usecase.filecompression;

import com.github.luben.zstd.Zstd;
import lombok.RequiredArgsConstructor;
import org.ium.model.CompressedFile;
import org.ium.model.FileToCompress;
import org.ium.model.port.CompressionPort;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.ium.model.CompressedFile.FileCompressionMetadata.NEW_BYTES;
import static org.ium.model.CompressedFile.FileCompressionMetadata.OLD_BYTES;

@RequiredArgsConstructor
public class FileCompressionUseCase implements CompressionPort {
    @Override
    public Mono<CompressedFile> compress(FileToCompress fileToCompress) {
        return Mono.just(fileToCompress)
                .map(f -> Zstd.compress(f.byteBuffer(), f.compressionLevel()))
                .map(bb -> new CompressedFile(bb, Map.of(OLD_BYTES, String.valueOf(fileToCompress.byteBuffer().length), NEW_BYTES, String.valueOf(bb.length))));
    }
}
