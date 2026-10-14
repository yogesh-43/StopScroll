# StopScroll / ReelGuard — Offline Reels & Shorts Feed Guard

An **open-source Android prototype** for reducing short-form-video scrolling. The repository is named **StopScroll**, while the Android app displays **ReelGuard** (package `org.openfocus.reelguard`). Built with **Kotlin + Jetpack Compose**, intended to work **locally on one phone**, without signup, cloud services or an external backend.

> **Prototype / best-effort detection:** this project has not been compiled or validated against current app versions on a real device. It relies on accessibility text and view identifiers, which may change frequently. It does **not** guarantee blocking every Reel or Short.

## How it works

1. In ReelGuard, enable the global guard and choose which supported apps to watch.
2. Manually enable the ReelGuard **Accessibility Service** in Android settings.
3. When Instagram, YouTube or Facebook is on screen, the service samples the currently accessible view tree (labels, content descriptions and resource IDs).
4. `ReelDetector.matches(...)` uses **conservative, app-specific heuristics** to decide whether it appears to be a short-video feed.
5. On detection, the service invokes Android's **Home** action, increments a local counter, and **attempts** to open a restriction message. Android may block launching that activity from the background.

The intent is to leave normal Instagram, YouTube and Facebook usable. Because their interfaces can overlap, both **false positives and missed detections are possible**.

## Implemented features

| Feature | Present in source | Notes |
| --- | --- | --- |
| Global enable/disable | Yes | Preference toggle in `MainActivity`. |
| Instagram Reels recognition | Heuristic | Looks for combinations of Reels-related labels and view IDs; may miss localized or updated layouts. |
| YouTube Shorts recognition | Heuristic | Looks for Shorts player/video labels or matching IDs. |
| Facebook Reels recognition | Heuristic | Similar matching of Reels labels/view IDs. |
| Per-app switches | Yes | Enable each of the three supported package IDs independently. |
| Return to Home | Best effort | Accessibility `GLOBAL_ACTION_HOME` when a feed is detected. |
| Restriction message | Best effort | `BlockedActivity` is launched with new-task flags; Android may suppress launch. |
| Blocked-detection count | Yes | Locally increments a cumulative detection counter. Not an accurate count of videos watched. |
| Anti-loop cooldown | Yes | 350 ms scan throttling and 2.5 s gap between redirects. |
| Offline operation | Yes | No network integration or backend. |
| Unit tests | Minimal | Two detector tests: one Shorts match and one normal-video non-match. |
| Daily limits / schedules / focus timers | **No** | Not implemented in this repository. |
| Scroll gesture prevention inside feeds | **No** | Responds after view changes; does not intercept touch input directly. |
| Full application blocking | **No** | Designed to detect specific short-video interfaces, not block the entire host app. |
| Tamper-proof lockdown | **No** | Accessibility can be disabled and the application can be uninstalled. |

## Important Android permissions

The app declares **`BIND_ACCESSIBILITY_SERVICE`** on its Accessibility Service; the Android system, not the application, grants service binding after the user explicitly enables it. It requests window/content-change events and permission to inspect accessible view content. No `INTERNET` or Usage Access permission is declared.

Accessibility access can reveal sensitive onscreen text while the service is active. In the current code, it **does not transmit or persist collected labels**; it uses them only for a local feed-detection decision.

## Code map

```text
app/src/main/
  AndroidManifest.xml
  java/org/openfocus/reelguard/
    MainActivity.kt                 Compose UI and app/global switches
    GuardSettings.kt                SharedPreferences settings and detection count
    ReelAccessibilityService.kt     Accessibility events, view traversal, Home redirect
    ReelDetector.kt                 App-specific feed detection rules
    BlockedActivity.kt              Optional restriction-message screen
  res/xml/accessibility_service.xml Accessibility-service configuration
  res/values/strings.xml            App label and service disclosure
app/src/test/
  java/org/openfocus/reelguard/ReelDetectorTest.kt
```

### Detector flow

```text
Accessibility event
   -> supported package? / global and per-app switches enabled?
   -> rate limiting
   -> inspect up to 350 accessible labels, maximum depth 16
   -> ReelDetector.matches(package, labels, viewIds)
   -> if match: increment count + Android Home action
   -> attempt BlockedActivity (may be disallowed)
```

The detector checks `com.instagram.android`, `com.google.android.youtube` and `com.facebook.katana`. Other variants (Instagram Lite, Facebook Lite, cloned apps or other video platforms) are currently unsupported.

## Build and run

1. Install **Android Studio**, **JDK 17** and **Android SDK 35**.
2. Clone: `git clone https://github.com/yogesh-43/StopScroll.git`.
3. Open the directory in Android Studio and sync using **Gradle JDK 17**.
4. Run the **app** configuration on Android 8.0+.
5. In the ReelGuard UI, choose the target apps.
6. Tap **Open Accessibility Settings** and enable **ReelGuard**; acknowledge the system warning only after reviewing what access it grants.
7. Open Instagram, YouTube and Facebook on a test device and observe whether the detector works with your particular versions.

The repository currently omits a Gradle wrapper; use a compatible local Gradle installation or generate one. It uses Android Gradle Plugin 8.7.3, Kotlin 2.0.21 and Compose BOM 2024.12.01. **Compilation has not been verified in this environment.**

## Tests and known gaps

The included `ReelDetectorTest` covers two simple cases only; real-world view trees and UI changes need automated/instrumented tests. Detection is heuristic and language/layout dependent. Android may suppress background activity starts; falling back to Home is attempted. The app cannot prevent users from turning off Accessibility, reopening the feed, clearing data or uninstalling.

A future improvement could add opt-in diagnostic mode (with sensitive data redacted), UI-specific test fixtures, localization support, configurable daily limits, and broader platform coverage—but those are **not implemented** in this version.

## Privacy, Play Store policy and contributions

No account, network, analytics SDK or server is used. Settings and the count are stored in local SharedPreferences. Accessibility must be user-initiated and clearly disclosed. Before distributing through Google Play, review Google's Accessibility API policy and test behavior across devices.

Contributions, test cases and bug reports are welcome. Please do not upload screenshots or accessibility dumps containing other people's personal information.

## License

[MIT](LICENSE).
