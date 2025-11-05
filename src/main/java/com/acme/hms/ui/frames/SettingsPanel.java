package com.acme.hms.ui.frames;

import com.acme.hms.util.I18n;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel {

    public SettingsPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel label = new JLabel(I18n.tr("nav.settings"));
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addComponent(label)
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(label)
        );
    }
}
