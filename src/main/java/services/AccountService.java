package services;

import controllers.MenuController;
import controllers.UserController;
import daos.*;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import models.User;

import java.util.*;

public class AccountService {
//       private static UserDAO userDao = new UserDAOImpl();
       private static AccountDAO accountDao = new AccountDAOImpl();
//       private static UserController userController = new UserController();
       private static UserService userService = new UserService();

       private Scanner sc = new Scanner(System.in);
       private static Logger log = LoggerFactory.getLogger(MenuController.class);

//       private static HashMap<Integer, User> userList = userDao.findAll();
       public static HashMap<Integer, Account> accountList = accountDao.findAll();

       public void deposit(Account account, double amount){
              while(amount < 0){
                     System.out.println("invalid input, please try again. ");
                     amount = sc.nextDouble();
                     sc.nextLine();
              }
              account.setBalance(account.getBalance() + amount);
              accountDao.updateAccount(account);
              System.out.println("Deposit successful. ");
       }

       public void withdraw(Account account, double amount){
              while(amount > account.getBalance() || amount < 0){
                     System.out.println("insufficient balance, please try again. ");
                     amount = sc.nextDouble();
                     sc.nextLine();
              }
              account.setBalance(account.getBalance() - amount);
              accountDao.updateAccount(account);
              System.out.println("Withdraw successful. ");

       }
       public void transfer(Account acc, int receivingAccountId, double amount) {
              withdraw(acc, amount);
              deposit(accountList.get(receivingAccountId), amount);
              System.out.println("Transfer completed. ");
              log.info("Account id: " + acc.getAccountId() + " just transferred $" + amount + " into account id "+ receivingAccountId);
       }

       public void deleteAccount(int accountId){
              accountDao.deleteMapping(accountId);
              accountDao.deleteAccount(accountId);
              userService.userList.remove(accountId);
              System.out.println("account id: " + accountId + " deleted successfully");
       }
       
       public HashMap<Integer, User> findAllUsersOfAccount(Account acc) {
              return accountDao.findAllUsersOfAccount(acc);
       }

       public HashMap<Integer, Account> findAllAccount(){
             return accountDao.findAll(); 
       }

       public HashMap<Integer, Account> findAllByUser(User user){
              return accountDao.findAllByUser(user);
       }

       public boolean addUserToAccount(int accountid, int addedUserId){
             return accountDao.addUserToAccount(accountid, addedUserId);
       }

       public Account findById(int id){
              return accountDao.findById(id);
       }


       public void accountInfo(Account acc) {
              System.out.println(acc.toString());
       }


}
