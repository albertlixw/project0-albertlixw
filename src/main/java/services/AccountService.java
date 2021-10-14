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
       private static UserDAO userDao = new UserDAOImpl();
       private static AccountDAO accountDao = new AccountDAOImpl();
       private static UserController userController = new UserController();

       private Scanner sc = new Scanner(System.in);
       private static Logger log = LoggerFactory.getLogger(MenuController.class);

       private static HashMap<Integer, User> userList = userDao.findAll();
       private static HashMap<Integer, Account> accountList = accountDao.findAll();

       public Account makeNewAccount(int addedUserId){

              System.out.println("Please enter your balance");
              double balance = sc.nextDouble();
              sc.nextLine();

              while(balance < 0||balance>1e50){
                     System.out.println("Balance can't be negative or greater than 1e50");
                     balance = sc.nextDouble();
                     sc.nextLine();              }

              //clerk approve
              System.out.println("Needs approval from admin/clerk");

              while(!userController.approve()){
                     System.out.println("Your application got denied. Please try again, or come another day. ");
              }
              Account acc = new Account(balance);
              accountDao.addAccount(acc);
              //cuz account without user might as well not exist. 
              //This is fine since I know it's single thread. 
              accountList = accountDao.findAll();
              int accid = accountList.get(accountList.size()-1).getAccountId();
              accountDao.addUserToAccount(accid, addedUserId);
              log.info("new account made with account id: " + accid);
              return acc;
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
              log.info("Account id: " + acc.getAccountId() + " just transferred ");
       }
       public void accountInfo(Account acc) {
              System.out.println(acc.toString());
       }

       public void deleteAccount(User user){
              boolean bool = userController.approve();
              if(bool) {
                     System.out.println("Which account id do you want to delete?");
                     int id = Integer.parseInt(sc.nextLine());
                     accountDao.deleteAccount(id);
                     userList.remove(id);
                     System.out.println("account id: " + id + " deleted successfully");

              }else{
                     System.out.println("failed to delete account");
              }
       }
}
