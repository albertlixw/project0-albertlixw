package daos;

import models.Account;
import models.User;
import java.util.*;

public interface UserDAO {

    public HashMap<Integer, User> findAll();
    public User findUserById(int id);
    public boolean addUser(User user);
    public boolean updateUser(User user);

    public User findUserByUsername(String username);

    public boolean deleteUser(int id);
}
