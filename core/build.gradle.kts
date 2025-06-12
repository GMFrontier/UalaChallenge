plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinAndroidPlugin)
    id(Plugins.kotlinSerializationPlugin)
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = ProjectConfig.appIdPrefix + "core"

}

dependencies {
    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.gson)

    api(Room.pagingRuntime)
    api(Kotlin.kotlinSerializationJson)

}