import org.gradle.kotlin.dsl.ksp
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.buildconfig)
}
// Chargement des propriétés locales
val localProperties = Properties()
val localPropertiesFile: File? = rootProject.file("local.properties")
if (localPropertiesFile != null && localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}
val buildType = project.findProperty("buildType")?.toString() ?: "debug"
val apiKeyProperty = "API_KEY_${buildType.uppercase()}"
val apiKey = System.getenv(apiKeyProperty)
    ?: localProperties.getProperty(apiKeyProperty)
    ?: error("$apiKeyProperty non définie")

kotlin {

    androidLibrary {
        namespace = "fr.majdi.moviesapp.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        //withJava() // enable java compilation support
        //withHostTestBuilder {}.configure {}
//        withDeviceTestBuilder {
//            sourceSetTreeName = "test"
//        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
        // Enable Android resource processing
        androidResources {
            enable = true
        }
    }

    applyDefaultHierarchyTemplate()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            binaryOption("bundleId", "fr.majdi.moviesapp.shared")
        }
    }

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            api(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)


            // Koin
            api(libs.koin.core)
            implementation(libs.koin.composeVM)
            implementation(libs.koin.compose)
            implementation(libs.koin.annotations)

            // ViewModel support in common code
            implementation(libs.androidx.lifecycle.viewmodel)

            //implementation(libs.kamel.image)
            implementation(libs.bundles.coil)
            implementation(libs.coil.network.ktor3)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)

            implementation(libs.androidx.navigation.compose)
            implementation(libs.compose.tabler.icons)
        }
        val androidMain by getting
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            // Koin support for Android
            api(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.compose.ui.tooling.preview)
        }

        val iosMain by getting
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        val desktopMain by getting
        desktopMain.dependencies {
            implementation(libs.ktor.client.cio)
            implementation(libs.compose.desktop)
            implementation(libs.ktor.client.java)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }



    // KSP Common sourceSet
    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }
}

dependencies {
    listOf(
        "kspCommonMainMetadata",
        "kspAndroid",
        "kspDesktop",
        "kspIosArm64",
        "kspIosSimulatorArm64",
    ).forEach { add(it, libs.koin.ksp.compiler) }
    //à partir de gradle 9 il faut utiliser androidRunTimeClasspath au lieu de debugImplementation
    androidRuntimeClasspath(libs.compose.ui.tooling.preview)
    androidRuntimeClasspath(libs.compose.ui.tooling)
}

// Config KSP
ksp {
    //Activate default generation module
    arg("KOIN_DEFAULT_MODULE","true")
    arg("KOIN_CONFIG_CHECK", "true")
}

// Trigger Common Metadata Generation from Native tasks
tasks.matching { it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata" }.configureEach {
    dependsOn("kspCommonMainKotlinMetadata")
}

buildConfig {
    packageName("fr.majdi.moviesapp.shared.buildconfig")   // Package de la classe générée
    buildConfigField("String", "apiKey", "\"$apiKey\"")
}