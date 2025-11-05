package com.acme.hms.ui.frames;

import com.acme.hms.util.I18n;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    public MainFrame() {
        initUi();
    }

    private void initUi() {
        setTitle(I18n.tr("app.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createSidebar(), BorderLayout.WEST);
        root.add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(createPlaceholder(I18n.tr("nav.dashboard")), "dashboard");
        contentPanel.add(new PatientsPanel(), "patients");
        contentPanel.add(new AppointmentsPanel(), "appointments");
        contentPanel.add(new VisitsPanel(), "visits");
        contentPanel.add(new PharmacyPanel(), "pharmacy");
        contentPanel.add(new LabPanel(), "lab");
        contentPanel.add(new BillingPanel(), "billing");
        contentPanel.add(new UsersPanel(), "users");
        contentPanel.add(new ReportsPanel(), "reports");
        contentPanel.add(new SettingsPanel(), "settings");
        cardLayout.show(contentPanel, "dashboard");

        setContentPane(root);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(Color.decode("#0F4C81"));
        JLabel title = new JLabel(I18n.tr("app.title"));
        title.setForeground(Color.decode("#F7F9FC"));
        title.setFont(title.getFont().deriveFont(18f));
        header.add(title);
        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.decode("#1B263B"));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new javax.swing.BoxLayout(sidebar, javax.swing.BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 16, 24, 16));

        sidebar.add(navButton(I18n.tr("nav.dashboard"), e -> cardLayout.show(contentPanel, "dashboard")));
        sidebar.add(navButton(I18n.tr("nav.patients"), e -> cardLayout.show(contentPanel, "patients")));
        sidebar.add(navButton(I18n.tr("nav.appointments"), this::showAppointments));
        sidebar.add(navButton(I18n.tr("nav.visits"), this::showVisits));
        sidebar.add(navButton(I18n.tr("nav.pharmacy"), this::showPharmacy));
        sidebar.add(navButton(I18n.tr("nav.lab"), this::showLab));
        sidebar.add(navButton(I18n.tr("nav.billing"), this::showBilling));
        sidebar.add(navButton(I18n.tr("nav.users"), this::showUsers));
        sidebar.add(navButton(I18n.tr("nav.reports"), this::showReports));
        sidebar.add(navButton(I18n.tr("nav.settings"), this::showSettings));
        return sidebar;
    }

    private JButton navButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBackground(Color.decode("#0F4C81"));
        button.setForeground(Color.decode("#F7F9FC"));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.addActionListener(listener);
        return button;
    }

    private JPanel createPlaceholder(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#F7F9FC"));
        panel.add(new JLabel(title));
        return panel;
    }

    private void showAppointments(ActionEvent event) {
        cardLayout.show(contentPanel, "appointments");
    }

    private void showVisits(ActionEvent event) {
        cardLayout.show(contentPanel, "visits");
    }

    private void showPharmacy(ActionEvent event) {
        cardLayout.show(contentPanel, "pharmacy");
    }

    private void showLab(ActionEvent event) {
        cardLayout.show(contentPanel, "lab");
    }

    private void showBilling(ActionEvent event) {
        cardLayout.show(contentPanel, "billing");
    }

    private void showUsers(ActionEvent event) {
        cardLayout.show(contentPanel, "users");
    }

    private void showReports(ActionEvent event) {
        cardLayout.show(contentPanel, "reports");
    }

    private void showSettings(ActionEvent event) {
        cardLayout.show(contentPanel, "settings");
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
