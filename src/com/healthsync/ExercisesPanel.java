package com.healthsync;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ExercisesPanel extends JPanel {

    public ExercisesPanel(ReportPanel reportPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 245, 230)); // soft warm background

        // --- Header ---
        JLabel header = new JLabel("🏃 Log Your Exercise", JLabel.CENTER);
        header.setFont(new Font("Rubik", Font.BOLD, 28));
        header.setForeground(new Color(204, 102, 0)); // deep orange
        header.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // --- Center Panel for inputs ---
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(255, 245, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Input Fields with placeholders ---
        JTextField nameField = new JTextField("Exercise Name");
        JTextField durField = new JTextField("Duration in minutes");
        JTextField notesField = new JTextField("Notes (optional)");

        JTextField[] fields = {nameField, durField, notesField};
        for (JTextField f : fields) {
            f.setForeground(Color.BLACK); // placeholder text color
            f.setFont(new Font("Rubik", Font.PLAIN, 18));
            f.setBackground(Color.WHITE);
            f.setBorder(new LineBorder(new Color(255, 153, 51), 3, true)); // orange border
            f.setPreferredSize(new Dimension(250, 45));
            f.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (f.getText().equals("Exercise Name") ||
                            f.getText().equals("Duration in minutes") ||
                            f.getText().equals("Notes (optional)")) {
                        f.setText("");
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (f.getText().isEmpty()) {
                        if (f == nameField) f.setText("Exercise Name");
                        else if (f == durField) f.setText("Duration in minutes");
                        else if (f == notesField) f.setText("Notes (optional)");
                        f.setForeground(Color.BLACK);
                    }
                }
            });
        }

        // --- Add labels to panel ---
        gbc.gridx = 0; gbc.gridy = 0; centerPanel.add(nameField, gbc);
        gbc.gridy = 1; centerPanel.add(durField, gbc);
        gbc.gridy = 2; centerPanel.add(notesField, gbc);

        // --- Add Exercise Button ---
        JButton addButton = new JButton("Add Exercise");
        addButton.setFont(new Font("Rubik", Font.BOLD, 18));
        addButton.setBackground(new Color(255, 153, 51)); // bright orange
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                int duration = Integer.parseInt(durField.getText().trim());
                String notes = notesField.getText().trim();
                if (name.isEmpty() || durField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill required fields!");
                    return;
                }
                HealthSyncApp.exercises.add(new ExerciseEntry(name, duration, notes));
                nameField.setText("Exercise Name");
                durField.setText("Duration in minutes");
                notesField.setText("Notes (optional)");
                JOptionPane.showMessageDialog(this, "Exercise logged!");
                reportPanel.updateReport();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duration must be numeric!");
            }
        });

        gbc.gridy = 3; centerPanel.add(addButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // --- Footer / motivational text ---
        JPanel footerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        footerPanel.setBackground(new Color(255, 230, 200));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField footerBox1 = new JTextField("Keep moving! 🏃");
        JTextField footerBox2 = new JTextField("Consistency is key!");

        // Placeholder text color black
        footerBox1.setForeground(Color.BLACK);
        footerBox2.setForeground(Color.BLACK);

        // Styling footer boxes
        JTextField[] footerBoxes = {footerBox1, footerBox2};
        for (JTextField f : footerBoxes) {
            f.setFont(new Font("Rubik", Font.ITALIC, 16));
            f.setBackground(Color.WHITE);
            f.setBorder(new LineBorder(new Color(255, 153, 51), 2, true));
        }

        footerPanel.add(footerBox1);
        footerPanel.add(footerBox2);

        add(footerPanel, BorderLayout.SOUTH);
    }
}
