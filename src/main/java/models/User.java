package models;

public class User {
    private String id;//default 0
    private String role; //customer, clerk, admin
    private int money;//admin have no money
    private String pwd;

    public User(String id, String role, int money, String pwd){
        this.id = id;
        this.role = role;
        this.money = money;
        this.pwd = pwd;
    }

    public User(){
        this.id = "abc";
        this.role = "customer";
        this.money = 0;
        this.pwd = "";
    }

    public void purchase(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPwd() {return pwd;}

    public void setPwd(String pwd) {this.pwd = pwd;}
}
