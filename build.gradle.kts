plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
    id("application")
    id("io.ktor.plugin") version "2.3.5" // Plugin para o Ktor
}

group = "com.tarefasapi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Ktor core
    implementation("io.ktor:ktor-server-core:2.3.5")
    implementation("io.ktor:ktor-server-netty:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.11")

    implementation("org.jetbrains.exposed:exposed-core:0.43.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.43.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.43.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.43.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")


    // Banco de dados (H2)
    implementation("com.h2database:h2:2.2.224") // H2 para testes ou banco embutido

    // Jackson para JSON
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")

    // Testes
    testImplementation("io.ktor:ktor-server-tests:2.3.5")
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.3.5")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.tarefasapi.MainKt")
}
