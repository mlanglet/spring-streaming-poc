package com.mathiaslanglet

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration(proxyBeanMethods = false)
class WebClientConfig {
    @Bean
    fun webClient(): WebClient {
        val size = 40 * 1024 * 1024
        val strategies =
            ExchangeStrategies
                .builder()
                .codecs { codecs: ClientCodecConfigurer -> codecs.defaultCodecs().maxInMemorySize(size) }
                .build()
        return WebClient.builder().exchangeStrategies(strategies).build()
    }
}
