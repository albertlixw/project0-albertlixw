package daos;

import models.Home;
import models.User;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class UserDAOImpl implements UserDAO{

    private HomeDAO homeDao = new HomeDAOImpl();

    @Override
    public HashMap<Integer, User> findAll() {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Users;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            HashMap<Integer, User> userList = new HashMap<Integer, User>();
            //ResultSets have a cursor like Scanner

            while(result.next()){
                User user = new User(
                        result.getInt("user_level"),
                        result.getString("username"),
                        result.getString("pwd"),
                        result.getString("keyword")
                );
                int userId = result.getInt("userId");
                user.setId(userId);
                String homeName = result.getString("home");
                if(homeName!=null){
                    Home home = homeDao.findByName(homeName);
                    user.setHome(home);

                }
                userList.put(user.getId(), user);
            }
            return userList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    @Override
    public User findUserByUsername(String username) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * from users WHERE username = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            User user = new User();

            if(result.next()){
                user = new User(
                        result.getInt("user_level"),
                        result.getString("username"),
                        result.getString("pwd"),
                        result.getString("keyword")
                );
                int userId = result.getInt("userId");
                user.setId(userId);
                String homeName = result.getString("home");
                if(homeName!=null){
                    Home home = homeDao.findByName(homeName);
                    user.setHome(home);
                }
            }
        return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    @Override
    public User findUserById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Users WHERE userid = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            HashMap<Integer, User> userList = new HashMap<Integer, User>();
            //ResultSets have a cursor like Scanner
            User user = new User();
            if(result.next()){
                user = new User(
                        result.getInt("user_level"),
                        result.getString("username"),
                        result.getString("pwd"),
                        result.getString("keyword")
                );
                int userId = result.getInt("userId");
                user.setId(userId);
                String homeName = result.getString("home");
                if(homeName!=null){
                    Home home = homeDao.findByName(homeName);
                    user.setHome(home);

                }
            }
            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO users(user_level, username, pwd, keyword) VALUES (?, ?, ?, ?);";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, user.getLevel());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPwd());
            statement.setString(4, user.getKeyword());

//            if(user.getHome()!=null){
//                statement.setString(5, user.getHome().getName());
//            }
            statement.execute();

            return true;

        }   catch(SQLException e){
            e.printStackTrace();
        }

        return false;    }

    @Override
    public boolean updateUser(User user) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE users SET user_level = ?, username = ?, pwd = ?, keyword = ? WHERE userId = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, user.getLevel());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPwd());
            statement.setString(4, user.getKeyword());
            statement.setInt(5, user.getId());
            statement.execute();
            return true;

        }   catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }


}
