import kotlinx.benchmark.gradle.JvmBenchmarkTarget
import kotlinx.benchmark.gradle.benchmark
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.9.21"

    kotlin("plugin.allopen") version "1.9.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.6"

}

repositories {
    mavenCentral()
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

tasks {
    sourceSets {
        main {
            java.setSrcDirs(listOf("src", "benchmarks"))
            kotlin.setSrcDirs(listOf("src", "benchmarks"))
        }
    }


    wrapper {
        gradleVersion = "8.4"
    }
}
dependencies {

    implementation("com.squareup.retrofit2:retrofit:2.9.0)")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.6")
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime-jvm:0.4.6")
}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

benchmark {
    configurations {
        named("main") {
            iterationTime = 5
            iterationTimeUnit = "sec"
        }
    }
    targets {
        register("main")
        {
            this as JvmBenchmarkTarget
            jmhVersion = "1.36"//"1.21"
        }
    }
}
