dependencies {
    implementation(project(":extractor-core"))
    implementation(project(":extractor-source:source-oracle"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    runtimeOnly("com.oracle.ojdbc:ojdbc8")
}