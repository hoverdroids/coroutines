import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.3"

    defaultConfig {
        applicationId = "com.hoverdroids.coroutines"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions.exclude("META-INF/main.kotlin_module")
}

//More at https://developer.android.com/jetpack/androidx/migrate/artifact-mappings
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //Kotlin
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation("androidx.core:core-ktx:1.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.4")

    // Coroutines - Retrofit extention
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-experimental-adapter:1.0.0")

    //Logging
    implementation("com.elvishew:xlog:1.6.1")
    implementation("com.jakewharton.timber:timber:4.7.1")

    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.2.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.2.2")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.5.2")

    // LiveData and ViewModel
    implementation("android.arch.lifecycle:extensions:1.1.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.2")
    implementation("com.squareup.retrofit2:converter-moshi:2.0.0")

    // Dagger 2
    implementation("com.google.dagger:dagger:2.25.2")
    kapt("com.google.dagger:dagger-compiler:2.25.2")
    compileOnly("org.glassfish:javax.annotation:10.0-b28")

    // Tests
    testImplementation("junit:junit:4.13")
    testImplementation("io.mockk:mockk:1.8.7")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4")
    testImplementation("android.arch.core:core-testing:1.1.1")
    testImplementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

repositories {
    mavenCentral()
    maven("http://repository.jetbrains.com/all")
}