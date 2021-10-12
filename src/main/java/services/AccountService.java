package services;

import daos.*;
import models.*;
//import models.User;

import java.util.*;

public class AccountService {
       private AccountDAO accountDao = new AccountDAOImpl();
       private Scanner sc = new Scanner(System.in);

       UserDAO userDao = new UserDAOImpl();
       HashMap<Integer, User> userList = userDao.findAll();
       

       public boolean approve(){
              System.out.println("Admin/Clerk approval: Please enter a Admin/Clerk account id");

              User authorized = userList.get(sc.nextLine());
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

       public void makeNewAccount(){
              System.out.println("Please enter your balance");

              int balance = Integer.parseInt(sc.nextLine());
              while(balance < 0||balance>1e50){
                     System.out.println("Balance can't be negative or greater than 1e50");
                     balance = Integer.parseInt(sc.nextLine());
              }
              //clerk approve
              System.out.println("Needs approval from admin/clerk");

              while(!approve()){
                     System.out.println("Your application got denied. Please try again, or come another day. ");
              }
       }

       public List<User> findAllUsersOfAccount(Account acc) {
              return accountDao.findAllUsersOfAccount(acc);
       }

       public List<Account> findAllAccount(){
             return accountDao.findAll(); 
       }

       public List<Account> findAllByUser(User user){
              return accountDao.findAllByUser(user);
       }

       public boolean addUserToAccount(Account account, int addedUserId){
             return accountDao.addUserToAccount(account, addedUserId);
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
              acc.toString();
       }


}
