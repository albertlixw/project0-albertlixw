package controllers;

import daos.UserDAO;
import daos.UserDAOImpl;
import models.Account;
import models.User;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.AccountService;
import services.UserService;

//import java.util.HashMap;
import java.sql.SQLException;
import java.util.*;

public class UserController {

    private static UserService userService = new UserService();
    private static MenuController menuController = new MenuController();
    private static AccountService accountService = new AccountService();
    private UserDAO userDao = new UserDAOImpl();

    private Scanner sc = new Scanner(System.in);
//    private static HashMap<Integer, User> userList = userService.getUserList();

    private static Logger log = LoggerFactory.getLogger(UserService.class);


    public void changePwdClerk(User user){
        System.out.println("Which user id would you like to change password?");
        int userid = Integer.parseInt(sc.nextLine());
        User account4Change = userService.userList.get(userid);
        if(user.getLevel()>0){
            changePwd(account4Change);
        }
    }
    public void changePwd(User user){
//        System.out.println("Which user id would you like to change password?");
//        int userid = user.getId();
//        User account4Change = userList.get(userid);
        System.out.println("Welcome to password change UI, Please enter keyword to proceed: ");
        String input = sc.nextLine();

        if(input.equals(user.getKeyword())){
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

            userInfo(user);
            userDao.updateUser(user);
            System.out.println("pwd change successful!");
        }else{
            System.out.println("Keyword doesn't match with our records. Please try again. ");
        }
    }

    //clerk and admin can use this.
    public void getAnyUserInfo(User user){
        if(user.getLevel() < 1){
            System.out.println("Unauthorized action.");
            return;
        }
        menuController.printAllUsers(userService.userList);
        System.out.println("Which user id would you like to check info?");
        int checkUserId = Integer.parseInt(sc.nextLine());
        User selectedUser = userService.userList.get(checkUserId);
        userInfo(selectedUser);
        HashMap<Integer, Account> accountsOfUser = accountService.findAllByUser(selectedUser);
        menuController.printAllAccounts(accountsOfUser);
    }


    public void deleteUser(int id){
        boolean bool = approve();
        if(bool) {
            System.out.println("Which account id do you want to delete?");
            id = Integer.parseInt(sc.nextLine());
            userService.deleteUser(id);
            userService.userList.remove(id);
        }else{
            System.out.println("failed to delete account");
        }
    }

    public void showAllUserId(){
        System.out.println("Here are all the user ids: ");
        HashMap<Integer, User> userList = userService.getUserList();
        for(Integer i : userList.keySet()){
            System.out.println(userList.get(i).toString());
        }
    }

    public User getUser() {
        System.out.println("Welcome to user selection: Press 1 to select existing user, Press 2 to sign up;");
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
                try{
                    user = newUserBuilder();
                    return user;
                }catch(SQLException e){
                    e.printStackTrace();
                }return getUser();
            }
        }
        System.out.println("invalid choice, please try again. ");
        return getUser();
    }
    public User newUserBuilder() throws SQLException {


        User user;

        System.out.println("Please enter your unique username");
        String username = sc.nextLine();

        while(userService.userList.containsKey(username)){
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
        userService.userList.put(user.getId(), user);
        System.out.println("New user created! Welcome aboard " + user.getUsername() + "! Please proceed to login. ");
        log.info("new user made: " + user.toString());

        return user;
    }

    public boolean approve(){
        System.out.println("Admin/Clerk approval: Please enter a Admin/Clerk User id");

        User authorized = userService.userList.get(Integer.parseInt(sc.nextLine()));

        while(authorized==null){
            System.out.println("Userid not found, please try again. ");
            authorized = userService.userList.get(Integer.parseInt(sc.nextLine()));
        }
        while(authorized.getLevel()<1){
            System.out.println("Customers cannot approve applications. Please try again. ");
            authorized = userService.userList.get(Integer.parseInt(sc.nextLine()));
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
        log.info("User id: " + authorized.getId() + "just approved an action");
        return approve();
    }

    public void userInfo(User user){
        System.out.println(user.toString());
    }
}
