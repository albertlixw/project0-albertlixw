package models;

public class User {
    private String id;//default 0
    private String role; //customer, clerk, admin
    private double money;//admin have no money
    private String pwd;

    private String keyWord;

    //for changing password authorization
    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public User(String id, String role, double money, String pwd){
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPwd() {return pwd;}

    public void setPwd(String pwd) {this.pwd = pwd;}
}
