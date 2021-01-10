plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":extractor-core"))
    implementation(project(":extractor-source:source-core"))

    implementation("org.springframework.boot:spring-boot-starter")
}