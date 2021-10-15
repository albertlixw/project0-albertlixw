package services;

import controllers.AccountController;
import controllers.UserController;
import daos.AccountDAO;
import daos.AccountDAOImpl;
import daos.UserDAO;
import daos.UserDAOImpl;
import models.Account;
import models.User;
import org.junit.jupiter.api.*;
import utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
//    static UserController userController = new UserController();
//    static AccountController accountController = new AccountController();
    static AccountDAO accountDao = new AccountDAOImpl();
//    static UserDAO userDao = new UserDAOImpl();
    static AccountService accountService = new AccountService();
    static UserService userService = new UserService();

    @BeforeEach
    void beforeEach() {
        try(Connection conn = ConnectionUtil.getConnection()){
            accountDao.addAccount(new Account(2000));    //acc2
            accountService.accountList = accountDao.findAllAccountAsList();

//            User user = new User(2 ,"TestAdmin", "pwd", "keyword");
//            userDao.addUser(user);
//            user = (userDao.findUserByUsername("TestAdmin"));

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void afterEach() {
        accountService.accountList = accountDao.findAllAccountAsList();
        accountService.deleteAccount(accountService.accountList.get(accountService.accountList.size()-1).getAccountId());
//        accountService.deleteAccount(accountService.accountList.get(accountService.accountList.size()-1).getAccountId());
    }

    @Test
    void deposit() {
        try(Connection conn = ConnectionUtil.getConnection()){
            Account acc2 = accountService.accountList.get(accountService.accountList.size()-1);//2000
            Account acc1 = new Account(1000);
            accountDao.addAccount(acc1);
            accountService.accountList = accountDao.findAllAccountAsList();
            acc1 = accountService.accountList.get(accountService.accountList.size()-1);
            accountService.deposit(acc1, 1000);

            accountService.accountList = accountDao.findAllAccountAsList();
            acc1 = accountService.accountList.get(accountService.accountList.size()-1);

            assertTrue(acc1.getBalance() == acc2.getBalance());
            accountService.deleteAccount(accountService.accountList.get(accountService.accountList.size()-1).getAccountId());

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void withdraw() {
        try(Connection conn = ConnectionUtil.getConnection()){
            Account acc2 = accountService.accountList.get(accountService.accountList.size()-1);//2000
            Account acc1 = new Account(3000);
            accountDao.addAccount(acc1);
            accountService.accountList = accountDao.findAllAccountAsList();
            acc1 = accountService.accountList.get(accountService.accountList.size()-1);
            accountService.withdraw(acc1, 1000);

            accountService.accountList = accountDao.findAllAccountAsList();
            acc1 = accountService.accountList.get(accountService.accountList.size()-1);

            assertTrue(acc1.getBalance() == acc2.getBalance());
            accountService.deleteAccount(accountService.accountList.get(accountService.accountList.size()-1).getAccountId());

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void transfer() {
        try(Connection conn = ConnectionUtil.getConnection()){
            Account acc2 = accountService.accountList.get(accountService.accountList.size()-1);//2000
            Account acc1 = new Account(3000);
            accountDao.addAccount(acc1);

            accountService.accountList = accountDao.findAllAccountAsList();

            acc1.setAccountId(accountService.accountList.get(accountService.accountList.size()-1).getAccountId());
//            acc2.setAccountId(accountService.accountList.get(accountService.accountList.size()-2).getAccountId());
            accountService.accountMap = accountDao.findAll();
            accountService.transfer(acc1, acc2.getAccountId(), 500);

            accountService.accountMap = accountDao.findAll();
            acc1 = accountService.accountMap.get(acc1.getAccountId());
            acc2 = accountService.accountMap.get(acc2.getAccountId());
            assertTrue(acc1.getBalance() == acc2.getBalance());
            accountService.deleteAccount(acc1.getAccountId());
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Test
    void deleteAccount() {
        accountService.accountList = accountService.findAllAccountAsList();
        int listSize = accountService.accountList.size();
        accountDao.addAccount(new Account(500.0));
        accountService.accountList = accountService.findAllAccountAsList();
        accountService.deleteAccount(accountService.accountList.get(accountService.accountList.size()-1).getAccountId());
        assertEquals(listSize, accountService.findAllAccountAsList().size());
        
    }

    @Test
    void accountInfo() {
        Account acc2 = accountService.accountList.get(accountService.accountList.size()-1);//2000
        assertTrue(accountService.accountInfo(acc2));
    }
    
    @Test
    void findAllUsersOfAccount() {
        HashMap<Integer, User> accountHashMap = accountService.findAllUsersOfAccount(accountService.accountList.get(accountService.accountList.size()-1));

        assertEquals(accountHashMap.size(), 0);
    }

    @Test
    void findAllAccount() {
        HashMap<Integer, Account> accountHashMap = accountService.findAllAccount();
        assertEquals(accountHashMap.size(), 5);
    }

    @Test
    void findAllByUser() {
        User user = userService.userList.get(1);
        HashMap<Integer, Account> accountHashMap = accountService.findAllByUser(user);
        assertEquals(accountHashMap.size(), 3);
        
    }

    @Test
    void addUserToAccount() {
        Account acc2 = accountService.accountList.get(accountService.accountList.size()-1);//2000
        User user = userService.userList.get(1);
        accountService.addUserToAccount(acc2.getAccountId(), 1);
        HashMap<Integer, Account> accountHashMap = accountService.findAllByUser(user);
        assertEquals(accountHashMap.size(), 4);
    }



    @Test
    void findById() {
        Account acc = accountService.findById(1);
        assertEquals(acc.getAccountId(), 1);
    }


}