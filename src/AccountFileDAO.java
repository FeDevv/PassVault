import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class AccountFileDAO implements AccountDAO {
    private File file;

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
    public Account findByPlatform(String platform) {
        List<Account> accounts = getAll();

        for (Account account : accounts) {
            if (account.getPlatform().equalsIgnoreCase(platform)) {
                System.out.println("The following account has been found: " + account.toString());
                return account;
            }
        }

        System.out.println("No account with platform '" + platform + "' found.");
        return null;
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

        return matchingAccounts; // Restituisci una lista vuota se non ci sono account corrispondenti
    }


    @Override
    public void deleteAccount(Account account) {
        //rimuovere un account dal file
    }

    @Override
    public void updateAccount(Account updatedAccount) {
        //aggiornare un account
    }

}
