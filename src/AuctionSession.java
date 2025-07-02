package src;

public class AuctionSession {
    private static String currentUser;
    private static String currentRole;
    private static String currentUserEmail;

    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentRole(String role) {
        currentRole = role;
    }

    public static String getCurrentRole() {
        return currentRole;
    }

    public static void setCurrentUserEmail(String email) {
        currentUserEmail = email;
    }

    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public static void clearSession() {
        currentUser = null;
        currentRole = null;
        currentUserEmail = null;
    }
}
