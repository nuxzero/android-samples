# Sign App with Command Line

## Generate Private Key

Create a private key with `keyetool`

```bash
keytool -genkey -v -keystore [KEY_STORE_PATH] -keyalg RSA -keysize 2048 -validity 10000 -alias [ALIAS_NAME]
```
Sample
```bash
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-alias
```

## Gradle Config

```groovy
signingConfigs {
  release {
    storeFile file("../test-release-key.jks")
    storePassword "testapp"
    keyAlias "test-alias"
    keyPassword "testapp"
  }
}

buildTypes {
  release {
    signingConfig signingConfigs.release
    ...
  }
}
```

Use variables from the system environment

```groovy
signingConfigs {
    release {
        storeFile file("../test-release-key.jks")
        storePassword System.getenv("KEYSTORE_PASSWORD")
        keyAlias System.getenv("KEY_ALIAS")
        keyPassword System.getenv("KEY_PASSWORD")
    }
}

buildTypes {
    release {
        signingConfig signingConfigs.release
        ...
    }
}
```

Set environment variables in terminal

```bash
export KEYSTORE_PASSWORD=testapp \
export KEY_ALIAS=test-alias \
export KEY_PASSWORD=testapp
```

## Build Sign App

Build project

```bash
./gradlew buildRelease
```

Create sign APK

```bash
./gradlew assembleRelease
```

Create sign App Bundle

```bash
./gradlew bundleRelease
```

## References

- https://medium.com/modulotech/how-to-sign-an-unsigned-apk-using-command-line-636a056373a0
- https://developer.android.com/studio/build/building-cmdline#gradle_signing
