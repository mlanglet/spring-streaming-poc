plugins {
    id("buildsrc.convention.kotlin-jvm")

    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.0")

    testImplementation(kotlin("test"))
}
