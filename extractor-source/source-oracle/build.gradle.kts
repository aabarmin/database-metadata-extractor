plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":extractor-core"))
    implementation(project(":extractor-source:source-core"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    testImplementation("org.testcontainers:testcontainers:1.15.1")
    testImplementation("org.testcontainers:junit-jupiter:1.15.1")
    testImplementation("org.testcontainers:oracle-xe:1.15.1")

    runtimeOnly("com.oracle.database.jdbc:ojdbc8")
}