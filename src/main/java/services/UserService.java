package services;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.User;

public class UserService {

    private static Logger log = LoggerFactory.getLogger(UserService.class);
    Scanner sc = new Scanner(System.in);

    private static HashMap<String, User> userList = new HashMap<>();

    public static HashMap<String, User> getUserList() {
//        HashMap <String, User> userList = new HashMap<>();
        User user = new User("1", "customer", 100, "123");
        userList.put("1", user);

        User clerk = new User("2", "clerk", 1000, "234");
        userList.put("2", clerk);

        User admin = new User("0", "admin", 100000000, "012");
        userList.put("0", admin);
//        ArrayList<User> userList = userDAO.findAllUsers();
//        for(int i = 0; i < userList; i++){
//            if(userList.get(i).indebt){
//                userList.remove(i);
//            }
//        }
        return userList;
    }

    public User newUserBuilder() {
        System.out.println("Please enter your id");
        String id = sc.nextLine();

        HashMap<String, User> userList = getUserList();
        User user;

        while(userList.containsKey(id)){
            System.out.println("id entered already exists, please try again. ");
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
        while(balance < 0){
            System.out.println("Balance can't be negative. ");
            balance = Integer.parseInt(sc.nextLine());
        }

        String pwd = null, confirmPwd = null;
        do{
            System.out.println("Please enter your password");
            pwd = sc.nextLine();
            System.out.println("Please confirm your password");
            confirmPwd = sc.nextLine();
        }while(pwd==null||confirmPwd==null||!pwd.equals(confirmPwd));

        //clerk approve
        System.out.println("Needs approval from admin/clerk");

        while(!approve()){
            System.out.println("Your application got denied. Please try again, or come another day. ");
        }

        user = new User(id, role, balance, pwd);
        userList.put(id, user);
        System.out.println("Account created! Please proceed to login. ");
        return user;
    }

    public void deposit(User user, double amount){
        while(amount < 0){
            System.out.println("invalid input, please try again. ");
            amount = sc.nextDouble();
        }
        user.setMoney(user.getMoney() + amount);
    }

    public void withdraw(User user, double amount){
        while(amount > user.getMoney() || amount < 0){
            System.out.println("insufficient balance, please try again. ");
            amount = sc.nextDouble();
        }
        user.setMoney(user.getMoney() - amount);
    }

    public void accountInfo(User user){
        System.out.println("Account id: " + user.getId() + "\n" + "Role: " + user.getRole() + "\n" + "Balance: " + user.getMoney());
    }

    //only admin can do this. 
    public void assignRole(User user){
        if(!user.getRole().equals("admin")){
            System.out.println("Unauthorized action.");
            return;
        }
        System.out.println("Which accountId would you like to modify?");
        String modifiedAccountId = sc.nextLine();
        User modifiedAccount = userList.get(modifiedAccountId);
        accountInfo(modifiedAccount);
        System.out.println("Which role would you like to give account " + modifiedAccount.getId() + "?");
        modifiedAccount.setRole(sc.nextLine());
        accountInfo(modifiedAccount);
    }

    //clerk and admin can use this. 
    public void getAnyUserInfo(User user){
        if(user.getRole().equals("customer")){
            System.out.println("Unauthorized action.");
            return;
        }
        System.out.println("Which account id would you like to check info?");
        String checkAccountId = sc.nextLine();
        accountInfo(userList.get(checkAccountId));
        System.out.println("Account Keyword: " + userList.get(checkAccountId).getKeyWord());
    }

    public void changePwd(User user){
        System.out.println("Which account id would you like to change password?");
        String checkAccountId = sc.nextLine();
        User account4Change = userList.get(checkAccountId);

        String pwd = null, confirmPwd = null;
        do{
            System.out.println("Please enter new password");
            pwd = sc.nextLine();
            System.out.println("Please confirm new password");
            confirmPwd = sc.nextLine();
        }while(pwd==null||confirmPwd==null||!pwd.equals(confirmPwd));
        account4Change.setPwd(pwd);
        accountInfo(account4Change);
        System.out.println("pwd change successful!");
    }

    public boolean approve(){
        System.out.println("Admin/Clerk approval: Please enter a Admin/Clerk account id");

        User authorized = userList.get(sc.nextLine());
        while(authorized.getRole().equals("customer")){
            System.out.println("Customers cannot approve applications. Please try again. ");
            authorized = userList.get(sc.nextLine());
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

    public void deleteAccount(){
        boolean bool = approve();
        if(bool) {
            System.out.println("Which account id do you want to delete?");
            String id = sc.nextLine();
            userList.remove(id);
        }else{
            System.out.println("failed to delete account");
        }
    }

    public void transfer(User user, String accountId, double amount) {
        withdraw(user, amount);
        deposit(userList.get(accountId), amount);
    }

//    private static UserDAO playerDAO = new UserDAO();

//    public User createNewUser(String id, String role, int balance){
//        User user = new User(id, role, balance);
//        return user;
//    }

}
