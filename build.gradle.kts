// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.androidLibraryPlugin) version (Plugins.agp) apply false
    id(Plugins.kotlinAndroidPlugin) version (Kotlin.version) apply false
    id(Plugins.kotlinComposePlugin) version (Kotlin.version) apply false
    id(Plugins.hiltAndroidPlugin) version (DaggerHilt.version) apply false
    id(Plugins.kspPlugin) version (Plugins.kspVersion) apply false
    id(Plugins.kotlinSerializationPlugin) version (Kotlin.version) apply false
    id(Plugins.secretsPlugin) version (Plugins.secretsVersion) apply false
    id(Plugins.ktLintPlugin) version (Plugins.ktLintVersion) apply false
}



allprojects {
    tasks.register("copyGitHooks", Copy::class) {
        description = "Copies the git hooks from /git-hooks to the .git folder."
        group = "git hooks"
        from("$rootDir/scripts/pre-commit")
        into("$rootDir/.git/hooks/")
    }

    tasks.register("installGitHooks", Exec::class) {
        description = "Installs the pre-commit git hooks from /git-hooks."
        group = "git hooks"
        workingDir = rootDir
        commandLine("chmod", "-R", "+x", ".git/hooks/")
        dependsOn("copyGitHooks")
        doLast {
            logger.info("Git hook installed successfully.")
        }
    }
    afterEvaluate {
        tasks.findByName("preBuild")?.let {
            it.dependsOn("installGitHooks")
        }
    }
}

