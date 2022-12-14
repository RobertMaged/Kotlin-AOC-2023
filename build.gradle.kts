import kotlinx.benchmark.gradle.*
import org.jetbrains.kotlin.allopen.gradle.*
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    java
    kotlin("jvm") version "1.7.22"

    kotlin("plugin.allopen") version "1.7.22"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.4"

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
            java.srcDirs("src")
            kotlin.srcDirs("src")
//            resources.setSrcDirs(listOf("$name/resources"))
        }

        register("benchmarks") {

//            java.srcDirs("benchmarks")
            kotlin.srcDirs("benchmarks")
            this.runtimeClasspath = sourceSets.main.get().runtimeClasspath
        }
    }


    wrapper {
        gradleVersion = "7.6"
    }
}
dependencies {

    implementation("com.squareup.retrofit2:retrofit:2.9.0)")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    kotlin.sourceSets.getByName("benchmarks").dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.4")
//            implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime-jvm:0.4.4")


        implementation(sourceSets.main.get().output)
        implementation(sourceSets.main.get().runtimeClasspath)

    }
//    implementation(project(":kotlinx-benchmark-runtime"))
//    benchmarksImplementation( sourceSets.main.output + sourceSets.main.runtimeClasspath)
}

//sourceSets.all {
//    kotlin.srcDirs("src")
//    java.srcDirs("src")
////    resources.srcDirs("$name/resources")
//}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

benchmark {
    sourceSets {
//    register("benchmarks")
    }
    configurations {
        named("main") {
            iterationTime = 5
            iterationTimeUnit = "sec"

        }
    }
    targets {
        register("benchmarks") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
        register("main") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
//        register("kotlin")
//        register("jvm")
    }
}
