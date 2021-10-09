package controllers;

import models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.UserService;

import java.util.*;

public class MenuController {

    private static Scanner sc = new Scanner(System.in);
    private static Logger log = LoggerFactory.getLogger(MenuController.class);
    private UserService userService = new UserService();

    public User getUser() throws NullPointerException{
        System.out.println("Welcome to user selection: Press 1 to select existing account, Press 2 to sign up;");
        String input = sc.nextLine();

        HashMap<String, User> userList = userService.getUserList();
        User user;

        switch(input.toLowerCase()){
            case "1" : {
                try{
                    System.out.println("Please enter account id");
                    String id = sc.nextLine();
                    while(!userList.containsKey(id)){
                        System.out.println("account not found, please try again. ");
                        id = sc.nextLine();
                    }
                    System.out.println("account found, please login");
//                    id = sc.nextLine();
//                    if(id.equals("-1")) break;
                    user = userList.get(id);
                    return user;
                }catch(NullPointerException e){
                    log.warn("user exists but failed to get from List. " + e.getMessage() );
                    System.out.println("failed to retrieve account, please try again. ");
                    return getUser();
                }


            } case "2" :{
                user = userService.newUserBuilder();
                return user;
            } 
        }
        System.out.println("invalid choice, please try again. ");
        return getUser();
    }

    public void userUI(User user){
        login(user);
        System.out.println("Dear " + user.getRole()+", What would you like to do today?");
        switch(user.getRole()){
            case "admin":{
                System.out.println("Are you here to assign roles? y/n");
                if(sc.nextLine().equals("y")){
                    userService.assignRole(user);
                }
                    System.out.println("Transferring to clerk's UI");
            }
            case "clerk" : {
                System.out.println("Dear clerk: What would you like to do today? 1. Check any user's info. 2. Change password of a user");
                switch(sc.nextLine()){
                    case "1":{
                        userService.getAnyUserInfo(user);
                        break;
                    }
                    case "2":{
                        userService.getAnyUserInfo(user);
                        userService.changePwd(user);
                        break;
                    }
                }
                System.out.println("Transferring to customer's UI");

            }
            case "customer":{
                System.out.println("Enter 1 for deposit, 2 for withdrawal, 3 for account Info, 4 to change password");
                switch(sc.nextLine()){
                    case "1":{
                        System.out.println("How much would you like to deposit?");
                        double amount = sc.nextDouble();
                        userService.deposit(user, amount);
                        userService.accountInfo(user);
                        break;
                    }
                    case "2" :{
                        System.out.println("How much would you like to withdraw?");
                        double amount = sc.nextDouble();
                        userService.withdraw(user, amount);
                        userService.accountInfo(user);
                        break;
                    }

                    case "3" : {
                        userService.accountInfo(user);
                        break;
                    }
                    case "4":{
                        userService.changePwd(user);
                        break;
                    }
                    case "5": {

                        System.out.println("Which account id do you wanna transfer to?");
                        String accountId = sc.nextLine();
                        System.out.println("How much money do you wanna transfer to? ");
                        double amount = sc.nextDouble();
                        userService.transfer(user, accountId, amount);
                        break;
                    }
                }
                break;
            }



            default: {
                System.out.println("invalid input, please try again");
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

    //check service method to know what role user have
    //return userobj
    //switch role to determine which menu to print




    public static void login(User user){
//        System.out.println("Please enter account id: ");
//        String id = sc.nextLine();

            System.out.println("please enter the pwd");
            String pwd = sc.nextLine();
            if(pwd.equals(user.getPwd())){

                System.out.println("login successful! Welcome "+ user.getRole() + " " + user.getId());
                //check account type: user? clerk? admin?
            }else{
                System.out.println("login failed, please try again. ");
            }
    }
}
