import java.net.URI

val releasesDir: URI = File(project.property("zharelReleasesLocation").toString()).toURI()
val snapshotsDir: URI = File(project.property("zharelSnapshotsLocation").toString()).toURI()


plugins {
    kotlin("jvm")

    `java-library`
    `java-test-fixtures`

    id("org.jetbrains.dokka")

    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotlin.test)

    testFixturesApi(libs.bundles.kotest)

    testImplementation("org.knowm.xchart:xchart:3.8.8")

    dokkaHtmlPlugin("org.jetbrains.dokka:mathjax-plugin:1.9.10")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjvm-default=all"))
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.nextftc.nextrunner"
            artifactId = "core"
            version = libs.versions.lib.get()

            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "zharelReleases"
            url = releasesDir
        }
        maven {
            name = "zharelSnapshots"
            url = snapshotsDir
        }
    }
}
