plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "city_viewer_presentation"
}

dependencies {

    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.common))
    implementation(project(Modules.cityViewerDomain))

    implementation(Retrofit.gson)
    implementation(Maps.gmsPlayServicesMaps)
    implementation(Maps.mapsCompose)
    implementation(Maps.mapsComposeUtils)

    implementation(Room.pagingCompose)
}