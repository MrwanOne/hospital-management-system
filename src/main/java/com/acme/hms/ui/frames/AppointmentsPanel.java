package com.acme.hms.ui.frames;

import com.acme.hms.util.I18n;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AppointmentsPanel extends JPanel {

    private JTable calendarTable;

    public AppointmentsPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel(I18n.tr("appointments.title"));
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        titleLabel.setForeground(Color.decode("#0B0C10"));

        calendarTable = new JTable();
        calendarTable.setRowHeight(48);
        JScrollPane scrollPane = new JScrollPane(calendarTable);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(scrollPane)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addComponent(scrollPane)
        );
    }
}
