package com.acme.hms;

import com.acme.hms.controller.NavigationController;
import com.acme.hms.ui.frames.MainFrame;
import javax.swing.SwingUtilities;

/**
 * Entry point launching the Swing UI.
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame(new NavigationController()).setVisible(true));
    }
}
