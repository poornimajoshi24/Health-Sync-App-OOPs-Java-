package com.healthsync;

import javax.swing.*;
import java.awt.*;

public class HealthSyncApp extends JFrame {
    public static java.util.List<MedicineEntry> medicines = new java.util.ArrayList<>();
    public static java.util.List<Integer> waterLogs = new java.util.ArrayList<>(); // store ml as integers
    public static java.util.List<ExerciseEntry> exercises = new java.util.ArrayList<>();
    public static java.util.List<ReminderEntry> reminders = new java.util.ArrayList<>();

    private ReportPanel reportPanel;

    public HealthSyncApp() {
        super("HealthSync - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
       // setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(Color.WHITE);

        reportPanel = new ReportPanel();
        tabs.addTab("  Dashboard", new DashboardPanel());
        tabs.addTab("  Medicines", new MedicinePanel(reportPanel));
        tabs.addTab("  Water", new WaterLogPanel(reportPanel));
        tabs.addTab("  Exercises", new ExercisesPanel(reportPanel));
        tabs.addTab("  Reminders", new ReminderPanel(reportPanel));
       tabs.addTab("  Reports", reportPanel);



        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            HealthSyncApp app = new HealthSyncApp();
            app.setVisible(true);
        });
    }
}


