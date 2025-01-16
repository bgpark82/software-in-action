plugins {
    id("java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17 // code written using Java 17 synctax
    targetCompatibility = JavaVersion.VERSION_21 // gradle will generate bytecode compatible with Java 21
}

group = "com.bgpark"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0") // Replace with latest version if needed
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}

tasks.test {
    useJUnitPlatform()
}