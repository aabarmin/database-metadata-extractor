dependencies {
    implementation(project(":extractor-core"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    runtimeOnly("com.oracle.ojdbc:ojdbc8")
}