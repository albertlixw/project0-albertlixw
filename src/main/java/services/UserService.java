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

    private static UserController userController = new UserController();
    private static MenuController menuController = new MenuController();

    private static AccountService accountService = new AccountService();

    private static Scanner sc = new Scanner(System.in);

    private static HashMap<Integer, User> userList = getUserList();
//    private static HashMap<Integer, Account> accountList = accountService.findAllAccount();

    private static Logger log = LoggerFactory.getLogger(MenuController.class);





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


    public void deleteUser(User user){
        boolean bool = userController.approve();
        if(bool) {
            System.out.println("Which user id do you want to delete?");
            int id = Integer.parseInt(sc.nextLine());
            userDao.deleteUser(id);
            userList.remove(id);
            System.out.println("userid: " + id + " deleted successfully");
        }else{
            System.out.println("failed to delete account");
        }
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
        menuController.printAllUsers(userList);
        System.out.println("Which user id would you like to check info?");
        int checkUserId = Integer.parseInt(sc.nextLine());
        User selectedUser = userList.get(checkUserId);
        userController.userInfo(selectedUser);
        HashMap<Integer, Account> accountsOfUser = accountService.findAllByUser(selectedUser);
        menuController.printAllAccounts(accountsOfUser);
    }







//    private static UserDAO playerDAO = new UserDAO();

//    public User createNewUser(String id, String role, int balance){
//        User user = new User(id, role, balance);
//        return user;
//    }

}
