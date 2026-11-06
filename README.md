# GTA Radio Hub — Android prototype

This is an early Android Studio Kotlin prototype for a landscape car-screen radio UI.

## Included
- Landscape orientation and dark neon UI
- Swipe left/right to change game or station
- Basic station tuning transition placeholder
- Starter list of GTA games and radio stations

## Not yet included
- GTA intro/title video assets
- Real station logos/artwork
- Audio import and playlist editing
- Actual static/tuning audio asset
- Background playback/media notification
- APK build

## Build
Open this folder in Android Studio (recent version with Android Gradle Plugin 8.7.3), sync Gradle, then Run on an Android device/emulator.

To add real audio, implement Android's document picker and Media3/MediaPlayer playback; use only audio/video files you own or are licensed to use.

## Build APK from an Android phone (cloud build)
1. Create a GitHub account and a new repository.
2. Upload the contents of this project folder to the repository (including the hidden `.github` folder).
3. Open the repository's **Actions** tab and enable Actions if prompted.
4. Choose **Build GTA Radio Hub APK** and tap **Run workflow**.
5. When the run finishes, open it and download the `GTA-Radio-Hub-debug` artifact. Extract the ZIP to get `app-debug.apk`.
6. Open the APK on the phone and allow installation from that source if Android asks.

This workflow builds in GitHub's cloud; Android Studio is not needed on the phone.
