import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0-RC2"
    id("com.github.ben-manes.versions") version "0.44.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
        vendor.set(JvmVendorSpec.AMAZON)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
    testImplementation("io.kotest:kotest-framework-datatest:5.5.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf(
            "-Xcontext-receivers",
            "-opt-in=kotlin.ExperimentalStdlibApi",
        )
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
