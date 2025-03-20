import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.github.goooler.shadow") version "8.1.8"
}

group = "dev.snowcave"
version = "1.2.2"
val javaVersion = 21

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-text-minimessage:4.14.0")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-source $javaVersion")
    options.compilerArgs.add("-target $javaVersion")
}

tasks.withType<ShadowJar>() {
    archiveFileName = project.name + "-" + project.version + ".jar"
    dependencies {
        exclude(dependency("org.jetbrains.kotlin:.*"))
        exclude(dependency("org.jetbrains:.*"))
        exclude(dependency("org.intellij:.*"))
    }
    relocate("org.bstats", "dev.snowcave.showmyhand.metrics")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(javaVersion)
}