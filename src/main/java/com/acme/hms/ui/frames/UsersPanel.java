package com.acme.hms.ui.frames;

import com.acme.hms.util.LocalizationUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Simple placeholder panel for the users view.
 */
public class UsersPanel extends JPanel {
    public UsersPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel label = new JLabel(LocalizationUtil.get("nav.users"));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
