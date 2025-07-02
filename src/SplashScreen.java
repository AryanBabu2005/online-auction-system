package src;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(24, 40, 72));

        JLabel logo = new JLabel(new ImageIcon("images/logo.png")); 
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(logo, BorderLayout.CENTER);

        JLabel loading = new JLabel("Loading BidZone...", SwingConstants.CENTER);
        loading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loading.setForeground(Color.WHITE);
        content.add(loading, BorderLayout.SOUTH);

        getContentPane().add(content);
        setSize(500, 300);
        setLocationRelativeTo(null);
    }

    public static void showSplashAndLaunch() {
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);

        try {
            Thread.sleep(5000); // Show splash for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splash.setVisible(false);
        splash.dispose();

        new LandingScreen(); // or LoginUI/AuctionDashboard directly
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplashScreen::showSplashAndLaunch);
    }
}
