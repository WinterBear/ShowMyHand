import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.snowcave"
version = "1.0-SNAPSHOT"
val latestJava = 21
val oldestJava = 17

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-text-minimessage:4.14.0")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-source $oldestJava")
    options.compilerArgs.add("-target $oldestJava")
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
    jvmToolchain(oldestJava)
}