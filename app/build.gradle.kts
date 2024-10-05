plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "ro.alexmamo.firestorecleanarchitecture"
    compileSdk = 34

    defaultConfig {
        applicationId = "ro.alexmamo.firestorecleanarchitecture"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_19.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}

dependencies {
    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.activity.compose)
    implementation(libs.viewmodel.compose)
    //Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
}