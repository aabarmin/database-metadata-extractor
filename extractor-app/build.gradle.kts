plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":extractor-core"))
    implementation(project(":extractor-appender:appender-sql"))
    implementation(project(":extractor-output:output-html"))
    implementation(project(":extractor-output:output-confluence"))

    testImplementation("org.springframework.boot:spring-boot-starter-jdbc")
    testImplementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.0")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
}

tasks {
    clean {
        delete("html_output")
    }

    bootJar {
        archiveFileName.set("${rootProject.name}.${archiveExtension.get()}")
    }
}
