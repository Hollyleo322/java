plugins {
	java
    id("application")
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "holly"
version = "0.0.1-SNAPSHOT"
description = "Interface for Simple Navigator s21"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(24)
	}
}
application {
    mainClass = "cli.Application"
}

repositories {
	mavenCentral()
}

extra["springShellVersion"] = "3.4.1"

dependencies {
    implementation(files("lib/s21_graph_algorithms-1.0-SNAPSHOT.jar", "lib/s21_graph-1.0-SNAPSHOT.jar", "lib/javacpp.jar"))
	implementation("org.springframework.shell:spring-shell-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.shell:spring-shell-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.shell:spring-shell-dependencies:${property("springShellVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.bootRun {
    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}
