plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(18)
        targetSdkVersion(29)

        consumerProguardFiles("consumer-proguard-rules.pro")
    }
}

dependencies {
    implementation("androidx.annotation:annotation:1.2.0")
}

version = "1.0.1"

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("mavenAar") {
                from(components["release"])
            }
        }
    }
}
