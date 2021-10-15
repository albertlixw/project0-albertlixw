package services;

import daos.UserDAO;
import daos.UserDAOImpl;
import models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static UserService userService = new UserService();
    static UserDAO userDAO = new UserDAOImpl();

    @Test
    void deleteUser() {
        int listSize = userService.userList.size();
        userDAO.addUser(new User(2, "You are never gone name yourself with this", "pwd", "keyword"));
        userService.deleteUser(userDAO.findUserByUsername("You are never gone name yourself with this").getId());
        userService.userList = userDAO.findAll();
        assertEquals(listSize, userService.userList.size());
    }

    @Test
    void login() {
        User admin1 = userService.userList.get(1);
        assertTrue(userService.login(admin1, "admin", "012"));
    }
}