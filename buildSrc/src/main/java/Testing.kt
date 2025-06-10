object Testing {
    private const val junitVersion = "4.13.2"
    const val junit = "junit:junit:$junitVersion"

    private const val junitAndroidExtVersion = "1.2.1"
    const val junitAndroidExt = "androidx.test.ext:junit:$junitAndroidExtVersion"

    private const val junit4Version = "1.7.8"
    const val junit4 = "androidx.compose.ui:ui-test-junit4:$junit4Version"

    private const val jupiterVersion = "5.9.3"
    const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:$jupiterVersion"
    const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:$jupiterVersion"
    const val jupiterParams = "org.junit.jupiter:junit-jupiter-params:$jupiterVersion"

    private const val coroutinesTestVersion = "1.10.2"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesTestVersion"

    private const val mockkVersion = "1.12.5"
    const val mockk = "io.mockk:mockk:$mockkVersion"
    const val mockkAndroid = "io.mockk:mockk-android:$mockkVersion"
    private const val mockWebServerVersion = "4.11.0"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$mockWebServerVersion"

    private const val turbineVersion = "0.7.0"
    const val turbine = "app.cash.turbine:turbine:$turbineVersion"

    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"

    private const val assertkVersion = "0.28.1"
    const val assertk = "com.willowtreeapps.assertk:assertk:$assertkVersion"

    private const val rulesVersion = "1.6.1"
    const val rules = "androidx.test:rules:$rulesVersion"

    const val hiltTesting = "com.google.dagger:hilt-android-testing:${DaggerHilt.version}"

    private const val runner = "1.5.2"
    const val testRunner = "androidx.test:runner:$runner"

    private const val uiAutomatorVersion = "2.3.0-alpha04"
    const val uiAutomator = "androidx.test.uiautomator:uiautomator:$uiAutomatorVersion"

}