## To build app without Android Studio

```
./gradlew task build --build-cache
```
The built APK is put in `app/build/outputs/apk`.

## To install app on connected phone

```
./gradlew task installDebug --build-cache
