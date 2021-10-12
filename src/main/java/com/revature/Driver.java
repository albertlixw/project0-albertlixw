package com.revature;

import controllers.MenuController;

import models.*;
import java.util.*;


import java.util.Scanner;

public class Driver {
    private static MenuController menuController = new MenuController();

    public static void main(String[] args) {
//        User user = menuController.getUser();
//        Scanner scan = new Scanner(System.in);

        menuController.userUI();
    }


}
