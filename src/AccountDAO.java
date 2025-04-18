import java.util.List;

public interface AccountDAO {
    void saveAccount(Account account);
    List<Account> getAll();
    List<Account> findByPlatform(String platform);
    List<Account> findByUsername(String username);
    List<Account> findByEmail(String email);
    void deleteAccount(String platform, String username, String email, String password);
    void updateAccount(String platform, String username, String email, String password, Account newAccount);
}
