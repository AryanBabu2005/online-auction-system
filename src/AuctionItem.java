package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuctionItem {
    private String id;
    private String name;
    private int highestBid;
    private String seller;
    private boolean isNew;
    private List<BidRecord> bidHistory = new ArrayList<>();
    private long endTimeMillis;
    private List<String> imagePaths = new ArrayList<>();
    private List<String> specifications = new ArrayList<>();
    private int currentImageIndex = 0;

    private boolean announced = false;
    private boolean featured = false;

    public AuctionItem(String id, String name, int highestBid, int durationMinutes, String seller,
                       String[] imagePaths, String[] specs) {
        this.id = id;
        this.name = name;
        this.highestBid = highestBid;
        this.seller = seller;
        this.endTimeMillis = System.currentTimeMillis() + (durationMinutes * 60 * 1000);
        for (String path : imagePaths) {
            this.imagePaths.add(path.trim());
        }
        for (String spec : specs) {
            this.specifications.add(spec.trim());
        }
        this.isNew = true; // Default as new
    }

    public AuctionItem(String id, String name, int highestBid, int durationMinutes, String... imagePaths) {
        this(id, name, highestBid, durationMinutes, "Unknown", imagePaths, new String[0]);
    }

    public void addBid(BidRecord record) {
        bidHistory.add(record);
    }

    public void loadBidHistoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("bids_" + id + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("₹") && line.contains("at")) {
                    String[] parts = line.replace("💰 ₹", "").split(" at ");
                    int bidAmount = Integer.parseInt(parts[0].trim());
                    String timestamp = parts[1].trim();
                    BidRecord record = new BidRecord(id, bidAmount, timestamp);
                    bidHistory.add(record);
                    if (bidAmount > highestBid) {
                        highestBid = bidAmount;
                    }
                }
            }
        } catch (IOException ignored) {}
    }

    public String getTimeRemaining() {
        long remaining = endTimeMillis - System.currentTimeMillis();
        if (remaining <= 0)
            return " CLOSED";
        long minutes = remaining / 60000;
        long seconds = (remaining % 60000) / 1000;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public boolean isClosed() {
        return System.currentTimeMillis() > endTimeMillis;
    }

    public void extendTimer(int seconds) {
        endTimeMillis += seconds * 1000L;
    }

    public int getMinutesRemaining() {
        long remaining = endTimeMillis - System.currentTimeMillis();
        return remaining <= 0 ? 0 : (int) (remaining / 60000);
    }

    public long getEndTimeMillis() {
        return endTimeMillis;
    }

    public String getNextImagePath() {
        if (imagePaths.isEmpty()) return null;
        currentImageIndex = (currentImageIndex + 1) % imagePaths.size();
        return imagePaths.get(currentImageIndex);
    }

    public String getCurrentImagePath() {
        if (imagePaths.isEmpty()) return null;
        return imagePaths.get(currentImageIndex);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHighestBid() {
        return highestBid;
    }

    public List<BidRecord> getBidHistory() {
        return bidHistory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHighestBid(int highestBid) {
        this.highestBid = highestBid;
    }

    public void setTimerMinutes(int minutes) {
        this.endTimeMillis = System.currentTimeMillis() + (minutes * 60 * 1000);
    }

    public boolean isAnnounced() {
        return announced;
    }

    public void setAnnounced(boolean announced) {
        this.announced = announced;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public String[] getImagePaths() {
        return imagePaths.toArray(new String[0]);
    }

    public void setImagePaths(String[] imagePaths) {
        this.imagePaths = new ArrayList<>(List.of(imagePaths));
    }

    public List<String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String[] specs) {
        this.specifications = new ArrayList<>(List.of(specs));
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public String toString() {
        return "🔹 " + name + " (ID: " + id + ") - ₹" + highestBid + " ⏳ " + getTimeRemaining();
    }
}
