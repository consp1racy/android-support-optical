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
    compileOnly(projects.widgetCardview)

    implementation(projects.insetsHelper)

    implementation("androidx.annotation:annotation:1.2.0")

    api(projects.widgetAndroid)

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
