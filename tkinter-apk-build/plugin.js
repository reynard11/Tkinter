/**
 * Tkinter GUI Viewer - XED Editor Extension
 * View Python Tkinter GUIs through VNC directly in Xed Editor
 */

class TkinterViewerExtension {
  constructor(context) {
    this.context = context;
  }

  async onLoad() {
    console.log('Tkinter GUI Viewer Extension loaded');
  }

  async onUnload() {
    console.log('Tkinter GUI Viewer Extension unloaded');
  }

  async onActivate() {
    console.log('Tkinter GUI Viewer Extension activated');
    // Show VNC viewer screen
    if (this.context && this.context.showCustomScreen) {
      this.context.showCustomScreen('com.dev.tkinter.ui.VncViewerScreen');
    }
  }

  async onDeactivate() {
    console.log('Tkinter GUI Viewer Extension deactivated');
  }
}

// Export for XED Editor
if (typeof module !== 'undefined' && module.exports) {
  module.exports = TkinterViewerExtension;
}
