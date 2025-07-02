package src;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginUI() {
        setTitle("BidZone - Secure Login");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Logo and App Name
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(255, 255, 255));

        JLabel logo = new JLabel(new ImageIcon("images/logo.jpeg")); 
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(logo, BorderLayout.NORTH);

        JLabel appName = new JLabel("Welcome to BidZone", SwingConstants.CENTER);
        appName.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
        appName.setForeground(new Color(44, 62, 80));
        topPanel.add(appName, BorderLayout.CENTER);

        JLabel tagline = new JLabel("India's Trusted Real-Time Auction Portal", SwingConstants.CENTER);
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tagline.setForeground(Color.DARK_GRAY);
        topPanel.add(tagline, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Login Form
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.addActionListener(e -> login());

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.addActionListener(e -> new RegisterUI());

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.add(loginButton);
        actionPanel.add(registerButton);

        formPanel.add(actionPanel);
        add(formPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("© 2025 BidZone. All rights reserved.", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(Color.GRAY);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        String[] userDetails = getUserDetails(username, password);
        if (userDetails != null) {
            String role = userDetails[0];
            String email = userDetails[1];

            JOptionPane.showMessageDialog(this, " Login Successful!");
            AuctionSession.setCurrentUser(username);
            AuctionSession.setCurrentRole(role);
            AuctionSession.setCurrentUserEmail(email);
            WalletManager.initializeUser(username);
            AuctionDashboard.initializeItems();
            new AuctionDashboard();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, " Invalid username or password!");
        }
    }

    private String[] getUserDetails(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].equals(username) && parts[1].equals(password)) {
                    return new String[]{parts[2], parts[3]}; // role, email
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "⚠ Failed to read users.txt file.");
        }
        return null;
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}
