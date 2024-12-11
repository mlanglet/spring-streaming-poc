package com.mathiaslanglet.app

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient

@RestController
class FileController(
    private val restClient: RestClient,
) {
    @GetMapping("/files/{id}")
    fun getFile(
        @PathVariable id: String,
    ): ResponseEntity<ByteArray> {
        val document =
            restClient
                .get()
                .uri("http://file-server/www/pdf/$id")
                .retrieve()
                .toEntity(ByteArray::class.java)

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=$id")
            .body(document.body)
    }
}
