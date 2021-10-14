package controllers;

import daos.UserDAO;
import daos.UserDAOImpl;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.UserService;

//import java.util.HashMap;
import java.util.*;

public class UserController {

    private static UserService userService = new UserService();
    private UserDAO userDao = new UserDAOImpl();

    private Scanner sc = new Scanner(System.in);
    private static HashMap<Integer, User> userList = userService.getUserList();

    private static Logger log = LoggerFactory.getLogger(UserService.class);

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

        userInfo(user);
        userDao.updateUser(user);
        System.out.println("pwd change successful!");
    }




    public void deleteUser(){
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

    public void userInfo(User user){
        System.out.println(user.toString());
    }
}
