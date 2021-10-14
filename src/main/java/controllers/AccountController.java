package controllers;

import daos.AccountDAO;
import daos.AccountDAOImpl;
import models.Account;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.AccountService;

import java.util.*;

public class AccountController {
    private static AccountService accountService = new AccountService();
    private static UserController userController = new UserController();
    private static AccountDAO accountDao = new AccountDAOImpl();
    private static Logger log = LoggerFactory.getLogger(MenuController.class);

//    private static HashMap<Integer, Account> accountList = accountService.findAllAccount();
    private Scanner sc = new Scanner(System.in);

    public void deleteAccount(){
        boolean bool = userController.approve();
        if(bool) {
            System.out.println("Which account id do you want to delete?");
            int id = Integer.parseInt(sc.nextLine());
            accountService.deleteAccount(id);
        }else{
            System.out.println("failed to delete account");
        }
    }



    public void depositAdmin(User user){
        System.out.println("Which account id would you like to deposit money into?");
        int accId = Integer.parseInt(sc.nextLine());
        Account accountSelected = accountService.accountList.get(accId);
        System.out.println("How much would you like to deposit?");
        double amount = sc.nextDouble();
        sc.nextLine();
        if(user.getLevel()>0){
            accountService.deposit(accountSelected, amount);
        }
    }
    public void withdrawAdmin(User user){
        System.out.println("Which account id would you like to withdraw money from?");
        int accId = Integer.parseInt(sc.nextLine());
        Account accountSelected = accountService.accountList.get(accId);
        System.out.println("How much would you like to withdraw?");
        double amount = sc.nextDouble();
        sc.nextLine();
        if(user.getLevel()>0){
            accountService.withdraw(accountSelected, amount);
        }
    }

    public void transferAdmin(User user){
        System.out.println("Which account id would you like to withdraw money from?");
        int accId = Integer.parseInt(sc.nextLine());
        Account accountSelected = accountService.accountList.get(accId);
        System.out.println("How much would you like to withdraw?");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Which account id would you like to deposit money into?");
        int receiverId = Integer.parseInt(sc.nextLine());
//        Account receiver = accountList.get(receiverId);
        if(user.getLevel()>0){
            accountService.transfer(accountSelected,receiverId,amount);
        }
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

        while(!userController.approve()){
            System.out.println("Your application got denied. Please try again, or come another day. ");
        }
        Account acc = new Account(balance);
        accountDao.addAccount(acc);
        //cuz account without user might as well not exist.
        //This is fine since I know it's single thread.
        accountService.accountList = accountDao.findAll();
        int accid = accountService.accountList.get(accountService.accountList.size()-1).getAccountId();
        accountDao.addUserToAccount(accid, addedUserId);
        log.info("new account made with account id: " + accid);
        return acc;
    }

}
