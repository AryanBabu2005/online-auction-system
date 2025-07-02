package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ItemDetailsUI extends JFrame {
    private JLabel imageLabel;
    private JTextArea specsArea;
    private AuctionItem item;
    private Timer imageTimer;

    public ItemDetailsUI(AuctionItem item) {
        this.item = item;

        setTitle(" Item Details - " + item.getName());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Image Panel
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createTitledBorder("Images"));
        imageLabel.setPreferredSize(new Dimension(300, 250));
        add(imageLabel, BorderLayout.NORTH);

        // Specs Panel
        specsArea = new JTextArea();
        specsArea.setEditable(false);
        specsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        specsArea.setBorder(BorderFactory.createTitledBorder("Specifications"));
        JScrollPane scrollPane = new JScrollPane(specsArea);
        add(scrollPane, BorderLayout.CENTER);

        loadSpecs();
        startImageCarousel();

        setVisible(true);
    }

    private void loadSpecs() {
        StringBuilder sb = new StringBuilder();
        List<String> specs = item.getSpecifications();
        if (specs.isEmpty()) {
            sb.append("No specifications available.");
        } else {
            for (String spec : specs) {
                sb.append("• ").append(spec).append("\n");
            }
        }
        specsArea.setText(sb.toString());
    }

    private void startImageCarousel() {
        imageTimer = new Timer(3000, e -> updateImage());
        imageTimer.start();
        updateImage(); // Load first image immediately
    }

    private void updateImage() {
        String path = item.getNextImagePath();
        if (path != null) {
            try {
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
                imageLabel.setText("");
            } catch (Exception ex) {
                imageLabel.setText("Image not found");
            }
        } else {
            imageLabel.setText("No image available");
        }
    }
}

