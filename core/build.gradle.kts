import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    `maven-publish`

    id("org.jetbrains.dokka") version "1.9.10"
}

android {
    namespace = "com.rowanmcalpin.xenith"
    compileSdk = 32

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    publishing {
        singleVariant("release")
    }
}

dependencies {
//
//    implementation("androidx.core:core-ktx:1.12.0")
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.10.0")

    compileOnly("org.firstinspires.ftc:RobotCore:9.0.1")
    compileOnly("org.firstinspires.ftc:Hardware:9.0.1")
    compileOnly("org.firstinspires.ftc:FtcCommon:9.0.1")
    compileOnly("org.firstinspires.ftc:RobotServer:9.0.1")
    compileOnly("org.firstinspires.ftc:OnBotJava:9.0.1")
}

// CONFIGURE DOKKA
tasks.dokkaHtml.configure {
    moduleName.set("Xenith")

    dokkaSourceSets.configureEach {
        includes.from("packages.md")

    }
}

tasks.withType(DokkaTask::class).configureEach {
    pluginsMapConfiguration.set(
        mapOf(Pair("org.jetbrains.dokka.base.DokkaBase", """
        {
            "footerMessage": "Copyright © 2024 Rowan McAlpin"
        }"""))
    )
}

// CONFIGURE PUBLICATION FOR JITPACK
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.rowanmcalpin"
            artifactId = "xenith"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}