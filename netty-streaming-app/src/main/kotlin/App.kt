package com.mathiaslanglet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class App

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    (runApplication<App>(*args))
}
