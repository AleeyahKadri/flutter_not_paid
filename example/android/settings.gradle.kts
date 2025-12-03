import java.util.Properties

include(":app")

val localPropertiesFile = File(rootProject.projectDir, "local.properties")
val properties = Properties()

if (!localPropertiesFile.exists()) {
    throw AssertionError("local.properties file does not exist")
}

localPropertiesFile.inputStream().use { stream ->
    properties.load(stream)
}

val flutterSdkPath = properties.getProperty("flutter.sdk")
    ?: throw AssertionError("flutter.sdk not set in local.properties")

apply(from = "$flutterSdkPath/packages/flutter_tools/gradle/app_plugin_loader.gradle")
