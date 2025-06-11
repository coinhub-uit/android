// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.sonarqube") version "6.2.0.5505"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.google.ksp) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

sonar {
    properties {
        property("sonar.projectKey", "coinhub-uit_android")
        property("sonar.organization", "coinhub-uit")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}