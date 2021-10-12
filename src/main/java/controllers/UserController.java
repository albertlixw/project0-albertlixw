package controllers;

import models.User;
import services.UserService;

import java.util.HashMap;

public class UserController {

    private UserService userService = new UserService();

    public void showAllUserId(){
        System.out.println("Here are all the user ids: ");
        HashMap<Integer, User> userList = userService.getUserList();
        for(Integer i : userList.keySet()){
            System.out.println(userList.get(i).toString());
        }
    }

    public void userInfo(User user){
        System.out.println(user.toString());
    }
}
