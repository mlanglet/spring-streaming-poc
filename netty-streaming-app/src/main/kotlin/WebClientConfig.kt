package com.mathiaslanglet

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration(proxyBeanMethods = false)
class WebClientConfig {
    @Bean
    fun webClient(): WebClient = WebClient.builder().build()
}
