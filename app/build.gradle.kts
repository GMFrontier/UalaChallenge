import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id(Plugins.androidApplicationPlugin)
    id(Plugins.kotlinAndroidPlugin) version (Kotlin.version)
    id(Plugins.kotlinComposePlugin) version (Kotlin.version)
    id(Plugins.kspPlugin)
    id(Plugins.hiltAndroidPlugin)
    id(Plugins.kotlinSerializationPlugin) version (Kotlin.version)
    id(Plugins.junit5Plugin) version (Plugins.junit5)
    id(Plugins.ktLintPlugin)
    id(Plugins.secretsPlugin)
}

android {
    namespace = ProjectConfig.appId
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = ProjectConfig.appIdPrefix + "HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

secrets {
    propertiesFileName = "secrets.properties"

    defaultPropertiesFileName = "local.defaults.properties"
}

ktlint {
    version.set("0.48.2")
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    additionalEditorconfig.set(
        mapOf(
            "max_line_length" to "20"
        )
    )
    @Suppress("DEPRECATION")
    disabledRules.set(setOf("final-newline"))
    baseline.set(file("$projectDir/config/ktlint/baseline.xml"))
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
    }
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5") // Or the latest version

    implementation(project(Modules.common))
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.cityViewerPresentation))
    implementation(project(Modules.cityViewerDomain))
    implementation(project(Modules.cityViewerData))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)
    implementation(AndroidX.navigation)

    implementation(Kotlin.kotlinSerializationJson)

    implementation(Compose.activityCompose)
    implementation(platform(Compose.composeBom))
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material3)

    implementation(DaggerHilt.hiltAndroid)
    implementation(DaggerHilt.hiltNavigationCompose)
    ksp(DaggerHilt.hiltCompiler)
    ksp(DaggerHilt.hiltAndroidCompiler)

    testImplementation(Testing.junit)
    testImplementation(Testing.assertk)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockWebServer)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.turbine)
    testImplementation(Testing.jupiterApi)
    runtimeOnly(Testing.jupiterEngine)
    testImplementation(Testing.jupiterParams)

    androidTestImplementation(Testing.assertk)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.junit)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.rules)
    androidTestImplementation(Testing.uiTestManifest)
    androidTestImplementation(Testing.junit4)
    androidTestImplementation(platform(Compose.composeBom))
    androidTestImplementation(Testing.hiltTesting)
    androidTestImplementation(Testing.testRunner)
    androidTestImplementation(Testing.uiAutomator)

    debugImplementation(Compose.uiTooling)
    debugImplementation(Testing.uiTestManifest)
}