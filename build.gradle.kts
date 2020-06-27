plugins {
	java
	jacoco
	id("org.springframework.boot").version("2.3.0.RELEASE")
	id("io.spring.dependency-management").version("1.0.9.RELEASE")
}

group = "ru.mydesignstudio"
//sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations["annotationProcessor"])
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.httpcomponents:httpclient")
	implementation("commons-io:commons-io:2.6")
	implementation("commons-lang:commons-lang:2.6")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.11.0")
	implementation("com.google.code.findbugs:jsr305:3.0.2")
	implementation("com.google.guava:guava:29.0-jre")
	implementation("net.sourceforge.plantuml:plantuml:8059")

	testImplementation("org.assertj:assertj-core:3.16.1")
	testImplementation("com.github.tomakehurst:wiremock:2.26.3")

	compileOnly("org.projectlombok:lombok")
	testCompileOnly("org.projectlombok:lombok")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.oracle.ojdbc:ojdbc8")
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

tasks.test {
	useJUnitPlatform()
}
