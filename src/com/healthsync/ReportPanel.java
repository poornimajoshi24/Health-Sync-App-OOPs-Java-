package com.healthsync;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ReportPanel extends JPanel {
    private JPanel contentPanel;

    public ReportPanel() {
        setLayout(new BorderLayout());

        // Soft pastel gradient background for panel
        setOpaque(false);

        // Title
        JLabel title = new JLabel("Daily Health Report", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 34));
        title.setForeground(new Color(50, 50, 50));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Scrollable content panel
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(245, 240, 255),  // Top-left: soft pastel lavender
                        0, getHeight(), new Color(240, 255, 245) // Bottom: soft pastel mint
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        updateReport();
    }

    private JPanel createCard(String header, String content, Color cardColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow effect
                g2.setColor(new Color(180, 180, 180, 50));
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);

                // Card background
                g2.setColor(cardColor);
                g2.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 20, 20);
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // Header
        JLabel lblHeader = new JLabel(header);
        lblHeader.setFont(new Font("Poppins", Font.BOLD, 20));
        lblHeader.setForeground(new Color(50, 50, 50));
        lblHeader.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Content
        JTextArea area = new JTextArea(content);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        area.setOpaque(false);
        area.setForeground(new Color(60, 60, 60));

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        card.add(lblHeader, BorderLayout.NORTH);
        card.add(area, BorderLayout.CENTER);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(10, 20, 10, 20));
        wrapper.add(card, BorderLayout.CENTER);

        return wrapper;
    }

    public void updateReport() {
        contentPanel.removeAll();

        // Medicines - Pastel Pink
        StringBuilder meds = new StringBuilder();
        for (MedicineEntry m : HealthSyncApp.medicines) meds.append("• ").append(m.toString()).append("\n");
        contentPanel.add(createCard("💊 Medicines (" + HealthSyncApp.medicines.size() + ")", meds.toString(),
                new Color(255, 204, 204)));

        // Water - Pastel Blue
        int totalWater = HealthSyncApp.waterLogs.stream().mapToInt(Integer::intValue).sum();
        StringBuilder water = new StringBuilder();
        for (Integer v : HealthSyncApp.waterLogs) water.append("• ").append(v).append(" ml\n");
        contentPanel.add(createCard("💧 Water Logs - Total: " + totalWater + " ml (" + HealthSyncApp.waterLogs.size() + ")",
                water.toString(),
                new Color(204, 229, 255)));

        // Exercises - Pastel Green
        int totalDuration = HealthSyncApp.exercises.stream().mapToInt(ExerciseEntry::getDuration).sum();
        StringBuilder exercises = new StringBuilder();
        for (ExerciseEntry ex : HealthSyncApp.exercises) exercises.append("• ").append(ex.toString()).append("\n");
        contentPanel.add(createCard("🏃 Exercises - Total Duration: " + totalDuration + " mins (" + HealthSyncApp.exercises.size() + ")",
                exercises.toString(),
                new Color(204, 255, 204)));

        // Reminders - Pastel Purple
        StringBuilder reminders = new StringBuilder();
        for (ReminderEntry r : HealthSyncApp.reminders) reminders.append("• ").append(r.toString()).append("\n");
        contentPanel.add(createCard("⏰ Reminders (" + HealthSyncApp.reminders.size() + ")",
                reminders.toString(),
                new Color(229, 204, 255)));

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
