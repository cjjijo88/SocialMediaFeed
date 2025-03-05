plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.example.socialmediafeed"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.socialmediafeed"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.room.db)
    kapt(libs.androidx.room.kapt)
    implementation(libs.androidx.room.ktx)

    implementation(libs.com.google.hilt)
    kapt(libs.com.google.hilt.kapt)
    implementation(libs.androidx.hilt.navigation)

    implementation(libs.org.jetbrains.coroutine.core)
    implementation(libs.org.jetbrains.coroutine.android)

    implementation(libs.com.squareup.retrofit)
    implementation(libs.com.squareup.gson.converter)

    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.io.coil)


    implementation(libs.androidx.paging)

    implementation(libs.androidx.paging.compose)

    implementation(libs.com.google.swipe.refresh)

    implementation(libs.androidx.compose.material.icons)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}