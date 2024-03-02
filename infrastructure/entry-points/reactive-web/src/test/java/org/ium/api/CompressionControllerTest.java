package org.ium.api;

import org.ium.api.controller.impl.CompressionControllerImpl;
import org.ium.api.model.CompressedFileDto;
import org.ium.model.CompressedFile;
import org.ium.model.FileToCompress;
import org.ium.model.port.CompressionPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void successCompression() {
        when(compressionPort.compress(any(FileToCompress.class)))
                .thenReturn(Mono.just(new CompressedFile("asd", Map.of("name", "file"))));

        webTestClient.post()
                .uri("/compress")
                .bodyValue(new FileToCompress("asd", 4))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CompressedFileDto.class)
                .value(actual -> assertAll("Must return compressed file Dto",
                        () -> assertNotNull(actual),
                        () -> assertNotNull(actual.metadata()),
                        () -> assertTrue(actual.metadata().containsKey("name"))
                ));
    }

}
