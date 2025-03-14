plugins {
    java
    id("org.danilopianini.gradle-java-qa") version "1.103.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.12.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.12.1")
}

tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }
    testLogging.showStandardStreams = true
}

tasks.javadoc {
    isFailOnError = false
}
