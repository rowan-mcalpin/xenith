[![JitPack](https://img.shields.io/jitpack/version/com.rowanmcalpin/xenith?label=JitPack)](https://jitpack.io/#com.rowanmcalpin/xenith)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/t/rowan-mcalpin/xenith?label=Commits)](https://github.com/rowan-mcalpin/xenith/commits/main/)

# This project is on hold as of 4/8/2024

# Xenith

Xenith is an entirely custom library for FIRST Tech Challenge. It introduces new features and functionality to the base SDK, such as commands, more customizable motor control, and simpler OpMode creation.


## Installation

#### Install Xenith using Gradle.

In your project's `build.dependencies.gradle` file, change the `repositories` block to look like this:

```groovy
repositories {
    mavenCentral()
    google() // Needed for androidx

    maven { url = "https://jitpack.io/" }
}
```

Now, in your `TeamCode` module's `build.gradle`, add the following line to the end of the `dependencies` block:

```groovy
implementation 'com.rowanmcalpin:xenith:0.0.0.8'
```

Sync Gradle and you're good to go!
