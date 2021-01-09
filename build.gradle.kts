plugins {
    id("base")
    id("java")
    id("org.springframework.boot").version("2.4.1").apply(false)
    id("io.spring.dependency-management").version("1.0.10.RELEASE")
}

allprojects {
    group = "ru.mydesignstudio"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    repositories {
        mavenCentral()
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencies {
        /**
         * Lombok is used by all the projects
         */
        compileOnly("org.projectlombok:lombok")
        testCompileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testAnnotationProcessor("org.projectlombok:lombok")
        /**
         * Spring's featured dependencies
         */
        runtimeOnly("org.springframework.boot:spring-boot-devtools")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        /**
         * Adding common dependencies
         */
        implementation("commons-io:commons-io:2.6")
        implementation("commons-lang:commons-lang:2.6")
        implementation("com.google.guava:guava:30.1-jre")
    }

    /**
     * BOM for all the Spring's dependencies
     */
    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    /**
     * All the modules are using JUnit 5
     */
    tasks {
        test {
            useJUnitPlatform()
        }
    }
}