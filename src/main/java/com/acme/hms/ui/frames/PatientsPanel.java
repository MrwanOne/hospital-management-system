package com.acme.hms.ui.frames;

import com.acme.hms.util.I18n;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PatientsPanel extends JPanel {

    private JTable table;
    private JTextField searchField;

    public PatientsPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel(I18n.tr("patients.title"));
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        titleLabel.setForeground(Color.decode("#0B0C10"));

        searchField = new JTextField();
        searchField.setToolTipText(I18n.tr("patients.search"));

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(searchField)
                .addComponent(scrollPane)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(scrollPane)
        );
    }
}
