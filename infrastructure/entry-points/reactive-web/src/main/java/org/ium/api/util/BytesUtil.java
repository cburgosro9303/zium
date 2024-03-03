package org.ium.api.util;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.List;

@UtilityClass
public class BytesUtil {

    public static Mono<byte[]> convert(List<ByteBuffer> buffers) {
        return Mono.defer(() -> {
            return Mono.just(buffers)
                    .map(buffs -> {
                        int totalLength = buffs.stream()
                                .mapToInt(ByteBuffer::remaining)
                                .sum();
                        byte[] result = new byte[totalLength];
                        int currentPosition = 0;
                        for (ByteBuffer buffer : buffs) {
                            int length = buffer.remaining();
                            buffer.get(result, currentPosition, length);
                            currentPosition += length;
                        }
                        return result;
                    });
        });
    }

}
