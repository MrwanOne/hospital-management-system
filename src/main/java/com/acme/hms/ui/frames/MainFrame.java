package com.acme.hms.ui.frames;

import com.acme.hms.controller.NavigationController;
import com.acme.hms.util.LocalizationUtil;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/**
 * Main application window containing the sidebar and content area.
 */
public class MainFrame extends JFrame {
    private final NavigationController navigationController;
    private JPanel contentPanel;

    public MainFrame(NavigationController navigationController) {
        this.navigationController = navigationController;
        initComponents();
        this.navigationController.addPropertyChangeListener(event -> {
            if (NavigationController.PROPERTY_ACTIVE_VIEW.equals(event.getPropertyName())) {
                CardLayout layout = (CardLayout) contentPanel.getLayout();
                layout.show(contentPanel, (String) event.getNewValue());
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(LocalizationUtil.get("app.title"));
        setPreferredSize(new Dimension(1200, 800));
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel rootPanel = new JPanel();
        rootPanel.setBackground(Color.decode("#F7F9FC"));
        rootPanel.setLayout(new BorderLayout());

        JPanel sidebar = buildSidebar();
        contentPanel = buildContentPanel();

        rootPanel.add(sidebar, BorderLayout.WEST);
        rootPanel.add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(rootPanel);

        applyLocale(AppOrientation.fromLocale(Locale.forLanguageTag(LocalizationUtil.get("app.locale"))));
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.decode("#0F4C81"));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BorderLayout());

        JLabel header = new JLabel(LocalizationUtil.get("app.title"));
        header.setForeground(Color.WHITE);
        header.setHorizontalAlignment(JLabel.CENTER);
        header.setOpaque(true);
        header.setBackground(Color.decode("#0F4C81"));
        header.setPreferredSize(new Dimension(220, 80));

        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new javax.swing.BoxLayout(navPanel, javax.swing.BoxLayout.Y_AXIS));

        addNavButton(navPanel, "dashboard", LocalizationUtil.get("nav.dashboard"));
        addNavButton(navPanel, "patients", LocalizationUtil.get("nav.patients"));
        addNavButton(navPanel, "appointments", LocalizationUtil.get("nav.appointments"));
        addNavButton(navPanel, "visits", LocalizationUtil.get("nav.visits"));
        addNavButton(navPanel, "pharmacy", LocalizationUtil.get("nav.pharmacy"));
        addNavButton(navPanel, "lab", LocalizationUtil.get("nav.lab"));
        addNavButton(navPanel, "billing", LocalizationUtil.get("nav.billing"));
        addNavButton(navPanel, "users", LocalizationUtil.get("nav.users"));
        addNavButton(navPanel, "reports", LocalizationUtil.get("nav.reports"));
        addNavButton(navPanel, "settings", LocalizationUtil.get("nav.settings"));

        JScrollPane scrollPane = new JScrollPane(navPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        sidebar.add(header, BorderLayout.NORTH);
        sidebar.add(scrollPane, BorderLayout.CENTER);
        return sidebar;
    }

    private JPanel buildContentPanel() {
        JPanel panel = new JPanel(new CardLayout());
        panel.add(buildDashboardPanel(), "dashboard");
        panel.add(new JPanel(), "patients");
        panel.add(new JPanel(), "appointments");
        panel.add(new JPanel(), "visits");
        panel.add(new JPanel(), "pharmacy");
        panel.add(new JPanel(), "lab");
        panel.add(new JPanel(), "billing");
        panel.add(new JPanel(), "users");
        panel.add(new JPanel(), "reports");
        panel.add(new JPanel(), "settings");
        return panel;
    }

    private JPanel buildDashboardPanel() {
        JPanel dashboard = new JPanel();
        dashboard.setBackground(Color.WHITE);
        dashboard.setLayout(new BorderLayout());
        JLabel welcome = new JLabel(LocalizationUtil.get("welcome.message"));
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setForeground(Color.decode("#0B0C10"));
        dashboard.add(welcome, BorderLayout.CENTER);
        JToolBar statusBar = new JToolBar();
        statusBar.setFloatable(false);
        statusBar.add(new JLabel(LocalizationUtil.get("status.ready")));
        dashboard.add(statusBar, BorderLayout.SOUTH);
        return dashboard;
    }

    private void addNavButton(JPanel parent, String viewKey, String title) {
        JButton button = new JButton(title);
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.setBackground(Color.decode("#1B263B"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.addActionListener(event -> navigationController.setActiveView(viewKey));
        parent.add(button);
    }

    public void applyLocale(AppOrientation orientation) {
        boolean isRtl = orientation == AppOrientation.RTL;
        applyComponentOrientation(isRtl ? ComponentOrientation.RIGHT_TO_LEFT
                : ComponentOrientation.LEFT_TO_RIGHT);
    }

    /**
     * Supported orientation states.
     */
    public enum AppOrientation {
        LTR,
        RTL;

        public static AppOrientation fromLocale(Locale locale) {
            return "ar".equals(locale.getLanguage()) ? RTL : LTR;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame(new NavigationController()).setVisible(true));
    }
}
