
plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.googlecode.lanterna:lanterna:3.2.0-alpha1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
}

application {
    mainClass.set("org.example.Domain")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}