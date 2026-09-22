plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "s21.main"
version = "1.0-SNAPSHOT"

application {
    mainClass = "run.Desktop"
}
tasks.shadowJar {
    archiveClassifier.set("all")
    manifest {
        attributes(
            "Main-Class" to "run.Cli"
        )
    }
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
    implementation("net.java.dev.jna:jna:5.17.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}