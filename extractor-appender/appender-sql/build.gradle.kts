plugins {
    java
    id("org.springframework.boot").version("2.3.0.RELEASE").apply(false)
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

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    implementation("commons-lang:commons-lang:2.6")
    implementation("commons-io:commons-io:2.6")
    runtimeOnly("com.oracle.ojdbc:ojdbc8")

    compileOnly("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}