const val kotlinVersion = "1.3.70"

object BuildPlugins {
    object Version {
        const val androidBuildToolsVersion = "4.1.0-alpha01"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Version.androidBuildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    const val detekt = "io.gitlab.arturbosch.detekt"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val versions = "com.github.ben-manes.versions"
}

object AndroidSdk {
    const val min = 23
    const val compile = "android-R"
    const val target = 30
}


object Libraries {
    private object Versions {
        const val appCompat = "1.2.0-alpha03"
        const val ktx = "1.3.0-alpha02"
        const val constraintLayout = "2.0.0-beta4"
        const val material = "1.2.0-alpha05"
        const val vectorDrawable = "1.1.0"
        const val livedata = "2.3.0-alpha01"
        const val dagger = "2.26"
    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val materialComponents = "com.google.android.material:material:${Versions.material}"
    const val vectorDrawable = "androidx.vectordrawable:vectordrawable:${Versions.vectorDrawable}"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroidCompiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
}
