package com.mathiaslanglet

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient

@RestController
class FileController(
    private val webClient: WebClient,
) {
    @GetMapping("/files/{id}")
    fun streamPdf(
        @PathVariable id: String,
    ): ResponseEntity<ByteArray> {
        val document =
            webClient
                .get()
                .uri("http://file-server/www/pdf/$id")
                .exchangeToMono { clientResponse: ClientResponse ->
                    clientResponse.bodyToMono(ByteArray::class.java)
                }.block()

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=$id")
            .body(document)
    }
}
