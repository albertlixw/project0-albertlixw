package daos;

import models.Account;
import models.Home;
import models.User;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.*;


public class AccountDAOImpl implements AccountDAO{
    private final HomeDAO homeDao = new HomeDAOImpl();
//    private Scanner sc = new Scanner(System.in);
    @Override
    public HashMap<Integer, Account> findAll() {

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM accounts;";
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(sql);
            HashMap<Integer, Account> accountList = new HashMap<>();

            while(result.next()){
                Account acc = new Account(0);
                acc.setAccountId(result.getInt("accountid"));
                acc.setBalance(result.getDouble("balance"));
                accountList.put(acc.getAccountId(), acc);
            }
            return accountList;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Account findById(int accountId) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM accounts WHERE accountid = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet result = statement.executeQuery();
            Account acc = new Account(0);

            if(result.next()){
                acc.setBalance(result.getDouble("balance"));
            }

            return acc;

        }   catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateAccount(Account account) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE accounts SET balance = ? WHERE accountid = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, account.getBalance());
            statement.setInt(2, account.getAccountId());
            statement.execute();

            return true;

        }   catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean addAccount(Account account) {
        try(Connection conn = ConnectionUtil.getConnection()){
            System.out.println(account.getBalance());
            String sql = "INSERT INTO accounts(balance) VALUES (?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, account.getBalance());
            statement.execute();

            return true;

        }   catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean addUserToAccount(int accountid, int addedUserId) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO map_users_accounts (userid, accountid) VALUES (?, ?);";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, addedUserId);
            statement.setInt(2, accountid);
            statement.execute();

            return true;
        }   catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public HashMap<Integer, Account> findAllByUser(User user) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM accounts a \n" +
                    "JOIN map_users_accounts m ON a.accountid = m.accountid\n" +
                    "JOIN users u \n" +
                    "ON u.userid = m.userid\n" +
                    "WHERE u.userid = ?;\n";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();
            HashMap<Integer, Account> accountList = new HashMap<>();

            while(result.next()){
                Account acc = new Account(0);
                acc.setAccountId(result.getInt("accountid"));
                acc.setBalance(result.getDouble("balance"));
                accountList.put(acc.getAccountId(), acc);
            }        
            return accountList;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<Integer, User> findAllUsersOfAccount(Account account) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM users u \n" +
                    "JOIN map_users_accounts m ON u.userid = m.userid\n" +
                    "JOIN accounts a \n" +
                    "ON a.accountid = m.accountid\n" +
                    "WHERE a.accountid = ?;\n";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, account.getAccountId());
            ResultSet result = statement.executeQuery();
            HashMap<Integer, User> userList = new HashMap<>();

            while(result.next()){
                User user = new User();
                user.setId(result.getInt("userid"));
                user.setUsername(result.getString("username"));
                user.setPwd(result.getString("pwd"));
                user.setKeyword(result.getString("keyword"));
                user.setLevel(result.getInt("user_level"));
                userList.put(user.getId(), user);
                String homeName = result.getString("home");
                if(homeName!=null){
                    Home home = homeDao.findByName(homeName);
                    user.setHome(home);
                }
            }
            return userList;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteAccount(int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "DELETE FROM accounts WHERE accountid = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
