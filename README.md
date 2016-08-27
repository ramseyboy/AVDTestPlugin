[WIP] AVDTestPlugin
-------------

Gradle plugin i've been working on in my free time to create/run/then shut down
an Android emulator just for device tests.

Right now its just a buildSrc plugin for a sample app but when the time comes you can:

```
#install to local .m2 repo
./install.sh
```

In your projects build.gradle

```
buildscript {
    repositories {
        mavenLocal()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:${version}'
        classpath 'me.ramseyboy:avdtestplugin:0.1'
    }
}

apply plugin: 'com.android.application'
apply plugin: 'me.ramseyboy.avdtestplugin'

//plugin configuration
avdtest {
    avdName = "Marshmallow"
    targetId = "Google Inc.:Google APIs:23"
    abi = "google_apis/x86_64"
}

```

Then write some android device tests and run:

```
./gradlew connectedCheck
```

