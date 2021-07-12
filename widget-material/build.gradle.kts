plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(18)
        targetSdkVersion(29)
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    compileOnly(project(":widget-cardview"))

    implementation(project(":insets"))
    implementation(project(":insets-helper"))

    api(project(":widget-appcompat"))

    implementation("androidx.annotation:annotation:1.2.0")

    api("com.google.android.material:material:1.0.0")

    testImplementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.5.1")
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
