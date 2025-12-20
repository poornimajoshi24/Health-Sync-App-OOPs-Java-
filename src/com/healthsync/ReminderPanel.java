package com.healthsync;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ReminderPanel extends JPanel {

    public ReminderPanel(ReportPanel reportPanel) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // base white for gradient effect

        // --- Gradient background panel ---
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(255, 200, 200),
                        0, h, new Color(255, 150, 150)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        gradientPanel.setLayout(new GridBagLayout());
        add(gradientPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Header with border & shadow effect ---
        JLabel header = new JLabel("⏰ Your Reminders", JLabel.CENTER);
        header.setFont(new Font("Rubik", Font.BOLD, 30));
        header.setForeground(new Color(153, 0, 51));
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(153, 0, 51), 3, true),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        gradientPanel.add(header, gbc);

        // --- Input Fields (pill shaped) ---
        JTextField msgField = new JTextField("Enter reminder message");
        JTextField timeField = new JTextField("Time (optional, e.g., 08:00 AM)");
        JTextField[] fields = {msgField, timeField};

        for (JTextField f : fields) {
            f.setForeground(Color.BLACK);
            f.setFont(new Font("Rubik", Font.PLAIN, 18));
            f.setBackground(Color.WHITE);
            f.setBorder(new LineBorder(new Color(255, 100, 100), 2, true));
            f.setPreferredSize(new Dimension(350, 50));
            f.setOpaque(true);

            f.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (f.getText().equals("Enter reminder message") ||
                            f.getText().equals("Time (optional, e.g., 08:00 AM)")) {
                        f.setText("");
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (f.getText().isEmpty()) {
                        if (f == msgField) f.setText("Enter reminder message");
                        else if (f == timeField) f.setText("Time (optional, e.g., 08:00 AM)");
                        f.setForeground(Color.BLACK);
                    }
                }
            });
        }

        gbc.gridy = 1; gradientPanel.add(msgField, gbc);
        gbc.gridy = 2; gradientPanel.add(timeField, gbc);

        // --- Button with gradient hover effect ---
        JButton addButton = new JButton("Add Reminder") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(255, 100, 100),
                        0, getHeight(), new Color(204, 0, 51)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Rubik", Font.BOLD, 18));
        addButton.setContentAreaFilled(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addButton.addActionListener(e -> {
            String msg = msgField.getText().trim();
            if (msg.isEmpty() || msg.equals("Enter reminder message")) {
                JOptionPane.showMessageDialog(this, "Please enter a reminder message!");
                return;
            }
            HealthSyncApp.reminders.add(new ReminderEntry(msg, timeField.getText().trim()));
            msgField.setText("Enter reminder message");
            timeField.setText("Time (optional, e.g., 08:00 AM)");
            JOptionPane.showMessageDialog(this, "Reminder added successfully!");
            reportPanel.updateReport();
        });

        gbc.gridy = 3; gradientPanel.add(addButton, gbc);

        // --- Footer sticky note style ---
        JLabel footer = new JLabel("💡 Tip: Set reminders to stay productive!", JLabel.CENTER);
        footer.setFont(new Font("Rubik", Font.ITALIC, 16));
        footer.setForeground(new Color(102, 0, 51));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 102, 102), 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        gbc.gridy = 4; gbc.insets = new Insets(20, 12, 20, 12);
        gradientPanel.add(footer, gbc);
    }
}
