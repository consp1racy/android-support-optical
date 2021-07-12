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
    compileOnly(project(":widget-cardview"))

    implementation(project(":insets"))
    implementation(project(":insets-helper"))

    implementation("androidx.annotation:annotation:1.2.0")

    api(project(":widget-android"))

    api("androidx.appcompat:appcompat:1.3.0")

    compileOnly("com.google.android.material:material:1.0.0")
    compileOnly("androidx.cardview:cardview:1.0.0")
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
