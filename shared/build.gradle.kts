import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version libs.versions.kotlin
    alias(libs.plugins.realm)
}

kotlin {
   /* @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
       browser()
    }*/
    
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    /*listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }*/
    
    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")

            //Ktor
            implementation("io.ktor:ktor-client-core:2.3.6")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
            implementation("io.ktor:ktor-client-logging:2.3.6")

            //Koin
            implementation("io.insert-koin:koin-core:3.5.3")

            implementation(libs.realm)
        }
        androidMain.dependencies {
            implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.1")
            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
            implementation("io.ktor:ktor-client-android:2.3.6")
        }
    }
}

android {
    namespace = "org.dgkat.multiplatform_fake_shop.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
