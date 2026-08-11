#!/bin/bash

# TkinterViewer APK Build Script for XED Editor Terminal

echo "========================================="
echo "  Tkinter VNC Viewer - APK Builder"
echo "========================================="
echo ""

# Check Java
echo "✓ Checking Java..."
java -version

# Check Gradle
echo ""
echo "✓ Checking Gradle..."
gradle --version

# Build
echo ""
echo "========================================="
echo "Starting APK Build..."
echo "========================================="
echo ""

gradle clean build

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
    echo "APK location:"
    find . -name "*.apk" -type f 2>/dev/null | head -5
    echo ""
    echo "To install:"
    echo "  adb install -r app/build/outputs/apk/debug/app-debug.apk"
else
    echo ""
    echo "❌ Build failed!"
    echo "Check errors above"
fi
