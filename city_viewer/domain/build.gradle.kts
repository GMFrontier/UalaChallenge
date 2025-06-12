plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
    id(Plugins.kspPlugin)
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "city_viewer_domain"
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.core))

    implementation(Room.roomRuntime)
    ksp(Room.roomCompiler)
    implementation(Room.roomRtx)

}