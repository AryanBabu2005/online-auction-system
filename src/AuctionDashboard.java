package src;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AuctionDashboard extends JFrame {
    private JPanel itemGrid;
    private JLabel balanceLabel, userLabel;
    private boolean darkMode = false;
    public static List<AuctionItem> items = new ArrayList<>();

    public AuctionDashboard() {
        setTitle("BidZone | Online Auctions for Everyone");
        setSize(1080, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // HEADER PANEL
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(24, 40, 72));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("\uD83C\uDF0D Welcome to BidZone - India's Trusted Auction Marketplace");
        titleLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        userLabel = new JLabel("\uD83D\uDC64 Logged in as: " + AuctionSession.getCurrentUser());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userLabel.setForeground(Color.LIGHT_GRAY);
        userPanel.add(userLabel);

        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        balanceLabel.setForeground(Color.GREEN);
        userPanel.add(balanceLabel);

        JButton themeToggle = new JButton("🌗 Toggle Theme");
        themeToggle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        themeToggle.setBackground(Color.GRAY);
        themeToggle.setForeground(Color.WHITE);
        themeToggle.setFocusPainted(false);
        themeToggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themeToggle.addActionListener(e -> toggleTheme());
        userPanel.add(themeToggle);

        headerPanel.add(userPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // CARD GRID
        itemGrid = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        itemGrid.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(itemGrid);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // BUTTONS PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        buttonPanel.add(createStyledButton("\uD83D\uDD04 Refresh", new Color(255, 140, 0), e -> loadAuctions()));
        buttonPanel.add(createStyledButton("\uD83D\uDCB0 Place Bid", new Color(46, 204, 113), e -> new BidUI()));
        buttonPanel.add(createStyledButton("\uD83D\uDCDC All Bids", new Color(52, 152, 219), e -> new BidHistoryUI()));
        buttonPanel.add(createStyledButton("\uD83D\uDCCB My Bids", new Color(155, 89, 182), e -> new UserHistoryUI()));
        buttonPanel.add(createStyledButton("\uD83D\uDD0D View Details", new Color(241, 196, 15), e -> {
            if (!items.isEmpty()) new ItemDetailsUI(items.get(0));
            else JOptionPane.showMessageDialog(this, "No items available.");
        }));

        if ("admin".equalsIgnoreCase(AuctionSession.getCurrentRole())) {
            buttonPanel.add(createStyledButton("\u2699 Admin Panel", new Color(231, 76, 60), e -> new AdminPanelUI()));
        }

        add(buttonPanel, BorderLayout.SOUTH);

        new javax.swing.Timer(10000, e -> loadAuctions()).start();
        new javax.swing.Timer(3000, e -> itemGrid.repaint()).start();

        loadAuctions();
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color color, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.addActionListener(action);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(color.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        Color bg = darkMode ? new Color(33, 33, 33) : new Color(245, 245, 245);
        Color fg = darkMode ? Color.LIGHT_GRAY : Color.BLACK;

        itemGrid.setBackground(bg);
        userLabel.setForeground(fg);
        balanceLabel.setForeground(darkMode ? Color.GREEN : new Color(0, 128, 0));
        repaint();
    }

    public static void initializeItems() {
        items.clear();
        items.add(new AuctionItem("1", "MacBook Pro", 50000, 5, "TechStore",
            new String[]{"images/macbook1.jpg", "images/macbook2.jpg", "images/macbook3.jpg"},
            new String[]{"CPU: Apple M2", "RAM: 16GB", "Storage: 512GB SSD", "Condition: New"}));

        items.add(new AuctionItem("2", "iPhone 14", 40000, 3,"AppleStore",
            new String[]{"images/iphone1.jpg", "images/iphone2.jpg"},
            new String[]{"Chip: A15 Bionic", "Display: 6.1-inch", "Storage: 128GB", "Condition: Like New"}));

        items.add(new AuctionItem("3", "Rolex Watch", 70000, 4,"LuxuryWatches",
            new String[]{"images/rolex1.jpg", "images/rolex2.jpg"},
            new String[]{"Brand: Rolex", "Model: Submariner", "Condition: Excellent", "Waterproof: Yes"}));

        items.add(new AuctionItem("4", "Sony PS5", 30000, 2,"GamingZone",
            new String[]{"images/ps5_1.jpg", "images/ps5_2.jpg"},
            new String[]{"Brand: Sony", "Type: PlayStation 5", "Storage: 1TB", "Includes: 1 Controller"}));

        for (AuctionItem item : items) {
            item.loadBidHistoryFromFile();
        }

        if (!items.isEmpty()) {
            int randomIndex = (int) (Math.random() * items.size());
            items.get(randomIndex).setFeatured(true);
        }
    }

    private void loadAuctions() {
        itemGrid.removeAll();
        for (AuctionItem item : items) {
            if (item.isClosed() && !item.isAnnounced()) {
                announceWinner(item);
                item.setAnnounced(true);
            }
            if (!item.isClosed()) {
                itemGrid.add(new AuctionItemCard(item));
            }
        }
        itemGrid.revalidate();
        itemGrid.repaint();
        updateWalletBalance();
    }

    private void updateWalletBalance() {
        String currentUser = AuctionSession.getCurrentUser();
        int balance = WalletManager.getBalance(currentUser);
        balanceLabel.setText("\uD83D\uDCB0 Wallet: \u20B9" + balance);
    }

    private void announceWinner(AuctionItem item) {
    String winner = getHighestBidder(item.getId());
    if (winner == null) {
        JOptionPane.showMessageDialog(this, " Auction Ended for " + item.getName() + "\nNo bids were placed.");
    } else {
        JOptionPane.showMessageDialog(this,
            "🏆 " + item.getName() + " SOLD!\nWinner: " + winner +
            "\nWinning Bid: ₹" + item.getHighestBid());

    }
}


    private String getHighestBidder(String itemId) {
    String winner = null;
    int highestBid = -1;

    try (BufferedReader reader = new BufferedReader(new FileReader("bids_" + itemId + ".txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Expected format: 💰 ₹45000 at 2025-05-21 18:13:53 by Aryan
            if (line.contains(" by ")) {
                String[] mainParts = line.split(" by ");
                String leftPart = mainParts[0].trim();   // 💰 ₹45000 at 2025-05-21 18:13:53
                String bidder = mainParts[1].trim();     // Aryan

                // Now extract bid amount
                int rupeeIndex = leftPart.indexOf("₹");
                int atIndex = leftPart.indexOf("at");
                if (rupeeIndex != -1 && atIndex != -1 && atIndex > rupeeIndex) {
                    String amountStr = leftPart.substring(rupeeIndex + 1, atIndex).trim();
                    int bidAmount = Integer.parseInt(amountStr);

                    if (bidAmount > highestBid) {
                        highestBid = bidAmount;
                        winner = bidder;
                    }
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return winner;
}
}
