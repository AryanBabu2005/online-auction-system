package src;

import javax.swing.*;
import java.awt.*;

public class AuctionItemCard extends JPanel {
    public AuctionItemCard(AuctionItem item) {
        setPreferredSize(new Dimension(250, 300));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setBackground(Color.WHITE);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));
                setBackground(new Color(250, 250, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                setBackground(Color.WHITE);
            }
        });

        // 🖼 Image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            String imgPath = item.getCurrentImagePath();
            if (imgPath != null) {
                ImageIcon icon = new ImageIcon(imgPath);
                Image scaled = icon.getImage().getScaledInstance(240, 120, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
            } else {
                imageLabel.setText("No Image");
            }
        } catch (Exception e) {
            imageLabel.setText("Image Error");
        }

        add(imageLabel, BorderLayout.NORTH);

        //  Item info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel nameLabel = new JLabel(" " + item.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel sellerLabel = new JLabel(" Seller: " + item.getSeller());
        sellerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sellerLabel.setForeground(Color.DARK_GRAY);

        JLabel bidLabel = new JLabel(" Starting Bid: ₹" + item.getHighestBid());
        bidLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel timeLabel = new JLabel(" Time Left: " + item.getTimeRemaining());
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        if (item.isNew()) {
            JLabel newBadge = new JLabel("🆕 NEW");
            newBadge.setForeground(Color.WHITE);
            newBadge.setBackground(new Color(52, 152, 219));
            newBadge.setOpaque(true);
            newBadge.setFont(new Font("Segoe UI", Font.BOLD, 12));
            newBadge.setHorizontalAlignment(SwingConstants.CENTER);
            newBadge.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
            infoPanel.add(newBadge);
        }

        JButton bidBtn = new JButton("Place Bid");
        bidBtn.setBackground(new Color(34, 139, 34));
        bidBtn.setForeground(Color.WHITE);
        bidBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        bidBtn.setFocusPainted(false);
        bidBtn.addActionListener(e -> new BidUI());

        infoPanel.add(nameLabel);
        infoPanel.add(sellerLabel);
        infoPanel.add(bidLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(bidBtn);

        add(infoPanel, BorderLayout.CENTER);
    }
}
