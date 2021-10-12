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
    private UserDAO userDao = new UserDAOImpl();
    AccountService accountService = new AccountService();
    UserController userController = new UserController();
    private static Logger log = LoggerFactory.getLogger(UserService.class);
    Scanner sc = new Scanner(System.in);

    private HashMap<Integer, User> userList = getUserList();
    private List<Account> accountList = new ArrayList<>();


    public HashMap<Integer, User> getUserList() {
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
        
        return userDao.findAll();
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
                        int id = sc.nextInt();
                        user = userDao.findUser(id);
                        while(user==null){
                            System.out.println("user not found, please try again. ");
                            id = sc.nextInt();
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

    public User newUserBuilder() {
        User user;

        System.out.println("Please enter your unique username");
        String username = sc.nextLine();

        while(userList.containsKey(username)){
            System.out.println("username entered already exists, please try again. ");
            username = sc.nextLine();
        }

        String pwd = null, confirmPwd = null;
        do{
            System.out.println("Please enter your password");
            pwd = sc.nextLine();
            System.out.println("Please confirm your password");
            confirmPwd = sc.nextLine();
        }while(pwd==null||confirmPwd==null||!pwd.equals(confirmPwd));

        System.out.println("Please enter your keyword for retrieving password");
        String keyword = sc.nextLine();

        System.out.println("Please enter your accessLevel. 0 for customer, 1 for clerk, 2 for admin. ");
        int accessLevel = sc.nextInt();

        while((accessLevel>2)||accessLevel < 0){
            System.out.println("invalid input, please try again");
            accessLevel = sc.nextInt();
        }



        user = new User(accessLevel, username, pwd, keyword);
        userList.put(user.getId(), user);
        System.out.println("Account created! Please proceed to login. ");
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
        modifiedAccount.setLevel(sc.nextInt());
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

        String pwd = "", confirmPwd = "";
        do{
            System.out.println("Please enter new password");
            pwd = sc.nextLine();
            System.out.println("Please confirm new password");
            confirmPwd = sc.nextLine();
        }while(!pwd.equals(confirmPwd));
        account4Change.setPwd(pwd);

        userController.userInfo(account4Change);
        userDao.updateUser(account4Change);
        System.out.println("pwd change successful!");
    }

    public void changePwd(User user){
//        System.out.println("Which user id would you like to change password?");
//        int userid = user.getId();
//        User account4Change = userList.get(userid);

        String pwd = "123", confirmPwd = "";
        while(!pwd.equals(confirmPwd)){
            System.out.println("Please enter new password");
            pwd = sc.nextLine();
            System.out.println("Please confirm new password");
            confirmPwd = sc.nextLine();
        };
        user.setPwd(pwd);

        userController.userInfo(user);
        userDao.updateUser(user);
        System.out.println("pwd change successful!");
    }


    public void deleteAccount(){
        //TODO: rewrite deleteDAOs SQL
        boolean bool = accountService.approve();
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
