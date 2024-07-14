// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false

    // Halt
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false

}
