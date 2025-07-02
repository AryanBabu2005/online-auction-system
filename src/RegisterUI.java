package src;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class RegisterUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton registerButton;

    public RegisterUI() {
        setTitle(" Register New User");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Create a New Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.DARK_GRAY);
        add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        emailField = new JTextField();

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(60, 179, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> registerUser());

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel()); // empty
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String email = emailField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Please fill in all fields.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "⚠ Please enter a valid email address.");
            return;
        }

        if (userExists(username)) {
            JOptionPane.showMessageDialog(this, " Username already exists!");
            return;
        }

        try (FileWriter writer = new FileWriter("users.txt", true)) {
            writer.write(username + "," + password + ",bidder," + email + "\n");
            JOptionPane.showMessageDialog(this, " Registration successful! You can now login.");
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "⚠ Failed to register user.");
        }
    }

    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException ignored) {}
        return false;
    }
}
