package src;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BidHistoryUI extends JFrame {
    private JTextArea historyArea;

    public BidHistoryUI() {
        setTitle(" Bid History Viewer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String itemId = JOptionPane.showInputDialog(this, "Enter Item ID:");

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(historyArea), BorderLayout.CENTER);

        if (itemId == null || itemId.trim().isEmpty()) {
            historyArea.setText(" Item ID was not entered.");
            setVisible(true);
            return;
        }

        loadBidHistory(itemId.trim());
        setVisible(true);
    }

    private void loadBidHistory(String itemId) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("bids_" + itemId + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            if (sb.length() == 0) {
                sb.append("No bids yet.");
            }
        } catch (IOException ex) {
            sb.append(" No bid history found for Item ID: ").append(itemId);
        }

        historyArea.setText(sb.toString());
    }
}
