package services;

import java.util.*;

import daos.*;
import models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.*;
import controllers.*;

public class UserService {

//    private AccountDAO  accountDao = new AccountDAOImpl();
    private static UserDAO userDao = new UserDAOImpl();
    private static AccountService accountService = new AccountService();
    private static UserController userController = new UserController();
    private static Scanner sc = new Scanner(System.in);

    private static HashMap<Integer, User> userList = getUserList();
    private static List<Account> accountList = new ArrayList<>();





    
    public static HashMap<Integer, User> getUserList() {
        return userDao.findAll();
//        HashMap <String, User> userList = new HashMap<>();
//        User user = new User(0, "abc", "123", "keyword");
//        userList.put("1", user);
//
//        User clerk = new User(1, "user1","123", "keyword" );
//        userList.put("2", clerk);
//
//        User admin = new User(2, "admin", "234", "keyword");
//        userList.put("0", admin);
//        HashMap<Integer, User> userList = userDao.findAll();
    }







    //only admin can do this. 
    public void assignRole(User user){
        if(user.getLevel()<2){
            System.out.println("Unauthorized action.");
            return;
        }
        System.out.println("Which accountId would you like to modify?");
        String modifiedAccountId = sc.nextLine();
        User modifiedAccount = userList.get(modifiedAccountId);
        userController.userInfo(modifiedAccount);
        System.out.println("Which role would you like to give account " + modifiedAccount.getId() + "?");
        modifiedAccount.setLevel(Integer.parseInt(sc.nextLine()));
        userController.userInfo(modifiedAccount);
    }

    //clerk and admin can use this. 
    public void getAnyUserInfo(User user){
        if(user.getLevel() < 1){
            System.out.println("Unauthorized action.");
            return;
        }
        System.out.println("Which account id would you like to check info?");
        String checkAccountId = sc.nextLine();
        userController.userInfo(userList.get(checkAccountId));
        System.out.println("Account Keyword: " + userList.get(checkAccountId).getKeyword());
    }

    public void changePwdClerk(User user){
        System.out.println("Which user id would you like to change password?");
        String userid = sc.nextLine();
        User account4Change = userList.get(userid);

        String pwd, confirmPwd;
        System.out.println("Please enter your password");
        pwd = sc.nextLine();
        System.out.println("Please confirm your password");
        confirmPwd = sc.nextLine();
        while(!pwd.equals(confirmPwd)){
            System.out.println("Password doesn't match. Please enter your password");
            pwd = sc.nextLine();
            System.out.println("Please confirm your password");
            confirmPwd = sc.nextLine();
        }
        account4Change.setPwd(pwd);

        userController.userInfo(account4Change);
        userDao.updateUser(account4Change);
        System.out.println("pwd change successful!");
    }





//    private static UserDAO playerDAO = new UserDAO();

//    public User createNewUser(String id, String role, int balance){
//        User user = new User(id, role, balance);
//        return user;
//    }

}
