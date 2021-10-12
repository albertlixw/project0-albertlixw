package daos;

//import models.Account;
import models.*;

import java.util.*;

public interface AccountDAO {
      public List<Account> findAll();
      public Account findById(int accountId);
      public boolean updateAccount(Account account);
      public boolean addAccount(Account account);

      public boolean addUserToAccount(Account account, int addedUserId);

    List<Account> findAllByUser(User user);

      public List<User> findAllUsersOfAccount(Account acc);
}
