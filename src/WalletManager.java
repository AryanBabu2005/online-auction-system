package src;

import java.util.HashMap;
import java.util.Map;

public class WalletManager {
    private static Map<String, Integer> wallet = new HashMap<>();
    private static final int STARTING_BALANCE = 100_000;

    public static void initializeUser(String username) {
        wallet.putIfAbsent(username, STARTING_BALANCE);
    }

    public static boolean hasEnough(String username, int amount) {
        return wallet.getOrDefault(username, 0) >= amount;
    }

    public static boolean deduct(String username, int amount) {
        if (!hasEnough(username, amount)) return false;
        wallet.put(username, wallet.get(username) - amount);
        return true;
    }

    public static int getBalance(String username) {
        return wallet.getOrDefault(username, 0);
    }
}

