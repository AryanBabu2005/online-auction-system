package src;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BidUI extends JFrame {
    private JTextField itemIdField, bidAmountField;
    private JButton bidButton;

    public BidUI() {
        setTitle(" Place Your Bid");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel(" Item ID:"));
        itemIdField = new JTextField();
        add(itemIdField);

        add(new JLabel(" Bid Amount:"));
        bidAmountField = new JTextField();
        add(bidAmountField);

        bidButton = new JButton(" Submit Bid");
        bidButton.setBackground(new Color(255, 153, 0));
        bidButton.setForeground(Color.WHITE);
        bidButton.addActionListener(e -> submitBid());

        add(new JLabel()); // empty cell
        add(bidButton);

        setVisible(true);
    }

    private void submitBid() {
        String itemId = itemIdField.getText().trim();
        String bidAmountStr = bidAmountField.getText().trim();

        try {
            int bidAmount = Integer.parseInt(bidAmountStr);
            String currentUser = AuctionSession.getCurrentUser();

            boolean itemFound = false;

            for (AuctionItem item : AuctionDashboard.items) {
                if (item.getId().equals(itemId)) {
                    itemFound = true;

                    if (item.isClosed()) {
                        JOptionPane.showMessageDialog(this, " This auction is already closed!");
                        return;
                    }

                    if (bidAmount <= item.getHighestBid()) {
                        JOptionPane.showMessageDialog(this, " Your bid must be higher than the current highest bid (₹"
                                + item.getHighestBid() + ")");
                        return;
                    }

                    if (!WalletManager.hasEnough(currentUser, bidAmount)) {
                        JOptionPane.showMessageDialog(this, " Insufficient balance! You have ₹"
                                + WalletManager.getBalance(currentUser));
                        return;
                    }

                    // Deduct wallet balance
                    WalletManager.deduct(currentUser, bidAmount);

                    // create timestamp
                    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    // create and add bid record
                    BidRecord record = new BidRecord(itemId, bidAmount, time);
                    item.setHighestBid(bidAmount);
                    item.addBid(record);

                    // save to file
                    try (FileWriter writer = new FileWriter("bids_" + itemId + ".txt", true)) {
                        writer.write(record.toString() + " by " + AuctionSession.getCurrentUser() + "\n");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "⚠ Failed to save bid to file.");
                    }

                    JOptionPane.showMessageDialog(this, "✔ Bid Placed Successfully!\n💰 Remaining Balance: ₹"
                            + WalletManager.getBalance(currentUser));

                    // Extend time if bid in last 10 sec
                    long timeRemaining = item.getEndTimeMillis() - System.currentTimeMillis();
                    if (timeRemaining > 0 && timeRemaining <= 10000) {
                        item.extendTimer(30);
                        JOptionPane.showMessageDialog(this, " Late bid! Auction time extended by 30 seconds!");
                    }

                    break;
                }
            }

            if (!itemFound) {
                JOptionPane.showMessageDialog(this, " Item ID not found!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠ Please enter a valid number for the bid.");
        }
    }
}
