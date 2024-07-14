plugins {
    alias(libs.plugins.android.application)

    // Halt
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.paggingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.paggingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")

    // Gson
    implementation ("com.google.code.gson:gson:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson: 2.11.0")

    // Pagging library
    val paging_version = "3.3.0"

    implementation("androidx.paging:paging-runtime:$paging_version")

    // RxJava3 support
    implementation("androidx.paging:paging-rxjava3:$paging_version")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    
    // Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    // Android lifecycle
    val lifecycle_version = "2.8.3"
    val arch_version = "2.2.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

}

kapt {
    correctErrorTypes = true
}