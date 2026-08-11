package com.dev.tkinter.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.xed.editor.extensions.ExtensionContext

/**
 * VNC Viewer Screen - Main UI component
 * 
 * Displays the VNC viewer using WebView with noVNC
 * Also provides status indicators and control buttons
 */
@Composable
fun VncViewerScreen(
    context: ExtensionContext,
    onOpenSettings: () -> Unit = {},
    onDiagnose: () -> Unit = {}
) {
    var isConnected by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf("Initializing...") }
    var isLoading by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    "Tkinter GUI Viewer",
                    modifier = Modifier.padding(start = 8.dp)
                )
            },
            actions = {
                IconButton(onClick = onOpenSettings) {
                    Icon(Icons.Default.Settings, "Settings")
                }
                IconButton(onClick = onDiagnose) {
                    Icon(Icons.Default.Build, "Diagnose")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        // Main VNC Viewer Area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color.Black)
        ) {
            // WebView for noVNC
            AndroidView(
                factory = { androidContext ->
                    WebView(androidContext).apply {
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            databaseEnabled = true
                            displayZoomControls = false
                        }

                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isConnected = true
                                isLoading = false
                                statusMessage = "Connected ✓"
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                errorCode: Int,
                                description: String?,
                                failingUrl: String?
                            ) {
                                super.onReceivedError(view, errorCode, description, failingUrl)
                                isConnected = false
                                isLoading = false
                                statusMessage = "Connection Error: $description"
                            }
                        }

                        // Load noVNC viewer HTML
                        loadUrl("file:///android_asset/vnc.html")
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            // Status Badge (Top-Right)
            if (!isConnected || isLoading) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = if (isConnected)
                        MaterialTheme.colorScheme.secondaryContainer
                    else
                        MaterialTheme.colorScheme.errorContainer,
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp, 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = if (isConnected)
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                else
                                    MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                        Text(
                            statusMessage,
                            color = if (isConnected)
                                MaterialTheme.colorScheme.onSecondaryContainer
                            else
                                MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            // Resolution Badge (Bottom-Right) - Optional
            if (isConnected) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8f)
                ) {
                    Text(
                        "720x1440",
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            // Loading Indicator (Center)
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Settings Screen - Configuration options
 */
@Composable
fun SettingsScreen(
    onClose: () -> Unit
) {
    var vncPort by remember { mutableStateOf("6080") }
    var geometry by remember { mutableStateOf("720x1440") }
    var colorDepth by remember { mutableStateOf("16") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Bar
        TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, "Close")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        // Settings Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "VNC Server Configuration",
                style = MaterialTheme.typography.headlineSmall
            )

            // VNC Port
            OutlinedTextField(
                value = vncPort,
                onValueChange = { vncPort = it },
                label = { Text("VNC Port") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )

            // Resolution
            OutlinedTextField(
                value = geometry,
                onValueChange = { geometry = it },
                label = { Text("Resolution (WIDTHxHEIGHT)") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("e.g., 720x1440") }
            )

            // Color Depth
            OutlinedTextField(
                value = colorDepth,
                onValueChange = { colorDepth = it },
                label = { Text("Color Depth (bits)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                ),
                supportingText = { Text("16 = faster, 24 = better quality") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Save settings */ },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
            ) {
                Text("Save Settings")
            }
        }
    }
}

@androidx.compose.runtime.Composable
private fun rememberScrollState(): androidx.compose.foundation.ScrollState {
    return remember { androidx.compose.foundation.ScrollState(0) }
}
