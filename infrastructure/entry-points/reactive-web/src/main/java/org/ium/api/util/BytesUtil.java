package org.ium.api.util;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.ByteBuffer;
import java.util.List;

@UtilityClass
public class BytesUtil {

    public static Mono<byte[]> convert(List<ByteBuffer> buffers) {
        return Mono.fromCallable(() -> {
                    int totalLength = 0;
                    for (ByteBuffer buffer : buffers) {
                        totalLength += buffer.remaining();
                    }

                    byte[] result = new byte[totalLength];
                    int currentPosition = 0;

                    for (ByteBuffer buffer : buffers) {
                        int length = buffer.remaining();
                        buffer.get(result, currentPosition, length);
                        currentPosition += length;
                    }

                    return result;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
