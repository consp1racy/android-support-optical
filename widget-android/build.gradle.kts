plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(18)
        targetSdkVersion(29)
    }
}

dependencies {
    implementation(projects.insets)
    implementation(projects.insetsHelper)
    implementation(projects.insetsExtensions)

    implementation("androidx.annotation:annotation:1.2.0")
}

version = "2.0.0"

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("mavenAar") {
                from(components["release"])
            }
        }
    }
}