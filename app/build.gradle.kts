import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.9.0"
}

var localProps = Properties()
var localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProps.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.coinhub.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.coinhub.android"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            "String",
            "oAuthGoogleAndroidClient",
            "\"${localProps.getProperty("OAUTH_GOOGLE_ANDROID_CLIENT")}\""
        )
        buildConfigField(
            "String",
            "supabaseUrl",
            "\"${localProps.getProperty("SUPABASE_URL")}\""
        )
        buildConfigField(
            "String",
            "supabaseAnonKey",
            "\"${localProps.getProperty("SUPABASE_ANON_KEY")}\""
        )
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.google.id)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.credential)
    implementation(libs.androidx.credential.play.service.auth)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(platform(libs.supabase.bom))
    implementation(libs.auth.kt)
    implementation(libs.postgrest.kt)
    implementation(libs.realtime.kt)
    implementation(libs.ktor.client)
    implementation(libs.compose.auth)
    implementation(libs.compose.auth.ui)
}
