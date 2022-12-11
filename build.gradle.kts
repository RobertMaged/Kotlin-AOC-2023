plugins {
    kotlin("jvm") version "1.7.22"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0)")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}
