plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    `maven-publish`
}

android {
    namespace = "com.rowanmcalpin.xenith"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.rowanmcalpin"
            artifactId = "xenith"
            version = "0.0.1"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}