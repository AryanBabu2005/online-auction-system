package src;

import javax.swing.*;
import java.awt.*;

public class LandingScreen extends JFrame {

    public LandingScreen() {
        setTitle("Welcome to BidZone");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //  Gradient background panel
        JPanel gradientPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(33, 47, 61), 0, getHeight(), new Color(72, 133, 237));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new BorderLayout());

        JLabel logo = new JLabel(new ImageIcon("images/logo.jpeg"));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        gradientPanel.add(logo, BorderLayout.NORTH);

        JLabel heading = new JLabel("\uD83D\uDD25 Welcome to BidZone", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI Black", Font.BOLD, 34));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea description = new JTextArea("BidZone is a modern desktop auction marketplace where users can bid in real-time on tech, gadgets, and luxury items. Enjoy secure bidding, wallet balance management, seller profiles, and live countdowns.");
        description.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setOpaque(false);
        description.setEditable(false);
        description.setFocusable(false);
        description.setForeground(Color.WHITE);
        description.setMargin(new Insets(10, 50, 20, 50));

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(heading, BorderLayout.NORTH);
        center.add(description, BorderLayout.CENTER);

        gradientPanel.add(center, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton startButton = new JButton("\uD83D\uDE80 Start Bidding");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        startButton.setBackground(new Color(46, 204, 113));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.addActionListener(e -> {
            new LoginUI();
            dispose();
        });

        JButton adminButton = new JButton("\u2699 Admin Login");
        adminButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        adminButton.setBackground(new Color(231, 76, 60));
        adminButton.setForeground(Color.WHITE);
        adminButton.setFocusPainted(false);
        adminButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        adminButton.addActionListener(e -> {
            new LoginUI();
            dispose();
        });

        buttonPanel.add(startButton);
        buttonPanel.add(adminButton);

        gradientPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(gradientPanel);
        setVisible(true);
    }
}
