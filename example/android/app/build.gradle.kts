import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { stream ->
        localProperties.load(stream)
    }
}

val flutterRoot = localProperties.getProperty("flutter.sdk")
    ?: throw GradleException("Flutter SDK not found. Define location with flutter.sdk in the local.properties file.")

val flutterVersionCode = localProperties.getProperty("flutter.versionCode") ?: "1"
val flutterVersionName = localProperties.getProperty("flutter.versionName") ?: "1.0"

plugins {
    id("com.android.application")
    id("kotlin-android")
}

apply(from = "$flutterRoot/packages/flutter_tools/gradle/flutter.gradle")

android {
    compileSdkVersion(29)

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
        }
    }

    lintOptions {
        disable("InvalidPackage")
    }

    defaultConfig {
        // TODO: Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html).
        applicationId = "com.example.example"
        minSdkVersion(16)
        targetSdkVersion(29)
        versionCode = flutterVersionCode.toInt()
        versionName = flutterVersionName
    }

    buildTypes {
        getByName("release") {
            // TODO: Add your own signing config for the release build.
            // Signing with the debug keys for now, so `flutter run --release` works.
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

// Flutter extension configuration
// The flutter extension is registered by flutter.gradle and we access it dynamically
val flutter = project.extensions.getByName("flutter")
val sourceMethod = flutter.javaClass.getMethod("source", String::class.java)
sourceMethod.invoke(flutter, "../..")

dependencies {
    val kotlinVersion: String by rootProject.extra
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
}
