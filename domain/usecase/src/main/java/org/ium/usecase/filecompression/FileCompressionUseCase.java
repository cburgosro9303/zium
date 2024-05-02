package org.ium.usecase.filecompression;

import com.github.luben.zstd.Zstd;
import lombok.RequiredArgsConstructor;
import org.ium.model.CompressedFile;
import org.ium.model.FileToCompress;
import org.ium.model.port.CompressionPort;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.ByteBuffer;
import java.util.Map;

import static org.ium.model.CompressedFile.FileCompressionMetadata.NEW_BYTES;
import static org.ium.model.CompressedFile.FileCompressionMetadata.OLD_BYTES;

@RequiredArgsConstructor
public class FileCompressionUseCase implements CompressionPort {
    @Override
    public Mono<CompressedFile> compress(FileToCompress fileToCompress) {
        return Mono.just(fileToCompress)
                .map(f -> ByteBuffer.wrap(f.byteBuffer()))
                .flatMap(byteBuffer -> compressFileReactively(byteBuffer, fileToCompress.compressionLevel()))
                .map(bb -> new CompressedFile(bb.array(), Map.of(OLD_BYTES, String.valueOf(fileToCompress.byteBuffer().length),
                        NEW_BYTES, String.valueOf(bb.array().length))));
    }

    private Mono<ByteBuffer> compressFileReactively(ByteBuffer byteBuffer, int compressionLevel) {
        return Mono.fromCallable(() -> {
            byte[] inputBytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(inputBytes);  // Copia los bytes de ByteBuffer a byte[]
            byte[] compressedBytes = Zstd.compress(inputBytes, compressionLevel);
            return ByteBuffer.wrap(compressedBytes);  // Envuelve el resultado comprimido en un ByteBuffer
        }).subscribeOn(Schedulers.boundedElastic());  // Ejecuta en un hilo que permite operaciones bloqueantes
    }
}
