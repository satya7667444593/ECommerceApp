plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.android.library") version "8.1.0" apply false
    id("com.google.gms.google-services") version "4.4.4" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false

    // Corrected KSP version
    id("com.google.devtools.ksp") version "2.1.21-2.0.1" apply false // Example version

    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.21" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "2.1.21" apply false
}
