package src;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class UserHistoryUI extends JFrame {
    public UserHistoryUI() {
        setTitle(" My Bid History");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(area);

        JButton exportButton = new JButton(" Export to Text File");
        exportButton.setBackground(new Color(30, 144, 255));
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);
        exportButton.addActionListener(e -> exportToFile(area.getText()));

        String currentUser = AuctionSession.getCurrentUser();
        StringBuilder sb = new StringBuilder();

        for (AuctionItem item : AuctionDashboard.items) {
            String fileName = "bids_" + item.getId() + ".txt";
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("by " + currentUser)) {
                        sb.append("Item: ").append(item.getName()).append("\n");
                        sb.append("   ").append(line).append("\n\n");
                    }
                }
            } catch (IOException ignored) {}
        }

        if (sb.length() == 0) sb.append("You haven't placed any bids yet.");
        area.setText(sb.toString());

        add(scroll, BorderLayout.CENTER);
        add(exportButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void exportToFile(String content) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("my_bid_history.txt"));
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
                writer.write(content);
                JOptionPane.showMessageDialog(this, " Bid history exported successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "⚠ Failed to export file.");
            }
        }
    }
}
