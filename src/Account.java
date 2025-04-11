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
        this.encryptedPassword = CryptoUtils.encrypt(plainPassword);
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

    public String getEncryptedPassword() {
        return encryptedPassword;
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

    public boolean matches(Account other){
        return this.platform.equalsIgnoreCase(other.platform) &&
                this.email.equalsIgnoreCase(other.email);
    }

    @Override
    public String toString() {
        return "↪ " + platform + " ⇨ " +
                "Username: " + username +
                "\nEmail: " + email +
                "\npassword: " + encryptedPassword;
    }
}
