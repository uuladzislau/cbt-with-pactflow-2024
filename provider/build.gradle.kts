plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.spring") version "2.0.0"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("au.com.dius.pact") version "4.6.15"
}

group = "com.github.uuladzislau"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

val pact_version = "4.6.15"
val swagger_request_validator_version = "2.43.0"
val rest_assured_version = "5.5.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    // Test dependencies for Pact
    testImplementation("com.atlassian.oai:swagger-request-validator-restassured:$swagger_request_validator_version")
    testImplementation("io.rest-assured:kotlin-extensions:$rest_assured_version")
    testImplementation("au.com.dius.pact.provider:junit5:$pact_version")
    testImplementation("au.com.dius.pact.provider:spring6:$pact_version")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()


}
