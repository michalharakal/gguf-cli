plugins {
    kotlin("multiplatform")
}
kotlin {
    jvm()
    macosX64 {
        binaries {
            executable()
        }
    }
    linuxX64 {
        binaries {
            executable()
        }
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.io.core)
                implementation(libs.skainet.core)
                implementation(libs.skainet.io)
                implementation(libs.skainet.gguf)

            }
        }
    }
}