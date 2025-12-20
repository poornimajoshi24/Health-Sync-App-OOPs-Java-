package com.healthsync;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WaterLogPanel extends JPanel {

    public WaterLogPanel(ReportPanel reportPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 230, 255)); // soft light blue background

        // --- Header ---
        JLabel header = new JLabel("💧 Water Intake Log", JLabel.CENTER);
        header.setFont(new Font("Rubik", Font.BOLD, 30));
        header.setForeground(new Color(0, 70, 140)); // deep blue
        header.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // subtle vertical gradient
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(180, 220, 255),
                        0, getHeight(), new Color(150, 200, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 100, 200), 2),
                "Add Water Intake (ml)", 0, 0,
                new Font("Rubik", Font.BOLD, 16), new Color(0, 60, 140)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField waterField = new JTextField();
        waterField.setFont(new Font("Rubik", Font.PLAIN, 18));
        waterField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 100, 200), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        waterField.setForeground(new Color(0, 0, 128)); // navy text
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(waterField, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // --- Buttons Panel ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnPanel.setBackground(new Color(200, 230, 255));

        JButton addBtn = new JButton("Add Water");
        styleButton(addBtn, new Color(0, 100, 200), new Color(0, 60, 180), Color.WHITE);
        addBtn.setFont(new Font("Rubik", Font.BOLD, 18));
        addBtn.addActionListener(e -> {
            String s = waterField.getText().trim();
            try {
                int ml = Integer.parseInt(s);
                if (ml <= 0) {
                    JOptionPane.showMessageDialog(this, "Enter a positive ml value");
                    return;
                }
                HealthSyncApp.waterLogs.add(ml);
                waterField.setText("");
                JOptionPane.showMessageDialog(this, "Water log added successfully!");
                reportPanel.updateReport();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter numeric value (ml)");
            }
        });

        JButton clearBtn = new JButton("Clear");
        styleButton(clearBtn, new Color(100, 100, 255), new Color(60, 60, 200), Color.WHITE);
        clearBtn.setFont(new Font("Rubik", Font.BOLD, 18));
        clearBtn.addActionListener(e -> waterField.setText(""));

        btnPanel.add(clearBtn);
        btnPanel.add(addBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // --- Optional: Water background decoration ---
        JLabel waterIcon = new JLabel("💦");
        waterIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        waterIcon.setForeground(new Color(0, 150, 255, 80)); // semi-transparent
        waterIcon.setHorizontalAlignment(SwingConstants.CENTER);
        waterIcon.setVerticalAlignment(SwingConstants.CENTER);
        add(waterIcon, BorderLayout.EAST);
    }

    private void styleButton(JButton button, Color base, Color hover, Color textColor) {
        button.setBackground(base);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { button.setBackground(hover); }
            @Override
            public void mouseExited(MouseEvent e) { button.setBackground(base); }
        });
    }
}
