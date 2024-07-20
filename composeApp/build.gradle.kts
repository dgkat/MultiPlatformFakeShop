import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
   /* @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }*/
    
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.material3.android)

            implementation("androidx.navigation:navigation-compose:2.7.6")
            implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
            //implementation(libs.androidx.runtime.tracing)

            implementation(libs.compose.foundation)
            //implementation(libs.compose.material)
            implementation(libs.androidx.material.icons.extended)
            //compose-coil = { module = "io.coil-kt:coil-compose:2.6.0", version.ref = "compose" }
            implementation(libs.coil.compose)
            // Koin for Android
            val koin_version = "3.5.3"
            implementation("io.insert-koin:koin-android:$koin_version")
            implementation("io.insert-koin:koin-androidx-compose:$koin_version")
            implementation( "io.insert-koin:koin-android-compat:$koin_version")
           // implementation(project.dependencies.platform("androidx.compose:compose-bom:2024.06.00"))

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.uiTooling)
            implementation(projects.shared)
        }
    }
}

android {
    namespace = "org.dgkat.multiplatform_fake_shop"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.dgkat.multiplatform_fake_shop"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}


compose.experimental {
    web.application {}
}