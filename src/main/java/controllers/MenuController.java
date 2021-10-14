package controllers;

import models.*;
import controllers.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.AccountService;
import services.UserService;

import java.util.*;
import java.util.List;

public class MenuController {

    private static Scanner sc = new Scanner(System.in);
//    private static Logger log = LoggerFactory.getLogger(MenuController.class);
    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();

    private UserController userController = new UserController();

    HashMap<Integer, User> userList = userService.getUserList();

    public void userUI(){
        System.out.println("Welcome to User UI");
        User user = userController.getUser();
        login(user);
        System.out.println(user.getUsername() + ", level " + user.getLevel()+" user, userId: " + user.getId());
        switch(user.getLevel()){
            case 2:{
                adminUI(user);
                System.out.println("Transferring to clerk's UI");
            }
            case 1 : {
                clerkUI(user);
                System.out.println("Transferring to customer's UI");
            }
            case 0:{
                customerUI(user);
                break;
            }
            default: {
                System.out.println("invalid input, please try again");
                userUI();
                break;
            }
        }
    }

    private void clerkUI(User user) {
        System.out.println("Dear clerk: What would you like to do today? 0. go to customer UI. 1. Check any user's info. 2. Change password of an account. ");
        switch(sc.nextLine()){
            case "0":{
                break;
            }
            case "1":{
//                userList = userService.getUserList();
                userService.getAnyUserInfo(user);
                clerkUI(user);
                break;
            }
            case "2":{
                userService.getAnyUserInfo(user);
                userController.changePwdClerk(user);
                clerkUI(user);
                break;
            }
        }
    }

    private void adminUI(User user) {
        System.out.println("Dear admin. What would you like to do today? ");
        System.out.println("Enter: 0 - logout; " +
                "1 - assign roles?; " +
                "2 - Edit any USER and ACCOUNT; " +
                "3 - Delete a User;" +
                "4 - Delete an Account; ");
        
        String input = sc.nextLine();
        switch(input){
            case "0":{
                break;
            }
            case "1":{

                userService.assignRole(user);
                adminUI(user);
                break;

            }
        }
    }

    //check service method to know what role user have
    //return userobj
    //switch role to determine which menu to print



    public void customerUI(User user){
        System.out.println("Welcome, dear customer. " +
                "Enter 0 to logout；" +
                "1：select account id; " +
                "2: change password; " +
                "3: get userInfo; " +
                "4: create a new account for userid: " + user.getId());
        switch(sc.nextLine()){
            case "0":{
                break;
            }
            case "1":{

                HashMap<Integer, Account> accountList = accountService.findAllByUser(user);
                System.out.println("Accounts under current user: " );
                printAllAccounts(accountList);
                System.out.println("Please select an account id");
                int accountId = sc.nextInt();
                sc.nextLine();

                while(!accountList.containsKey(accountId))  {
                    System.out.println("You don't seem to own this account, please select from the list. ");
                    accountId = sc.nextInt();
                    sc.nextLine();
                }
                Account acc = accountList.get(accountId);

                System.out.println("Enter 1 for deposit, 2 for withdrawal, 3 for account info, " +
                        "4 to transfer to another account, 5 to add another user for this account, " +
                        "6 to List all users that owns this account. ");
                String input = sc.nextLine();
                switch(input){
                    case "1" :{
                        System.out.println("How much would you like to deposit?");
                        double amount = sc.nextDouble();
                        sc.nextLine();

                        accountService.deposit(acc, amount);
                        accountService.accountInfo(acc);
                        customerUI(user);
                        break;
                    }
                    case "2" :{
                        System.out.println("How much would you like to withdraw?");
                        double amount = sc.nextDouble();
                        sc.nextLine();

                        accountService.withdraw(acc, amount);
                        accountService.accountInfo(acc);
                        customerUI(user);
                        break;
                    }

                    case "3" : {
                        accountService.accountInfo(acc);
                        customerUI(user);
                        break;
                    }
                    case "4": {

                        System.out.println("Which account id do you wanna transfer to?");
                        int receivingAccountId = sc.nextInt();
                        sc.nextLine();

//                                Account receivingAccount = accountService.findById(receivingAccountId);
                        System.out.println("How much money do you wanna transfer? ");
                        double amount = sc.nextDouble();
                        sc.nextLine();

                        accountService.transfer(acc, receivingAccountId, amount);
                        customerUI(user);
                        break;
                    }
                    case "5" : {
                        System.out.println("Which user would you like to add to this account? ");
                        int addedUserId = sc.nextInt();
                        sc.nextLine();
//                        System.out.println(acc.getAccountId());
                        accountService.addUserToAccount(acc.getAccountId(), addedUserId);
                        customerUI(user);
                        break;
                    }
                    case "6":{
                        HashMap<Integer, User> userList = accountService.findAllUsersOfAccount(acc);
                        System.out.println("This account is owned by these user IDs: ");
                        printAllUsers(userList);
                        customerUI(user);
                        break;
                    }
                    default: {
                        System.out.println("invalid input, please try again");
                        customerUI(user);
                        break;
                    }
                }
                break;
            }
            case "2":{
                userController.changePwd(user);
                customerUI(user);
                break;
            }
            case "3":{
                userController.userInfo(user);
                customerUI(user);
                break;
            }
            case "4":{
                //make new account, attach user to account together.
                Account acc = accountService.makeNewAccount(user.getId());
//                accountService.addUserToAccount(acc, user.getId());
                customerUI(user);
                break;
            }
            default: {
                System.out.println("invalid input, please try again");
                customerUI(user);
                break;
            }
        }
    }

    public void login(User user){
//        System.out.println("Please enter account id: ");
//        String id = sc.nextLine();
            System.out.println("Please enter your unique username: ");
            String username = sc.nextLine();
            System.out.println("please enter the pwd");
            String pwd = sc.nextLine();
            if(username.equals(user.getUsername())&&pwd.equals(user.getPwd())){

                System.out.println("login successful! Welcome back, level "+ user.getLevel() + " user, UserId: " + user.getId());
                //check account type: user? clerk? admin?
            }else{
                System.out.println("One/both of them failed, please try again. ");
                userUI();
            }
    }


    public void printAllAccounts(HashMap<Integer, Account> list){
        for(Integer i : list.keySet()){
            System.out.println(list.get(i).toString());
        }
    }

    public void printAllUsers(HashMap<Integer,User> list){
        for(Integer i : list.keySet()){
            System.out.println(list.get(i).toString());
        }
    }
}
