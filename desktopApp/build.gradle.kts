import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
}

group = "fr.majdi.desktop.movieapp"
version = "1.0.0"

dependencies {
    implementation(project(":shared"))
    implementation(libs.compose.desktop)
    implementation(libs.kotlinx.coroutines.swing)
    implementation(libs.slf4j.simple)
}

compose.desktop {
    application {
        mainClass = "fr.majdi.desktop.movieapp.MainKt"
        jvmArgs += "-XX:+ShowCodeDetailsInExceptionMessages"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "fr.majdi.desktop.movieapp"
            macOS {
                bundleID = "fr.majdi.desktop.movieapp"
            }
        }
    }
}