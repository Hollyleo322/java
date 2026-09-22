plugins {
    id("java")
    id("io.freefair.lombok") version "8.14.2"
    id("jacoco")
    id("application")
}
application {
    mainClass = "algorithm.GraphAlgorithms"
}
tasks.run {
    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}
group = "main"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("lib/javacpp.jar"))
    implementation(files("lib/s21_graph-1.0-SNAPSHOT.jar"))
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.platform:junit-platform-launcher:1.13.4")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
}