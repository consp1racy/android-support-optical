plugins {
    id("com.android.application")
    kotlin("android")
    id("maven-publish")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "net.xpece.androidx.optical.sample"

        minSdkVersion(18)
        targetSdkVersion(29)

        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            signingConfig = signingConfigs["debug"]
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.vectordrawable:vectordrawable:1.1.0")

    implementation(projects.widgetAndroid)
    implementation(projects.widgetCardview)
    implementation(projects.widgetMaterial)
    implementation(projects.rootAndroidLayout)

    implementation(projects.insets)

    implementation(projects.insetsExtensions)

    implementation(projects.insetsHelper)

    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
}