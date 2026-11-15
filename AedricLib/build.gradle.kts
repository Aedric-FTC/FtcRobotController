plugins {
    id("com.android.library")
}

android {
    namespace = "com.aedriclib"
    compileSdk {
        version = release(30)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

/*repositories {
    google()
    mavenCentral()
    ivy {
        url = uri(project(":ftcRobotController").file("libs"))
        metadataSources {
            artifact() // Tells Gradle to look directly for raw files without metadata
        }
    }
}*/

dependencies {
    implementation("androidx.appcompat:appcompat:1.8.0")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
/*    compileOnly(rootProject.file("ftcRobotController/libs/RobotCore-release.aar"))
    compileOnly(rootProject.file("ftcRobotController/libs/Hardware-release.aar"))
    compileOnly(rootProject.file("ftcRobotController/libs/FtcCommon-release.aar"))*/
}