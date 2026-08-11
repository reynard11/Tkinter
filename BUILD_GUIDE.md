# 📱 Build Tkinter APK on XED Editor Terminal

## Prerequisites

Make sure you have installed (run in XED terminal):

```bash
apt install wget unzip -y
apt install openjdk-21-jdk gradle -y
```

---

## Step 1: Prepare the Project

```bash
# Go to project directory
cd ~/MyProjects/tkinter-apk

# Verify structure
ls -la
```

You should see:
- `build.gradle.kts` ← Build configuration
- `settings.gradle` ← Project settings
- `gradle.properties` ← Gradle properties
- `src/` ← Source code
- `proguard-rules.pro` ← Optimization rules
- `AndroidManifest.xml` ← App manifest

---

## Step 2: Build the APK

### Option A: Simple Build

```bash
# Go to project directory
cd ~/MyProjects/tkinter-apk

# Build debug APK (faster)
gradle clean build
```

### Option B: Release Build (slower, better optimization)

```bash
gradle clean build -Pbuild=release
```

---

## Step 3: Find Your APK

After build completes:

```bash
# Find all APK files
find . -name "*.apk" -type f

# Typical locations:
# Debug: app/build/outputs/apk/debug/app-debug.apk
# Release: app/build/outputs/apk/release/app-release.apk
```

---

## Step 4: Install on Phone

### Option A: Using ADB (if available)

```bash
# List connected devices
adb devices

# Install
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Option B: Manual Installation

1. Copy APK to phone storage
2. Use file manager to open it
3. Allow installation from unknown sources
4. Tap install

---

## Troubleshooting

### Error: "Could not find or load main class"

**Solution:**
```bash
# Make sure gradle is installed
gradle --version

# If not found, install it
apt install gradle -y
```

### Error: "Java not found"

**Solution:**
```bash
# Install Java
apt install openjdk-21-jdk -y

# Verify
java -version
```

### Build takes too long

**Solution:**
```bash
# Use offline build (faster)
gradle clean build --offline

# Or increase memory
export GRADLE_OPTS="-Xmx1024m"
gradle clean build
```

### OutOfMemory error

**Solution:**
```bash
# Reduce parallel tasks
gradle clean build -Dorg.gradle.parallel=false
```

---

## What Gets Built

- `app-debug.apk` - Debug version (faster build, larger file)
- `app-release.apk` - Release version (slower build, optimized)

Choose **debug** for testing, **release** for distribution.

---

## Fast Build Commands

```bash
# Clean only (no build)
gradle clean

# Build only (no clean)
gradle build

# Build and show tasks
gradle tasks

# View dependencies
gradle dependencies
```

---

## Estimate Build Time

- **First build**: 3-5 minutes (downloads dependencies)
- **Incremental build**: 1-2 minutes
- **Release build**: 2-4 minutes

---

## After Successful Build

APK is ready to:
- ✅ Install on your phone
- ✅ Share with others
- ✅ Publish on Play Store (after signing)
- ✅ Test with other devices

---

**Happy building! 🎉**

For more help: Check Gradle documentation or Android Developer docs
