package com.revature;

import controllers.MenuController;

import models.*;
import java.util.*;


import java.util.Scanner;

public class Driver {


    public static void main(String[] args) {
        MenuController menuController = new MenuController();
        User user = menuController.getUser();
//        Scanner scan = new Scanner(System.in);

        menuController.userUI(user);
    }


}
