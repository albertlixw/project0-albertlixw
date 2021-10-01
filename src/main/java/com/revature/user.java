package com.revature;

public class user {
    private int id;//default 0
    private String role; //buyer, seller, admin
    private int money;//admin have no money

    public user(int id, String role, int money){
        this.id = id;
        this.role = role;
        this.money = money;     }

    public user(){
        this.id = 0;
        this.role = "buyer";
        this.money = 0;     }

    public void purchase(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
