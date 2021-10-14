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
    private static Logger log = LoggerFactory.getLogger(UserService.class);
    private static Scanner sc = new Scanner(System.in);

    private static HashMap<Integer, User> userList = getUserList();
    private static List<Account> accountList = new ArrayList<>();


    public boolean approve(){
        System.out.println("Admin/Clerk approval: Please enter a Admin/Clerk User id");

        User authorized = userList.get(Integer.parseInt(sc.nextLine()));

        while(authorized==null){
            System.out.println("Userid not found, please try again. ");
            authorized = userList.get(Integer.parseInt(sc.nextLine()));
        }
        while(authorized.getLevel()<1){
            System.out.println("Customers cannot approve applications. Please try again. ");
            authorized = userList.get(Integer.parseInt(sc.nextLine()));
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

    public User getUser() {
        System.out.println("Welcome to user selection: Press 1 to select existing account, Press 2 to sign up;");
        String input = sc.nextLine();

//        HashMap<Integer, User> userList = getUserList();
//        List<User> userList = userService.getUserList();

        User user;

        switch(input){
            case "1" : {
                    try{
                        System.out.println("Please enter user id");
                        int id = Integer.parseInt(sc.nextLine());
                        //do this to pop /n after nextInt();
                        user = userDao.findUserById(id);
                        while(user==null){
                            System.out.println("user not found, please try again. ");
                            id = Integer.parseInt(sc.nextLine());
                            user = userDao.findUserById(id);
                        }
                        System.out.println("account found, please login");
//                    id = sc.nextLine();
//                    if(id.equals("-1")) break;
//                        user = userList.get(id);
                        return user;
                    }catch(NullPointerException e){
                        log.warn("user exists but failed to get from List. " + e.getMessage() );
                        System.out.println("failed to retrieve account, please try again. ");
                        return getUser();
                    }

            } case "2" :{
                user = newUserBuilder();
                return user;
            }
        }
        System.out.println("invalid choice, please try again. ");
        return getUser();
    }
    
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

    public User newUserBuilder() {
        User user;

        System.out.println("Please enter your unique username");
        String username = sc.nextLine();

        while(userList.containsKey(username)){
            System.out.println("username entered already exists, please try again. ");
            username = sc.nextLine();
        }

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

        System.out.println("Please enter your keyword for retrieving password");
        String keyword = sc.nextLine();

        System.out.println("Please enter your accessLevel. 0 for customer, 1 for clerk, 2 for admin. ");
        int accessLevel = Integer.parseInt(sc.nextLine());
//        sc.nextLine();                                f

        while((accessLevel>2)||accessLevel < 0){
            System.out.println("invalid input, please try again");
            accessLevel = Integer.parseInt(sc.nextLine());
//            sc.nextLine();
        }



        user = new User(accessLevel, username, pwd, keyword);
        userDao.addUser(user);
        user = userDao.findUserByUsername(username);
        userList.put(user.getId(), user);
        System.out.println("Account created! Welcome aboard " + user.getUsername() + "! Please proceed to login. ");
        return user;
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

    public void changePwd(User user){
//        System.out.println("Which user id would you like to change password?");
//        int userid = user.getId();
//        User account4Change = userList.get(userid);

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
        user.setPwd(pwd);

        userController.userInfo(user);
        userDao.updateUser(user);
        System.out.println("pwd change successful!");
    }


    public void deleteAccount(){
        //TODO: rewrite deleteDAOs SQL
        boolean bool = approve();
        if(bool) {
            System.out.println("Which account id do you want to delete?");
            String id = sc.nextLine();
            userList.remove(id);
        }else{
            System.out.println("failed to delete account");
        }
    }

    public void transfer(Account acc, int receivingAccountId, double amount) {
        accountService.withdraw(acc, amount);
        accountService.deposit(accountList.get(receivingAccountId), amount);
    }

//    private static UserDAO playerDAO = new UserDAO();

//    public User createNewUser(String id, String role, int balance){
//        User user = new User(id, role, balance);
//        return user;
//    }

}
