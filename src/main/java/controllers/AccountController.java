package controllers;

import models.Account;
import models.User;
import services.AccountService;

import java.util.*;

public class AccountController {
    private static AccountService accountService = new AccountService();
    private static HashMap<Integer, Account> accountList = accountService.findAllAccount();
    private Scanner sc = new Scanner(System.in);

    public void depositAdmin(User user){
        System.out.println("Which account id would you like to deposit money into?");
        int accId = Integer.parseInt(sc.nextLine());
        Account accountSelected = accountList.get(accId);
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
        Account accountSelected = accountList.get(accId);
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
        Account accountSelected = accountList.get(accId);
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


}
