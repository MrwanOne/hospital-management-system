package com.acme.hms;

import com.acme.hms.config.AppConfig;
import com.acme.hms.config.Database;
import com.acme.hms.ui.frames.MainFrame;
import com.acme.hms.util.I18n;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private Main() {
    }

    public static void main(String[] args) {
        setupUiDefaults();
        Database.init();
        I18n.reload();
        MainFrame.launch();
        Runtime.getRuntime().addShutdownHook(new Thread(Database::shutdown));
    }

    private static void setupUiDefaults() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("control", java.awt.Color.decode(AppConfig.getOrDefault("app.backgroundColor", "#F7F9FC")));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOG.warn("Unable to set system look and feel: {}", ex.getMessage());
        }
    }
}
