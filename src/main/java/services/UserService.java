package services;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.User;

public class UserService {
    private static Logger log = LoggerFactory.getLogger(UserService.class);

    public static HashMap<String, User> getUserList() {

        HashMap <String, User> userList = new HashMap<>();
        User user = new User("1", "customer", 100, "123");

        userList.put("1", user);
//        ArrayList<User> userList = userDAO.findAllUsers();
//        for(int i = 0; i < userList; i++){
//            if(userList.get(i).indebt){
//                userList.remove(i);
//            }
//        }
        return userList;
    }
//    private static UserDAO playerDAO = new UserDAO();

//    public User createNewUser(String id, String role, int balance){
//        User user = new User(id, role, balance);
//        return user;
//    }

}
