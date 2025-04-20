import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class AccountFileDAO implements AccountDAO {
    private final File file;

    public AccountFileDAO(String fileName) {
        this.file = new File(fileName);
        try{
            if(file.createNewFile()){
                System.out.println("Save-file successfully created.");
            }else{
                System.out.println("Using existing save-file.");
            }
        }catch(IOException e){
            System.err.println("Error while creating/opening file" + e.getMessage());
        }
    }

    @Override
    public void saveAccount(Account account) {
        //try with resources
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))){
            writer.write(account.toFileString());
            writer.newLine();
            System.out.println("Account successfully added to file.");
        }catch(IOException e){
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        //try with resources
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                accounts.add(new Account(line));
            }
        }catch(IOException e){
            System.err.println("Error while reading file: " + e.getMessage());
        }
        return accounts;
    }

    @Override
    public List<Account> findByPlatform(String platform) {
        List<Account> accounts = getAll();
        List<Account> matchingAccounts = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getPlatform().equalsIgnoreCase(platform)) {
                matchingAccounts.add(account);
            }
        }
        if (matchingAccounts.isEmpty()) {
            System.out.println("No matching account found with platform: " + platform);
        } else {
            System.out.println(matchingAccounts.size() + " matching accounts have been found with platform: " + platform);
        }

        return matchingAccounts;
    }

    @Override
    public List<Account> findByUsername(String username) {
        List<Account> accounts = getAll();
        List<Account> matchingAccounts = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                matchingAccounts.add(account);
            }
        }

        if (matchingAccounts.isEmpty()) {
            System.out.println("No matching account found with username: " + username);
        } else {
            System.out.println(matchingAccounts.size() + " matching accounts have been found with username: " + username);
        }

        return matchingAccounts;
    }

    @Override
    public List<Account> findByEmail(String email) {
        List<Account> accounts = getAll();
        List<Account> matchingAccounts = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getEmail().equalsIgnoreCase(email)) {
                matchingAccounts.add(account);
            }
        }

        if (matchingAccounts.isEmpty()) {
            System.out.println("No matching account found with email: " + email);
        } else {
            System.out.println(matchingAccounts.size() + " matching accounts have been found with email: " + email);
        }
        return matchingAccounts;
    }

    @Override
    public void deleteAccount(String platform, String username, String email, String password) {
        List<Account> accounts = getAll();
        List<Account> updatedAccounts = new ArrayList<>();
        boolean found = false;

        for(Account account : accounts){
            if(account.matchesCredentials(platform, username, email) && account.verifyPassword(password)){
                found = true;
                System.out.println("Account with matching information found. Removing it from file.");
            }else{
                updatedAccounts.add(account);
            }
        }

        if(!found){
            System.out.println("No account found with the following info: " +
                    platform + ", " + username + ", " + email + ", " + password);
            return;
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for(Account account : updatedAccounts){
                writer.write(account.toFileString());
                writer.newLine();
            }
            System.out.println("Account deleted successfully.");
        }catch(IOException e){
            System.err.println("Error while updating the file: " + e.getMessage());
        }
    }

    @Override
    public void updateAccount(String platform, String username, String email, String password, Account newAccount) {
        List<Account> accounts = getAll();
        List<Account> updatedAccounts = new ArrayList<>();
        boolean updated = false;

        for (Account account : accounts) {
            if (account.matchesCredentials(platform, username, email) && account.verifyPassword(password)) {
                System.out.println("Account with matching information found. Updating file");
                updatedAccounts.add(newAccount);
                updated = true;
            } else {
                updatedAccounts.add(account);
            }
        }

        if(!updated){
            System.out.println("No account found with info: " + platform + ", "
            + username + ", " + email + ", " + password);
            return;
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for(Account account : updatedAccounts){
                writer.write(account.toFileString());
                writer.newLine();
            }
            System.out.println("Account updated successfully.");
        }catch(IOException e){
            System.err.println("Error while updating the file: " + e.getMessage());
        }
    }
}
