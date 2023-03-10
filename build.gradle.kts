import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	id("org.springframework.boot") version "2.7.1"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("org.springframework.experimental.aot") version "0.12.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

extra["springCloudVersion"] = "2021.0.5"

repositories {
	maven { url = uri("https://repo.spring.io/release") }
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-configuration-processor")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("net.logstash.logback:logstash-logback-encoder:6.6")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
	testImplementation("org.mockito:mockito-junit-jupiter:4.10.0")
}


dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}


tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<BootBuildImage> {
	builder = "paketobuildpacks/builder:tiny"
	environment = mapOf("BP_NATIVE_IMAGE" to "true")
}

tasks.withType<Test> {
	useJUnitPlatform()
}


