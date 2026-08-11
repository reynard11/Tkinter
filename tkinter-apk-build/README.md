# Tkinter GUI Viewer - Xed Editor Extension

View Python Tkinter GUIs through VNC directly in Xed Editor!

## Features

✨ **Key Features:**
- 🖥️ View Tkinter GUI windows live in Xed Editor
- 🔧 Automatic VNC server setup
- 🌉 WebSocket bridge for secure connection
- 📱 Works on phones, tablets, and desktops
- ⚡ Fast startup (3-5 seconds)
- 🎨 Full color support (16-bit or 24-bit)
- 📋 Built-in diagnostics and troubleshooting

## Prerequisites

### On Your Computer (Development)
- **Android Studio** (free download from https://developer.android.com/studio)
- **Java/JDK 11 or higher**
- **Gradle** (usually bundled with Android Studio)
- **Kotlin plugin** (automatic with Android Studio)

### On Your Phone (Runtime)
- **Xed Editor** installed
- **Alpine or Ubuntu terminal** available in Xed
- **Python 3** (for running your Tkinter scripts)

## Building the APK

### Step 1: Extract the Project
```bash
# Unzip this file
unzip xed-tkinter-viewer.zip
cd xed-tkinter-viewer
```

### Step 2: Open in Android Studio

**Option A: From Android Studio**
```
1. Open Android Studio
2. File → Open → Select xed-tkinter-viewer folder
3. Wait for Gradle sync to complete
4. Android Studio will download dependencies automatically
```

**Option B: From Command Line**
```bash
# Navigate to project
cd xed-tkinter-viewer

# Build debug APK
./gradlew assembleDebug

# Or build release APK
./gradlew assembleRelease

# Output will be in:
# build/outputs/apk/debug/   OR
# build/outputs/apk/release/
```

### Step 3: Sign the APK (Release Only)

For debug builds, skip this step. For distribution:

```bash
# Create a keystore (one-time)
keytool -genkey -v -keystore release.keystore \
  -keyalg RSA -keysize 2048 -validity 365 \
  -alias release

# Sign the APK
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
  -keystore release.keystore \
  build/outputs/apk/release/xed-tkinter-viewer-release.apk release
```

### Step 4: Install on Phone

**Option A: Via ADB (USB Connection)**
```bash
adb install -r build/outputs/apk/debug/xed-tkinter-viewer-debug.apk
```

**Option B: Copy APK to Phone**
```bash
# Copy APK file to phone storage
adb push build/outputs/apk/debug/xed-tkinter-viewer-debug.apk /sdcard/Download/

# Then on phone:
# 1. Open Files
# 2. Navigate to Download
# 3. Tap the APK
# 4. Confirm installation
```

**Option C: Via Xed Editor**
```
1. Transfer APK to phone (cloud drive, email, etc.)
2. Open Xed Editor
3. Tap Store → Extensions
4. Tap "Install from storage" button
5. Browse and select the APK
6. Confirm installation ✓
```

## Installation in Xed Editor

Once the APK is built and transferred to your phone:

1. **Open Xed Editor**
2. **Tap the menu** (hamburger icon or dots)
3. **Select "Store"** or **"Extensions"**
4. **Tap "Install from storage"** button
5. **Browse** to select the APK file
6. **Confirm** the installation

The extension should now appear in your extensions list!

## Usage

### First Time Setup (per session)

1. Open Xed Editor
2. Press **Ctrl+Shift+P** (or Cmd+Shift+P on Mac) to open command palette
3. Search and run: **"Tkinter GUI: Start & Run"**
   - This installs required packages (one-time on first run)
   - Starts VNC server
   - Starts websockify bridge
   - Configures environment variables
4. Wait for status confirmation

### Running Your Tkinter Script

1. Create or open your Python Tkinter script in Xed
2. Press the **Run** button (▶) - same as usual
3. Your Tkinter GUI will appear in a new viewer tab!
4. If the viewer doesn't open, run: **"Tkinter GUI: Open Viewer"**

### Stopping the Display

When done:
1. Press **Ctrl+Shift+P** and run: **"Tkinter GUI: Stop GUI Display"**
2. Or close the viewer tab (disconnects)

### Troubleshooting

If something isn't working:
1. Press **Ctrl+Shift+P** and run: **"Tkinter GUI: Diagnose"**
2. Check the output for which services are running/not running
3. Check the VNC and websockify logs for error messages

## Project Structure

```
xed-tkinter-viewer/
├── manifest.json                           # Extension metadata
├── build.gradle.kts                        # Gradle build configuration
├── settings.gradle.kts                     # Gradle settings
├── README.md                               # This file
│
├── src/main/
│   ├── kotlin/com/dev/tkinter/
│   │   ├── Main.kt                         # Entry point / Extension loader
│   │   ├── VncManager.kt                   # VNC lifecycle management
│   │   └── ui/
│   │       └── VncViewerScreen.kt          # UI components
│   │
│   ├── res/
│   │   └── values/
│   │       └── strings.xml                 # String resources
│   │
│   └── assets/
│       └── vnc.html                        # noVNC viewer interface
│
└── build/
    └── outputs/apk/
        ├── debug/                          # Debug APK (for testing)
        └── release/                        # Release APK (for distribution)
```

## Development

### Key Classes

- **Main.kt**: Entry point, registers commands
- **VncManager.kt**: Handles VNC server lifecycle (start, stop, diagnose)
- **VncViewerScreen.kt**: Jetpack Compose UI components

### Adding New Features

1. Edit the appropriate `.kt` file
2. In Android Studio, click **Run → Run 'app'**
3. Or build and test with: `./gradlew assembleDebug`

## Troubleshooting

### "Extension fails to load"
- Check that Java JDK 11+ is installed
- Verify all dependencies downloaded successfully
- Check Android Studio's build output for errors

### "APK won't install"
- Make sure you're signing the release APK properly
- Check that your phone allows installation from unknown sources
- Uninstall any previous version first

### "VNC won't start"
- Run the **Diagnose** command to see what's failing
- Check internet connection (for first-time package downloads)
- Ensure sufficient disk space on phone

### "Can't see GUI window"
- Make sure you ran **"Start & Run"** first
- Check that your Python script is error-free
- Run **Diagnose** to verify VNC is running
- Try running the script manually from terminal to check for errors

## Performance Notes

- **Resolution**: 720×1440 (optimized for mobile)
- **Color Depth**: 16-bit (faster) or 24-bit (better quality)
- **Startup**: 3-5 seconds typical
- **FPS**: 10-30 depending on device and changes

You can modify these in `VncManager.kt` constants:
```kotlin
const val VNC_GEOMETRY = "720x1440"      // Change resolution
const val VNC_DEPTH = "16"                // 16 or 24 for color depth
```

## Publishing

To share this extension with others:

1. **Sign the release APK**
2. **Upload to GitHub Releases** (or your preferred platform)
3. **Create a GitHub repository** with this code
4. **Submit to Xed Extension Registry** (if participating)
5. **Users can install** via "Install from storage" or extension store

## License

MIT License - Feel free to modify and distribute!

## Support

- **Xed Editor Discord**: https://discord.gg/6bKzcQRuef
- **Xed Documentation**: https://xed-editor.github.io/Xed-Docs/
- **noVNC Project**: https://novnc.com/
- **TigerVNC Project**: https://tigervnc.org/

## Changelog

### Version 1.2.0 (First Xed Release)
- ✨ Converted from Acode to Xed Editor
- ✨ Kotlin + Jetpack Compose implementation
- ✨ Built-in diagnostics
- ✨ Commands: Start, Stop, Open Viewer, Diagnose
- ✨ Settings support
- ✨ Full VNC lifecycle management

---

**Happy Tkinter GUI viewing!** 🎨✨

Need help? Check the Xed Editor documentation or open an issue on GitHub!
