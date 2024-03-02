package org.ium.api.util;

import lombok.experimental.UtilityClass;

import java.nio.ByteBuffer;
import java.util.List;

@UtilityClass
public class BytesUtil {

    public static byte[] convert(List<ByteBuffer> buffers) {
        // Primero, calculamos el tamaño total requerido para el arreglo de bytes
        int totalLength = 0;
        for (ByteBuffer buffer : buffers) {
            totalLength += buffer.remaining();
        }

        // Creamos un arreglo de bytes con el tamaño total
        byte[] result = new byte[totalLength];

        // Variable para mantener la posición actual de escritura en el arreglo de bytes
        int currentPosition = 0;

        // Luego, copiamos cada ByteBuffer en el arreglo de bytes
        for (ByteBuffer buffer : buffers) {
            int length = buffer.remaining();
            buffer.get(result, currentPosition, length);
            currentPosition += length;
        }

        return result;
    }
}
