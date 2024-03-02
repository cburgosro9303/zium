package org.ium.api;

import org.ium.api.controller.impl.CompressionControllerImpl;
import org.ium.model.CompressedFile;
import org.ium.model.FileToCompress;
import org.ium.model.port.CompressionPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith({MockitoExtension.class})
@ContextConfiguration(classes = {CompressionControllerImpl.class})
class CompressionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    CompressionPort compressionPort;

    @BeforeEach
    public void setUp() {
        CompressedFile mockCompressedFile = new CompressedFile(
                "base64EncodedString".getBytes(StandardCharsets.UTF_8),
                Map.of(CompressedFile.FileCompressionMetadata.OLD_BYTES, "1000",
                        CompressedFile.FileCompressionMetadata.NEW_BYTES, "800")
        );

        when(compressionPort.compress(any(FileToCompress.class)))
                .thenReturn(Mono.just(mockCompressedFile));
    }

    @Test
    public void compressTest() {
        byte[] fileContent = "fileContent".getBytes(StandardCharsets.UTF_8);
        Resource fileResource = new ByteArrayResource(fileContent) {
            @Override
            public String getFilename() {
                return "testFile.txt";
            }
        };

        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", fileResource).contentType(MediaType.APPLICATION_OCTET_STREAM);
        bodyBuilder.part("compressionLevel", "5");

        webTestClient.post().uri("/compress")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.metadata.OLD_BYTES").isEqualTo("1000")
                .jsonPath("$.metadata.NEW_BYTES").isEqualTo("800");
    }


}
