package com.revature;

import controllers.MenuController;

import models.*;
import services.UserService;

import java.util.*;


import java.util.Scanner;

public class Driver {

    private static MenuController menuController = new MenuController();

    public static void main(String[] args) {
//        User user = menuController.getUser();
//        Scanner scan = new Scanner(System.in);
//        MenuController menuController = new MenuController();
//
//        UserService userService = new UserService();
//        System.out.println(userService.approve());
//
        System.out.println("Controller booting. ");
        menuController.userUI();
    }


}
