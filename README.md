# SpigotGradle 
[![Build Status](https://travis-ci.org/FerusTech/SpigotGradle.svg?branch=master)](https://travis-ci.org/FerusTech/SpigotGradle)

SpigotGradle is a Gradle plugin meant to make certain tasks easier in terms of developing Spigot plugins.

## Installation

### Any Gradle Version

```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.tech.ferus.gradle:SpigotGradle:1.0.4"
  }
}

apply plugin: "tech.ferus.gradle.spigotgradle"
```

### Gradle 2.1+

```groovy
plugins {
  id "tech.ferus.gradle.spigotgradle" version "1.0.4"
}
```

## Configuration

You should be aware that you can choose to run SpigotGradle without any configuration at all. However, if you want to specify some things or programmatically identify parameters, take a look below:

```groovy
spigot {
    meta {
        name = project.name
        version = project.version
        description = project.description
        main = "tld.domain.myplugin.MyPlugin"
        website = "https://domain.tld"
        author = "FerusGrim"
        authors = ["FerusGrim"]
    }
}
```

Those are the normally accepted parameters in a `plugin.yml` file and they all work. :)

**Behavioral Notes:**
* If you have an existing `plugin.yml` file, the settings in this configuration section will **not** override the settings you've already configured.
* If you don't have an existing `plugin.yml`, this plugin will only create it at **compile time**.

## Usage

Simply applying the plugin will ensure that it runs. However, for clarification, the current tasks are listed below.

**Gradle Tasks:**
- `generatePluginData`
