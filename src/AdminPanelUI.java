package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class AdminPanelUI extends JFrame {
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton;

    public AdminPanelUI() {
        setTitle(" Admin Item Manager");
        setSize(850, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table columns
        String[] columns = {"ID", "Name", "Price", "Timer (min)", "Images (Comma Separated)"};
        tableModel = new DefaultTableModel(columns, 0);
        itemTable = new JTable(tableModel);
        loadItemsIntoTable();

        JScrollPane scrollPane = new JScrollPane(itemTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        addButton = new JButton(" Add Item");
        addButton.addActionListener(e -> addItem());

        editButton = new JButton(" Edit Item");
        editButton.addActionListener(e -> editItem());

        deleteButton = new JButton(" Delete Item");
        deleteButton.addActionListener(e -> deleteItem());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadItemsIntoTable() {
        tableModel.setRowCount(0); // Clear table first
        for (AuctionItem item : AuctionDashboard.items) {
            tableModel.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getHighestBid(),
                item.getMinutesRemaining(),
                String.join(",", item.getImagePaths())
            });
        }
    }

    private void addItem() {
        String id = JOptionPane.showInputDialog(this, "Enter Item ID:");
        String name = JOptionPane.showInputDialog(this, "Enter Item Name:");
        String priceStr = JOptionPane.showInputDialog(this, "Enter Start Price:");
        String timerStr = JOptionPane.showInputDialog(this, "Enter Timer (minutes):");
        String imagesStr = JOptionPane.showInputDialog(this, "Enter Image Paths (comma separated):");

        if (id != null && name != null && priceStr != null && timerStr != null && imagesStr != null) {
            try {
                int price = Integer.parseInt(priceStr);
                int timer = Integer.parseInt(timerStr);
                String[] imagePaths = imagesStr.split(",");

                AuctionDashboard.items.add(new AuctionItem(id, name, price, timer, imagePaths));
                saveItemsToFile();
                loadItemsIntoTable();
                JOptionPane.showMessageDialog(this, "✅ Item Added Successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠ Please enter valid numbers for price and timer.");
            }
        }
    }

    private void editItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠ Please select an item to edit.");
            return;
        }

        String newName = JOptionPane.showInputDialog(this, "Enter new Name:", tableModel.getValueAt(selectedRow, 1));
        String newPriceStr = JOptionPane.showInputDialog(this, "Enter new Start Price:", tableModel.getValueAt(selectedRow, 2));
        String newTimerStr = JOptionPane.showInputDialog(this, "Enter new Timer (minutes):", tableModel.getValueAt(selectedRow, 3));
        String newImagesStr = JOptionPane.showInputDialog(this, "Enter new Image Paths (comma separated):", tableModel.getValueAt(selectedRow, 4));

        if (newName != null && newPriceStr != null && newTimerStr != null && newImagesStr != null) {
            try {
                int newPrice = Integer.parseInt(newPriceStr);
                int newTimer = Integer.parseInt(newTimerStr);
                String[] newImages = newImagesStr.split(",");

                AuctionItem item = AuctionDashboard.items.get(selectedRow);
                item.setName(newName);
                item.setHighestBid(newPrice);
                item.setTimerMinutes(newTimer);
                item.setImagePaths(newImages);
                
                saveItemsToFile();
                loadItemsIntoTable();
                JOptionPane.showMessageDialog(this, "✅ Item Updated Successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠ Please enter valid numbers for price and timer.");
            }
        }
    }

    private void deleteItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠ Please select an item to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            AuctionDashboard.items.remove(selectedRow);
            saveItemsToFile();
            loadItemsIntoTable();
            JOptionPane.showMessageDialog(this, "✅ Item Deleted Successfully!");
        }
    }

    private void saveItemsToFile() {
        try (FileWriter writer = new FileWriter("items.txt")) {
            for (AuctionItem item : AuctionDashboard.items) {
                writer.write(item.getId() + "," +
                             item.getName() + "," +
                             item.getHighestBid() + "," +
                             item.getMinutesRemaining() + "," +
                             String.join(",", item.getImagePaths()) + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "⚠ Failed to save items to file.");
        }
    }
}
