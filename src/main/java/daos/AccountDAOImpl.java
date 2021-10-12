package daos;

import models.Account;
import models.Home;
import models.User;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.*;


public class AccountDAOImpl implements AccountDAO{
    private HomeDAO homeDao = new HomeDAOImpl();
    private Scanner sc = new Scanner(System.in);
    @Override
    public List<Account> findAll() {

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM accounts;";
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(sql);
            List<Account> accountList = new ArrayList<>();

            while(result.next()){
                Account acc = new Account(0);
                acc.setAccountId(result.getInt("accountid"));
                acc.setBalance(result.getDouble("balance"));
                accountList.add(acc);
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
            statement.setString(1, accountId + "");
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
            String sql = "UPDATE account SET balance = ? WHERE accountid = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, account.getBalance());
            statement.setInt(2, account.getAccountId());
            statement.executeQuery();

            return true;

        }   catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean addAccount(Account account) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO accounts(balance) VALUE ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, account.getBalance());
            statement.executeQuery();

            return true;

        }   catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean addUserToAccount(Account account, int addedUserId) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = " INSERT INTO map_users_accounts (userid, accountid)\n" +
                    " \tVALUES (?, ?);";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, addedUserId + "");
            statement.setString(2, account.getAccountId() + "");

            return true;
        }   catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Account> findAllByUser(User user) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM accounts a \n" +
                    "JOIN map_users_accounts m ON a.accountid = m.accountid\n" +
                    "JOIN users u \n" +
                    "ON u.userid = m.userid\n" +
                    "WHERE u.userid =?;\n";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ""+user.getId());
            ResultSet result = statement.executeQuery();
            List<Account> accountList = new ArrayList<>();

            while(result.next()){
                Account acc = new Account(0);
                acc.setAccountId(result.getInt("accountid"));
                acc.setBalance(result.getDouble("balance"));
                accountList.add(acc);
            }
            return accountList;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAllUsersOfAccount(Account account) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM users u \n" +
                    "JOIN map_users_accounts m ON u.userid = m.userid\n" +
                    "JOIN accounts a \n" +
                    "ON a.accountid = m.accountid\n" +
                    "WHERE a.accountid = ?;\n";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, account.getAccountId()+"");
            ResultSet result = statement.executeQuery();
            List<User> userList = new ArrayList<>();

            while(result.next()){
                User user = new User();
                user.setId(result.getInt("userid"));
                user.setUsername(result.getString("username"));
                user.setPwd(result.getString("pwd"));
                user.setKeyword(result.getString("keyword"));
                user.setLevel(result.getInt("user_level"));
                userList.add(user);
                String homeName = result.getString("home_name");
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
}
