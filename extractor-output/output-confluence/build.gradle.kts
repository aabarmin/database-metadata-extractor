dependencies {
    implementation(project(":extractor-core"))
    implementation(project(":extractor-output:output-html"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("com.github.tomakehurst:wiremock:2.26.3")
    testImplementation("org.assertj:assertj-core:3.16.1")
    implementation("org.apache.httpcomponents:httpclient")
}