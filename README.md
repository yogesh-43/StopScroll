# ReelGuard — Short-video Feed Guard

Local-only Kotlin/Jetpack Compose short-video feed detection for Instagram, YouTube and Facebook. Uses explicit Accessibility permission and heuristic labels; best effort, not device-tested.

## Build
Open this repository in Android Studio, use JDK 17 / Android SDK 35, and sync Gradle. Enable the Accessibility service manually in Android settings.

## Privacy
All preferences and detection occur on the device; no account or server.

## Disclaimer
Android may deny background activity launches. Accessibility services can be disabled by the phone owner, so restrictions are not tamper-proof. This code is provided as-is under the MIT License.
