plugins {
    java
    jacoco
    id("org.springframework.boot").version("2.3.0.RELEASE")
    id("io.spring.dependency-management").version("1.0.9.RELEASE")
}

group = "ru.mydesignstudio"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

configurations {
    compileOnly {
        extendsFrom(configurations["annotationProcessor"])
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":extractor-core"))
    implementation(project(":extractor-appender:appender-sql"))
    implementation(project(":extractor-output:output-html"))
    implementation(project(":extractor-output:output-confluence"))

    testImplementation("org.springframework.boot:spring-boot-starter-jdbc")
    testImplementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("commons-io:commons-io:2.6")
    implementation("commons-lang:commons-lang:2.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.0")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.google.guava:guava:29.0-jre")

    compileOnly("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR4")
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    clean {
        delete("html_output")
    }
}
