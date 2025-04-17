public class Account {
    private final String platform;
    private final String username;
    private final String email;
    private final String encryptedPassword;

    // Builder
    public Account(String platform, String username, String email, String plainPassword){
        this.platform = platform;
        this.username = username;
        this.email = email;
        this.encryptedPassword = CryptoUtils.encrypt(plainPassword, SessionManager.getMasterPassword());
    }

    // Getter
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPlatform() {
        return platform;
    }

    /*
    public String getEncryptedPassword() {
        return encryptedPassword;
    }
    */

    public String getDecryptedPassword() {
        try {
            return CryptoUtils.decrypt(encryptedPassword, SessionManager.getMasterPassword());
        } catch (CryptoUtils.CryptoException e) {
            System.err.println("Failed to decrypt password: " + e.getMessage());
            return "[DECRYPTION ERROR]"; // O un valore di fallback
        }
    }

    //crea una stringa formattata a partire dall'account da poter mettere sul file
    public String toFileString(){
        return platform + ";" + username + ";" + email + ";" + encryptedPassword;
    }

    //prende una riga del file (formattata), la decompone nei campi di 'Account' e crea un oggetto Account
    public Account(String fileLine){
        String[] parts = fileLine.split(";");
        this.platform = parts[0];
        this.username = parts[1];
        this.email = parts[2];
        this.encryptedPassword = parts[3];
    }

    public boolean matchesCredentials(String platform, String username, String email){
        return this.platform.equalsIgnoreCase(platform) &&
                this.username.equalsIgnoreCase(username) &&
                this.email.equalsIgnoreCase(email);
    }

    public boolean verifyPassword(String inputPassword){
        return getDecryptedPassword().equals(inputPassword);
    }

    @Override
    public String toString() {
        return "▢ " + getPlatform() + " ⇨ Username: " + getUsername() + " | Email: " + getEmail() + " | password: " + getDecryptedPassword();
    }
}
