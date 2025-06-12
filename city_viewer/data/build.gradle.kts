plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
    id(Plugins.kspPlugin)
    id(Plugins.secretsPlugin)
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "city_viewer_data"

    buildFeatures {
        buildConfig = true
    }

    secrets {
        propertiesFileName = "secrets.properties"

        defaultPropertiesFileName = "local.defaults.properties"
    }

}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.common))
    implementation(project(Modules.cityViewerDomain))

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.gson)

    implementation(Room.roomRuntime)
    ksp(Room.roomCompiler)
    implementation(Room.roomRtx)
    implementation(Room.roomPaging)
}