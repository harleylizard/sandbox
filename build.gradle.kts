import org.lwjgl.Lwjgl
import org.lwjgl.lwjgl

plugins {
    id("java")
    id("org.lwjgl.plugin") version "0.0.34"
    id("io.github.sgtsilvio.gradle.proguard") version "0.6.0"
}

group = "com.harleylizard"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    lwjgl {
        implementation(Lwjgl.Module.core)
        implementation(Lwjgl.Module.glfw, Lwjgl.Module.opengl, Lwjgl.Module.stb)
        implementation(Lwjgl.Addons.`joml 1_10_5`)
    }
    implementation("it.unimi.dsi:fastutil:8.5.13")
    implementation("com.google.guava:guava:33.0.0-jre")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.guardsquare:proguard-gradle:7.3.2")
    implementation("com.harleylizard:simplexnoise:1.0-SNAPSHOT")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}