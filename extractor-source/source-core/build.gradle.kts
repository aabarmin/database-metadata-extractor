plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":extractor-core"))

    implementation("org.springframework.boot:spring-boot-starter")
}