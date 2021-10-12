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



    public void userUI(){
        System.out.println("Welcome to User UI");
        User user = userService.getUser();
        login(user);
        System.out.println("Dear " + user.getUsername() + ", level " + user.getLevel()+" user, What would you like to do today?");
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

            //service methods logging every action.
        //withdraw and deposits happen at time?
        //set level in config file, appendix.
        //definitely set level to info.
        // when level is higher than warn/error
        //print it out?
        //warn when error?

    }

    private void clerkUI(User user) {
        System.out.println("Dear clerk: What would you like to do today? 1. Check any user's info. 2. Change password of an account");
        switch(sc.nextLine()){
            case "1":{
                userService.getAnyUserInfo(user);
                break;
            }
            case "2":{
                //TODO: change account info
                userService.getAnyUserInfo(user);
                userService.changePwd(user);
                break;
            }
        }
    }

    private void adminUI(User user) {
        System.out.println("Are you here to assign roles? y/n");
        if(sc.nextLine().equals("y")){
            userService.assignRole(user);
        }else{
            //TODO
            //Edit USER and ACCOUNT INFO.
        }
    }

    //check service method to know what role user have
    //return userobj
    //switch role to determine which menu to print

    public void printAllAccounts(List<Account> list){
        for(Account ac : list){
            System.out.println(ac.getAccountId());
        }
    }

    public void printAllUsers(List<User> list){
        for(User user : list){
            System.out.println(user.getId());
        }
    }

    public void customerUI(User user){
        System.out.println("Enter 1 to select account id, 2 to change password, 3 to get userInfo, 4 to create a new account for userid: " + user.getId());
        switch(sc.nextLine()){
            case "1":{
                //TODO SELECT all users that owns this joint account.
                List<Account> accountList = accountService.findAllByUser(user);
                System.out.println("Accounts under current user: " );
                printAllAccounts(accountList);
                System.out.println("Please select an account id");
                int accountId = sc.nextInt();
                Account acc = accountService.findById(accountId);
                System.out.println("Enter 1 for deposit, 2 for withdrawal, 3 for account info, " +
                        "4 to transfer to another account, 5 to add another user for this account, " +
                        "6 to List all users that owns this account. ");
                String input = sc.nextLine();
                switch(input){
                    case "1" :{
                        System.out.println("How much would you like to deposit?");
                        double amount = sc.nextDouble();
                        accountService.deposit(acc, amount);
                        accountService.accountInfo(acc);
                        customerUI(user);
                        break;
                    }
                    case "2" :{
                        System.out.println("How much would you like to withdraw?");
                        double amount = sc.nextDouble();
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
//                                Account receivingAccount = accountService.findById(receivingAccountId);
                        System.out.println("How much money do you wanna transfer? ");
                        double amount = sc.nextDouble();
                        userService.transfer(acc, receivingAccountId, amount);
                        customerUI(user);
                        break;
                    }
                    case "5" : {
                        System.out.println("Which user would you like to add to this account? ");
                        int addedUserId = sc.nextInt();
                        accountService.addUserToAccount(acc, addedUserId);
                        customerUI(user);
                        break;
                    }
                    case "6":{
                        List<User> userList = accountService.findAllUsersOfAccount(acc);
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
            }
            case "2":{
                userService.changePwd(user);
                customerUI(user);
                break;
            }
            case "3":{
                userController.userInfo(user);
                customerUI(user);
                break;
            }
            case "4":{
                //make new account, attach user and account together.
                Account acc = accountService.makeNewAccount();
                accountService.addUserToAccount(acc, user.getId());
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

                System.out.println("login successful! Welcome level "+ user.getLevel() + " user, UserId: " + user.getId());
                //check account type: user? clerk? admin?
            }else{
                System.out.println("One/both of them failed, please try again. ");
                userUI();
            }
    }
}
