// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    java
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.2" apply false

    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.rowan-mcalpin"
            artifactId = "xenith"
            version = "1.0"

            from(components["java"])
        }
    }
}
