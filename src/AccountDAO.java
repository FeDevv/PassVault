import java.util.List;

public interface AccountDAO {
    void saveAccount(Account account);
    List<Account> getAll();
    Account findByPlatform(String platform);
    List<Account> findByUsername(String username);
    void deleteAccount(Account account);
    void updateAccount(Account updatedAccount);
}
