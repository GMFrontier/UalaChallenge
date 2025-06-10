plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
    id(Plugins.kotlinSerializationPlugin) version (Kotlin.version)

}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "core_ui"
}

dependencies {
    implementation(project(Modules.common))
    implementation(Kotlin.kotlinSerializationJson)

}