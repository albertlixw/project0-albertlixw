package services;

import daos.*;
import models.*;
//import models.User;

import java.util.*;

public class AccountService {
       private static AccountDAO accountDao = new AccountDAOImpl();
       private Scanner sc = new Scanner(System.in);

       private static UserDAO userDao = new UserDAOImpl();
       private static HashMap<Integer, User> userList = userDao.findAll();
       private static List<Account> accountList = accountDao.findAll();
       public boolean approve(){
              System.out.println("Admin/Clerk approval: Please enter a Admin/Clerk User id");

              User authorized = userList.get(Integer.parseInt(sc.nextLine()));
              while(authorized==null){
                     System.out.println("Userid not found, please try again. ");
                     authorized = userList.get(sc.nextLine());
              }
              while(authorized.getLevel()<1){
                     System.out.println("Customers cannot approve applications. Please try again. ");
                     authorized = userList.get(sc.nextLine());
              }
              System.out.println("Dear admin/clerk, please enter your password");
              String pwd = sc.nextLine();
              while(!authorized.getPwd().equals(pwd)){
                     System.out.println("password incorrect, please try again");
                     pwd = sc.nextLine();
              }

              System.out.println("login successful! Do you approve this action? y/n");
              String input = sc.nextLine();
              if(input.equals("y")){
                     System.out.println("approved");
                     return true;
              }else if(input.equals("n")){
                     System.out.println("denied");
                     return false;
              }
              System.out.println("Unexpected input detected. Please try again. ");
              return approve();
       }


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

              while(!approve()){
                     System.out.println("Your application got denied. Please try again, or come another day. ");
              }
              Account acc = new Account(balance);
              accountDao.addAccount(acc);
              //cuz account without user might as well not exist. 
              //This is fine since I know it's single thread. 
              accountList = accountDao.findAll();
              int accid = accountList.size();
              accountDao.addUserToAccount(accid, addedUserId);
              return acc;
       }

       public List<User> findAllUsersOfAccount(Account acc) {
              return accountDao.findAllUsersOfAccount(acc);
       }

       public List<Account> findAllAccount(){
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
              }
              account.setBalance(account.getBalance() + amount);
       }

       public void withdraw(Account account, double amount){
              while(amount > account.getBalance() || amount < 0){
                     System.out.println("insufficient balance, please try again. ");
                     amount = sc.nextDouble();
              }
              account.setBalance(account.getBalance() - amount);
       }

       public void accountInfo(Account acc) {
              System.out.println(acc.toString());
       }


}
