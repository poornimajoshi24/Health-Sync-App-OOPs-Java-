package com.healthsync;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardPanel extends JPanel {
    private JLabel timeLabel;
    private JLabel vitalLabel;
    private JLabel scoreLabel;
    private JPanel plantPanel;
    private double nourishment = 0;

    private double pulseScale = 1.0;
    private double burstRadius = 0;
    private boolean bursting = false;
    private Timer animTimer;

    public DashboardPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 250)); // Soft pastel background

        // --- Header ---
        JPanel header = new JPanel(new BorderLayout(15, 15));
        header.setOpaque(false);

        timeLabel = new JLabel(getTime(), SwingConstants.LEFT);
        timeLabel.setFont(new Font("Rubik", Font.BOLD, 20));
        timeLabel.setForeground(new Color(26, 35, 126));

        vitalLabel = new JLabel("Vital: 102/2431", SwingConstants.CENTER);
        vitalLabel.setFont(new Font("Rubik", Font.PLAIN, 14));
        vitalLabel.setForeground(new Color(48, 63, 159));

        header.add(timeLabel, BorderLayout.WEST);
        header.add(vitalLabel, BorderLayout.CENTER);

        // --- Main content split ---
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setOpaque(false);

        // Left: Task Cards
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);

        left.add(makeTaskCard("💊", "Morning Meds", "9:00 AM", new Color(209, 196, 233), new Color(179, 157, 219)));
        left.add(Box.createVerticalStrut(12));
        left.add(makeTaskCard("💧", "Hydrate Goal", "10:00 AM", new Color(178, 235, 242), new Color(128, 222, 234)));
        left.add(Box.createVerticalStrut(12));
        left.add(makeTaskCard("🧘", "Deep Focus Yoga", "1:00 PM", new Color(197, 225, 165), new Color(139, 195, 74)));
        left.add(Box.createVerticalStrut(12));
        left.add(makeTaskCard("📖", "Creative Reading", "4:00 PM", new Color(206, 147, 216), new Color(171, 71, 188)));
        left.add(Box.createVerticalStrut(12));
        left.add(makeTaskCard("🚶", "Mindful Walk", "6:00 PM", new Color(129, 212, 250), new Color(41, 182, 246)));

        // Right: Score Box + Plant
        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JPanel scoreBox = new JPanel();
        scoreBox.setLayout(new BoxLayout(scoreBox, BoxLayout.Y_AXIS));
        scoreBox.setBackground(new Color(240, 235, 250));
        scoreBox.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        scoreBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreLabel = new JLabel((int)nourishment + "%", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Rubik", Font.BOLD, 28));
        scoreLabel.setForeground(new Color(76, 175, 80));

        JLabel scoreText = new JLabel("Nourishment Score", SwingConstants.CENTER);
        scoreText.setFont(new Font("Rubik", Font.PLAIN, 14));
        scoreText.setForeground(new Color(26, 35, 126));

        JButton seedBtn = new JButton("+ Seed Focus");
        seedBtn.setBackground(new Color(128, 222, 234));
        seedBtn.setForeground(new Color(26, 35, 126));
        seedBtn.setFocusPainted(false);
        seedBtn.setFont(new Font("Rubik", Font.BOLD, 14));
        seedBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        seedBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        seedBtn.addActionListener(e -> {
            nourishment = Math.min(100, nourishment + 10);
            scoreLabel.setText((int)nourishment + "%");
            startBurstAnimation();
        });

        scoreBox.add(scoreLabel);
        scoreBox.add(Box.createVerticalStrut(8));
        scoreBox.add(scoreText);
        scoreBox.add(Box.createVerticalStrut(12));
        scoreBox.add(seedBtn);

        right.add(scoreBox);
        right.add(Box.createVerticalStrut(20));

        // Plant Panel
        plantPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int size = Math.min(getWidth(), getHeight());
                int cx = size / 2;
                int cy = size - 40;

                if (bursting) {
                    g2.setColor(new Color(76, 175, 80, 120));
                    g2.setStroke(new BasicStroke(3f));
                    g2.drawOval((int) (cx - burstRadius / 2), (int) (cy - burstRadius / 2),
                            (int) burstRadius, (int) burstRadius);
                }

                double growth = 0.4 + nourishment / 100.0 * 0.8;
                int stemH = (int) (size / 3 * growth);
                int leafSize = (int) (size * (0.15 + 0.5 * growth));

                g2.setColor(new Color(76, 175, 80));
                g2.fillRoundRect(cx - 5, cy - stemH, 10, stemH, 6, 6);

                g2.setColor(new Color(129, 212, 250));
                int leafW = (int) (leafSize * pulseScale);
                int leafH = (int) (leafSize * pulseScale);
                g2.fillOval(cx - leafW / 2, cy - stemH - leafH, leafW, leafH);
            }
        };
        plantPanel.setPreferredSize(new Dimension(150, 150));
        plantPanel.setOpaque(false);
        right.add(plantPanel);

        // Streaks
        JLabel streak1 = new JLabel("✨ 7-Day Exercise Streak");
        JLabel streak2 = new JLabel("💧 1-Day Hydration Streak");
        streak1.setAlignmentX(Component.CENTER_ALIGNMENT);
        streak2.setAlignmentX(Component.CENTER_ALIGNMENT);
        streak1.setForeground(new Color(26, 35, 126));
        streak2.setForeground(new Color(26, 35, 126));
        right.add(Box.createVerticalStrut(10));
        right.add(streak1);
        right.add(streak2);

        mainPanel.add(left);
        mainPanel.add(right);

        JLabel footer = new JLabel("Your Inner Garden Flourishes!", SwingConstants.CENTER);
        footer.setForeground(new Color(48, 63, 159));
        footer.setFont(new Font("Rubik", Font.PLAIN, 13));

        add(header, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        new Timer(1000, e -> timeLabel.setText(getTime())).start();
    }

    private JPanel makeTaskCard(String emoji, String text, String time, Color c1, Color c2) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Shape clip = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 18, 18);

                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
                g2.setPaint(gp);
                g2.fill(clip);

                g2.setColor(new Color(0, 0, 0, 20));
                g2.draw(clip);
            }
        };
        card.setLayout(new BorderLayout(10, 0));
        card.setPreferredSize(new Dimension(300, 50));
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel icon = new JLabel(emoji);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        JPanel textPart = new JPanel(new GridLayout(2, 1));
        textPart.setOpaque(false);
        JLabel timeLbl = new JLabel(time);
        timeLbl.setForeground(c2.darker());
        JLabel desc = new JLabel(text);
        desc.setFont(new Font("Montserrat", Font.BOLD, 13));
        textPart.add(timeLbl);
        textPart.add(desc);

        card.add(icon, BorderLayout.WEST);
        card.add(textPart, BorderLayout.CENTER);

        // Determine nourishment increment based on task
        int increment;
        switch (text) {
            case "Morning Meds" -> increment = 10;
            case "Hydrate Goal" -> increment = 20;
            case "Deep Focus Yoga" -> increment = 20;
            case "Creative Reading" -> increment = 10;
            case "Mindful Walk" -> increment = 30;
            default -> increment = 0;
        }

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                nourishment = Math.min(100, nourishment + increment);
                scoreLabel.setText((int) nourishment + "%");
                startBurstAnimation();
            }
        });

        return card;
    }

    private void startBurstAnimation() {
        if (animTimer != null && animTimer.isRunning()) animTimer.stop();
        bursting = true;
        burstRadius = 0;
        pulseScale = 1.2;

        animTimer = new Timer(20, e -> {
            burstRadius += 6;
            pulseScale = 1.0 + Math.sin(burstRadius / 10.0) * 0.1; 
            plantPanel.repaint();

            if (burstRadius > 100) {
                bursting = false;
                ((Timer) e.getSource()).stop();
            }
        });
        animTimer.start();
    }

    private String getTime() {
        return new SimpleDateFormat("hh:mm a").format(new Date());
    }
}
