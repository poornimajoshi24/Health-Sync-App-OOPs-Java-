package com.healthsync;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MedicinePanel extends JPanel {

    public MedicinePanel(ReportPanel reportPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 240, 250));

        // --- Header ---
        JLabel header = new JLabel("💊 Add Medicine", JLabel.CENTER);
        header.setFont(new Font("Rubik", Font.BOLD, 32));
        header.setForeground(new Color(25, 42, 86));
        header.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(header, BorderLayout.NORTH);

        // --- Form Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 250, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Input Fields ---
        JTextField nameField = new JTextField();
        JTextField doseField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField notesField = new JTextField();

        JTextField[] fields = {nameField, doseField, timeField, notesField};
        Color[] borderColors = {new Color(33, 150, 243), new Color(0, 188, 212), new Color(255, 152, 0), new Color(156, 39, 176)};
        for (int i = 0; i < fields.length; i++) {
            JTextField f = fields[i];
            Color color = borderColors[i];
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color, 3),
                    BorderFactory.createEmptyBorder(15, 20, 15, 20)
            ));
            f.setBackground(Color.WHITE);
            f.setFont(new Font("Rubik", Font.PLAIN, 18));
            f.setForeground(new Color(0, 0, 128)); // navy blue text
            f.setCaretColor(color);
        }

        // --- Labels ---
        JLabel nameLbl = new JLabel("Medicine Name:");
        JLabel doseLbl = new JLabel("Dosage:");
        JLabel timeLbl = new JLabel("Time (e.g., 08:00 AM):");
        JLabel notesLbl = new JLabel("Notes (optional):");

        JLabel[] labels = {nameLbl, doseLbl, timeLbl, notesLbl};
        for (JLabel l : labels) {
            l.setForeground(new Color(25, 42, 86));
            l.setFont(new Font("Rubik", Font.BOLD, 18));
        }

        // --- Add fields to form ---
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(nameLbl, gbc); gbc.gridx = 1; formPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(doseLbl, gbc); gbc.gridx = 1; formPanel.add(doseField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(timeLbl, gbc); gbc.gridx = 1; formPanel.add(timeField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(notesLbl, gbc); gbc.gridx = 1; formPanel.add(notesField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // --- Buttons Panel ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 25));
        btnPanel.setBackground(new Color(245, 250, 255));

        JButton addBtn = new JButton("Add Medicine");
        styleButton(addBtn, new Color(0, 0, 128), new Color(0, 0, 180), Color.BLACK); // text black
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Please enter medicine name"); return; }
            MedicineEntry m = new MedicineEntry(
                    name, doseField.getText().trim(),
                    timeField.getText().trim(),
                    notesField.getText().trim()
            );
            HealthSyncApp.medicines.add(m);
            nameField.setText(""); doseField.setText(""); timeField.setText(""); notesField.setText("");
            JOptionPane.showMessageDialog(this, "Medicine added successfully!");
            reportPanel.updateReport();
        });

        JButton clearBtn = new JButton("Clear");
        styleButton(clearBtn, new Color(25, 25, 25), new Color(70, 70, 70), Color.BLACK); // text black
        clearBtn.addActionListener(e -> {
            nameField.setText(""); doseField.setText(""); timeField.setText(""); notesField.setText("");
        });

        btnPanel.add(clearBtn);
        btnPanel.add(addBtn);

        add(btnPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button, Color base, Color hover, Color textColor) {
        button.setBackground(base);
        button.setForeground(textColor); // default text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setFont(new Font("Rubik", Font.BOLD, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { button.setBackground(base); }
        });
    }
}
