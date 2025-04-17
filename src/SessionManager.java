public class SessionManager {
    private static String masterPassword;

    public static void setMasterPassword(String password) {
        masterPassword = password;
    }

    public static String getMasterPassword() {
        if (masterPassword == null) {
            throw new IllegalStateException("Master password not set");
        }
        return masterPassword;
    }

    /*
    public static boolean isMasterPasswordSet() {
        return masterPassword != null;
    }
    */

    public static void clearSession() {
        masterPassword = null;
    }
}

