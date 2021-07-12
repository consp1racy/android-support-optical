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
    api(projects.rootFrameLayout)
    api(projects.rootLinearLayout)
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
