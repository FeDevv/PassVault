import java.util.Scanner;
import java.util.List;

public class Main {

    public static void printMenu(){
        System.out.println("\n----- MENU -----");
        System.out.println("1. Add new account");
        System.out.println("2. View all accounts");
        System.out.println("3. Find accounts by platform");
        System.out.println("4. Find accounts by username");
        System.out.println("5. Find accounts by email");
        System.out.println("6. Update an account");
        System.out.println("7. Delete an account");
        System.out.println("0. Exit\n");
    }

    public static void addAccount(Scanner sc, AccountFileDAO dao){
        System.out.print("Insert platform: ");
        String platform = sc.nextLine();

        System.out.print("Insert username: ");
        String username = sc.nextLine();

        System.out.print("Insert email: ");
        String email = sc.nextLine();

        System.out.print("Insert password: ");
        String password = sc.nextLine();

        Account account = new Account(platform, username, email, password);
        dao.saveAccount(account);
    }

    public static void seeAllAccounts(AccountFileDAO dao){
        List<Account> accounts = dao.getAll();

        if(accounts.isEmpty()){
            System.out.println("No account found (the file is empty.)");
            return;
        }
        System.out.println("List of the saved accounts:");
        for(Account account : accounts) {
            System.out.println(account);
        }
    }

    public static void findByPlatform(Scanner sc, AccountFileDAO dao){
        System.out.print("Enter the platform to filter results: ");
        String platform = sc.nextLine();

        List<Account> matchingAccounts = dao.findByPlatform(platform);

        if(!matchingAccounts.isEmpty()){
            System.out.println("List of matching accounts: ");
            for(Account account : matchingAccounts){
                System.out.println(account);
            }
        }
    }

    public static void findByUsername(Scanner sc, AccountFileDAO dao){
        System.out.print("Enter the username to filter results: ");
        String username = sc.nextLine();

        List<Account> matchingAccounts = dao.findByUsername(username);

        if(!matchingAccounts.isEmpty()){
            System.out.println("List of matching accounts:");
            for(Account account : matchingAccounts){
                System.out.println(account);
            }
        }
    }

    public static void findByEmail(Scanner sc, AccountFileDAO dao){
        System.out.print("Enter the email to filter results: ");
        String email = sc.nextLine();

        List<Account> matchingAccounts = dao.findByEmail(email);

        if(!matchingAccounts.isEmpty()){
            System.out.println("List of matching accounts:");
            for(Account account : matchingAccounts){
                System.out.println(account);
            }
        }
    }

    public static void updateAccount(Scanner sc, AccountFileDAO dao){
        System.out.println("Enter the information of the account to update:");
        System.out.print("Old platform: ");
        String oldPlatform = sc.nextLine();
        System.out.print("Old username: ");
        String oldUsername = sc.nextLine();
        System.out.print("Old email: ");
        String oldEmail = sc.nextLine();

        boolean exists = dao.getAll().stream()
                .anyMatch(acc -> acc.getPlatform().equalsIgnoreCase(oldPlatform)
                        && acc.getUsername().equalsIgnoreCase(oldUsername)
                        && acc.getEmail().equalsIgnoreCase(oldEmail));

        if (!exists) {
            System.out.println("No account found with the given platform, username, and email.");
            return;
        }

        System.out.print("Old password: ");
        String oldPassword = sc.nextLine();

        System.out.println("Enter data of new Account:");
        System.out.print("new platform: ");
        String newPlatform = sc.nextLine();
        System.out.print("new username: ");
        String newUsername = sc.nextLine();
        System.out.print("new email: ");
        String newEmail = sc.nextLine();
        System.out.print("New password: ");
        String newPassword = sc.nextLine();

        Account newAccount = new Account(newPlatform, newUsername, newEmail, newPassword);

        dao.updateAccount(oldPlatform, oldUsername, oldEmail, oldPassword, newAccount);
    }

    public static void deleteAccount(Scanner sc, AccountFileDAO dao){
        System.out.println("Enter the information of the account to be deleted.");
        System.out.print("Platform: ");
        String platform = sc.nextLine();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        dao.deleteAccount(platform, username, email, password);
    }

    public static void setMasterPassword(Scanner sc) {
        String masterPassword = "";

        while (masterPassword.isEmpty()) {
            System.out.print("Enter the Master Password: ");
            masterPassword = sc.nextLine().trim();

            if (masterPassword.isEmpty()) {
                System.out.println("Master password cannot be empty. Please try again.");
            }
        }

        SessionManager.setMasterPassword(masterPassword);
        System.out.println("Master password correctly set!");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\t\tPassVault V1.0\n");
        System.out.println("""
                Thanks for trying PassVault! Please remember that this software is provided for educational and demonstration purposes.\
                
                We recommend that you do not use it to store important personal data or in real-world scenarios.\
                
                The user assumes full responsibility for any use outside of these purposes.""");

        System.out.println("Would you like to proceed? [Y/n]");

        String choice;
        while(true) {
            System.out.print(">> ");
            choice = sc.nextLine();

            if (choice.equalsIgnoreCase("n")) {
                sc.close();
                System.out.println("Exiting...");
                return;
            } else if (choice.equalsIgnoreCase("y")) {
                setMasterPassword(sc);

                AccountFileDAO dao = new AccountFileDAO("accounts.txt");

                while(true) {
                    printMenu();
                    System.out.print(">> ");
                    String option = sc.nextLine();
                    System.out.println();

                    switch(option){
                        case "1":
                            addAccount(sc, dao);
                            break;
                        case "2":
                            seeAllAccounts(dao);
                            break;
                        case "3":
                            findByPlatform(sc, dao);
                            break;
                        case "4":
                            findByUsername(sc, dao);
                            break;
                        case "5":
                            findByEmail(sc, dao);
                            break;
                        case "6":
                            updateAccount(sc, dao);
                            break;
                        case "7":
                            deleteAccount(sc, dao);
                            break;
                        case "0":
                            sc.close();
                            SessionManager.clearSession();
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid option. Please select a valid number from the menu.");
                            break;
                    }
                }
            } else {
                System.out.println("Invalid choice. Please retry.");
            }
        }
    }
}