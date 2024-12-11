package com.mathiaslanglet

import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class FileController(
    private val webClient: WebClient,
) {
    @GetMapping("/files/{id}")
    fun streamPdf(
        @PathVariable id: String,
    ): Mono<ResponseEntity<Flux<DataBuffer>>> =
        Mono.just(
            ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=$id")
                .body(
                    webClient.get().uri("http://file-server/www/pdf/$id").exchangeToFlux {
                        it.bodyToFlux(DataBuffer::class.java)
                    },
                ),
        )
}
