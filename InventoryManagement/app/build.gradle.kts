plugins {
    alias(libs.plugins.androidApplication)
    //firebase
    //id("com.android.application")
    //id("com.google.gms.google-services")
}

android {
    namespace = "com.example.inventorymanagement"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.inventorymanagement"
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
    packaging {
        resources {
            excludes += "com/itextpdf/io/font/cmap_info.txt"
            excludes += "com/itextpdf/io/font/cmap/*"
        }
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

    // newly added
   // implementation("com.airbnb.android:lottie:6.4.0")

    // room database
    val room_version = "2.6.1"

    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")

    // pdf
    implementation("com.itextpdf:itext7-core:7.0.2")

    // firebase
    //implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
   // implementation("com.google.firebase:firebase-analytics")

    // bluetooth connectivity
    implementation ("com.itextpdf:itext7-core:7.1.15") // iText 7 library
    implementation ("com.itextpdf:io:7.1.15")         // iText IO
    implementation ("com.itextpdf:layout:7.1.15")      // iText Layout

   // implementation ("com.github.barteksc:android-pdf-viewer:3.2.0")
    // Connection of firebase database.

}
