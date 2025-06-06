import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.google.ksp)
}

val localProps = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProps.load(localPropertiesFile.inputStream())
}
val keystorePath: String? = localProps.getProperty("KEYSTORE_FILE")
val keystoreFile = keystorePath?.let { file(it) }
val hasKeystore = keystoreFile?.exists() == true
if (keystorePath != null && !hasKeystore) {
    println("Warning: Keystore file not found at ${keystoreFile?.absolutePath}")
}

android {
    namespace = "com.coinhub.android"
    compileSdk = 35

    if (hasKeystore) {
        signingConfigs {
            create("release") {
                storeFile = keystoreFile
                storePassword = localProps.getProperty("KEYSTORE_PASSWORD")
                keyAlias = localProps.getProperty("KEY_ALIAS")
                keyPassword = localProps.getProperty("KEY_PASSWORD")
            }
        }
    }

    defaultConfig {
        applicationId = "com.coinhub.android"
        minSdk = 33
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
        buildConfigField(
            "String",
            "apiServerUrl",
            "\"${localProps.getProperty("API_SERVER_URL")}\""
        )
        buildConfigField(
            "String",
            "vnpayReturnUrl",
            "\"${localProps.getProperty("VNPAY_RETURN_URL")}\""
        )
    }

    buildTypes {
        debug {
            getByName("release") {
                if (hasKeystore) { // Just use same signing config for debug
                    signingConfig = signingConfigs.getByName("release")
                }
            }
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            if (hasKeystore) {
                signingConfig = signingConfigs.getByName("release")
            }
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
    implementation(libs.androidx.animation.core.lint)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.zxing.core)
    implementation(libs.zxing.android.embedded)
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
    implementation(libs.androidx.material.icon)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity)


    implementation(libs.coil.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.gson)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.preferences.core)

    implementation(libs.compose.charts)
}
