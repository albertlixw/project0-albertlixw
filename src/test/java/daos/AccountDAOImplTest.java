package daos;

import models.Account;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOImplTest {

    private AccountDAO accountDao = new AccountDAOImpl();

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void addAccount() {
        Account acc = new Account(13.14);
        accountDao.addAccount(acc);
        int accId = acc.getAccountId();

        Account account = new Account(100.10);
        accountDao.addAccount(account);
        int accountId = account.getAccountId();
        HashMap<Integer, Account> accountList = accountDao.findAll();

        assertTrue(accountList.containsKey(accId));
        assertTrue(accountList.containsKey(accountId));

//        Assert.assertEquals(department.getName(), departments.get(0).getName());
//        Assert.assertEquals(employee.getEmail(), employees.get(0).getEmail());
    }

    @Test
    void addUserToAccount() {
    }

    @Test
    void findAllByUser() {
    }

    @Test
    void findAllUsersOfAccount() {
    }
}