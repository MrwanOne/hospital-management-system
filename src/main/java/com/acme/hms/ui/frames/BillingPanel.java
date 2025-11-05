package com.acme.hms.ui.frames;

import com.acme.hms.util.LocalizationUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Simple placeholder panel for the billing view.
 */
public class BillingPanel extends JPanel {
    public BillingPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel label = new JLabel(LocalizationUtil.get("nav.billing"));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
