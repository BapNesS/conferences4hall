plugins {
    id("com.android.library")
    kotlin("android")
}

val composeVersion: String by project
val accompanistVersion: String by project
val coilVersion: String by project
val datetimeVersion: String by project
android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":ui-resources"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    api("io.coil-kt:coil-compose:$coilVersion")
    api("io.coil-kt:coil-svg:$coilVersion")
    implementation("com.google.accompanist:accompanist-placeholder-material:$accompanistVersion")
    implementation("br.com.devsrsouza.compose.icons.android:font-awesome:1.0.0")
    implementation("com.halilibo.compose-richtext:richtext-commonmark:0.11.0")
    implementation("androidx.compose.material3:material3:1.0.0-alpha13")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    api("io.openfeedback:feedback-android-sdk-ui:0.0.8-SNAPSHOT")
    // Weird but necessary for the compose preview.
    debugImplementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    debugImplementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    debugImplementation("androidx.savedstate:savedstate-ktx:1.1.0")
    debugImplementation("androidx.core:core-ktx:1.7.0")
    debugImplementation("androidx.customview:customview:1.2.0-alpha01")
    debugImplementation("androidx.customview:customview-poolingcontainer:1.0.0-beta01")
}
