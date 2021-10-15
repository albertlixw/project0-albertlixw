package models;

public class User {
    private int id;//default 0
    private int level; //customer, clerk, admin
//    private double money;//admin have no money

    private String username;
    private String pwd;

    private String keyword;

    public User(int level, String username, String pwd, String keyword){
        this.level = level;
        this.username = username;
        this.pwd = pwd;
        this.keyword = keyword;
    }

    public User(){
        super();
    }

    @Override
    public String toString() {
        String homeString = "No home info available";
        if(home!=null){
            homeString = home.toString();
        }
        String res = "User{" +
                "id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", keyword='" + keyword + '\'' +
                ", home=" + homeString +
                '}';

        return res;
    }

    public String printIdLevelUsername() {
        String res = "User{" +
                "id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", username='" + username + '\'' +
                '}';

        return res;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private Home home;

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    //for changing password authorization
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }




//    public User(){
//        this.id = "abc";
//        this.level = "customer";
////        this.money = 0;
//        this.pwd = "";
//    }

}
