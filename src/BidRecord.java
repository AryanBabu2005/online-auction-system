package src;

public class BidRecord {
    private String itemId;
    private int bidAmount;
    private String timestamp;

    public BidRecord(String itemId, int bidAmount, String timestamp) {
        this.itemId = itemId;
        this.bidAmount = bidAmount;
        this.timestamp = timestamp;
    }

    public String getItemId() {
        return itemId;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return " ₹" + bidAmount + " at " + timestamp;
    }
}

