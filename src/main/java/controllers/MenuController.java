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

    public void signUp(){
        System.out.println("Welcome! ");
        int balance = Integer.parseInt(sc.nextLine());
    }

    public User getUser() throws NullPointerException{
        System.out.println("Welcome to user selection: Press 1 to login to existing account, Press 2 to sign up;");
        String input = sc.nextLine();
        HashMap<String, User> userList = UserService.getUserList();
        User user;
        switch(input.toLowerCase()){
            case "1" : {
                login();

                System.out.println("Please enter account id: ");
                String id = sc.nextLine();

                try{
                    if(userList.containsKey(id)){
                        System.out.println();
                    }
                    System.out.println("account doesn't exist, please try again. Enter -1 to sign up");
                    id = sc.nextLine();
                    if(id.equals("-1")) break;


                    user = userList.get(id);
                    return user;
                }catch(NullPointerException e){
                    System.out.println(e.getMessage());
                }
            } case "2" :{
                user = newUserBuilder();
                return user;
            } 
        }
        user = new User();
        return user;
    }

    public void userUI(User user){
        System.out.println("Dear " + user.getRole()+", What would you like to do today?");
        switch(user.getRole()){
            case "customer":{
                System.out.println("Enter 1 for deposit, 2 for withdrawal, 3 to get transaction log");
                switch(sc.nextLine()){
                    case "1":{
//                        userService.deposit();
                        break;
                    }
                    case "2" :{

                    }

                    case "3" : {

                    }
                }
                break;
            }

            case "clerk" : {
                System.out.println("Dear clerk: What would you like to do today?");
                System.out.println("What should employee be able to do?!? Enter 1 to create account, 2 for deleting accounts, 3 to get transaction log");
                sc.nextLine();
                break;
            }
            case "admin":{
                System.out.println("which account id would you modify today?");
                System.out.println("This account currently has role: XX. Which role do you wanna give this account? ");

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



    private User newUserBuilder() {
        User user;
        System.out.println("Please enter your id");
        String id = sc.nextLine();

        HashMap<String, User> userList = userService.getUserList();
        while(userList.containsKey(id)&&id!="-1"){
            System.out.println("id entered already exists, please try again. Enter -1 to exit. ");
            id = sc.nextLine();
        }

        System.out.println("Please enter your role");
        String role = sc.nextLine();

        while(!(role.equals("customer")||role.equals("admin")||role.equals("clerk"))){
            System.out.println("invalid input, please try again");
            role = sc.nextLine();
        }

        System.out.println("Please enter your balance");
        int balance = Integer.parseInt(sc.nextLine());

        System.out.println("Please enter your password");
        String pwd = sc.nextLine();

        user = new User(id, role, balance, pwd);
        userList.put(id, user);
        return user;
    }
    public static void login(){


        System.out.println("what is your username?");

        String username = sc.nextLine();
        if(username.equals("abc")){
            System.out.println("please enter the pwd");
            String pwd = sc.nextLine();
            if(pwd.equals("123")){
                User user = new User();
                System.out.println("login successful! Welcome "+ user.getRole() + " " + user.getId());
                //check account type: user? clerk? admin?
//                userUI(user);
            }else{
                System.out.println("login failed, please try again. ");
            }
        }else{
            System.out.println("Invalid username");
        }

    }
}
