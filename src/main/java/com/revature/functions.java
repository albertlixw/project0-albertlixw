package com.revature;

import java.util.*;

public class functions {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        login();
    }

    public static void login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("what is your username?");

        String username = sc.nextLine();
        if(username.equals("albert")){
            System.out.println("please enter the pwd");
            String pwd = sc.nextLine();
            if(pwd.equals("pwd")){
                System.out.println("login successful!");
            }
        }else{
            System.out.println("Invalid username");
        }

    }

    public void purchase(){

    }
    public void order(){

    }
    public void stock(){

    }
}
