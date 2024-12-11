package com.mathiaslanglet.app

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.io.OutputStream

@RestController
class FileController(
    private val restClient: RestClient,
) {
    @GetMapping("/files/{id}")
    fun getFile(
        @PathVariable id: String,
    ): ResponseEntity<StreamingResponseBody> {
        val responseBody =
            StreamingResponseBody { outputStream: OutputStream ->
                restClient
                    .get()
                    .uri("http://file-server/www/pdf/$id")
                    .exchange { _, clientResponse ->
                        clientResponse.body.transferTo(outputStream)
                    }

                outputStream.flush()
            }

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_PDF) // Set the content type to PDF
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=$id")
            .body(responseBody)
    }
}
