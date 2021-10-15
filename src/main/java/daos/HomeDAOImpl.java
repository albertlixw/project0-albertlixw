package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controllers.MenuController;
import models.Home;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConnectionUtil;
import utils.ConnectionUtil;

public class HomeDAOImpl implements HomeDAO{

	private static Logger log = LoggerFactory.getLogger(MenuController.class);

	@Override
	public boolean updateHome(Home home) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE homes SET (home_name, home_number, home_street, home_city, home_region, home_zip, home_country) = (?,?,?,?,?,?,?) WHERE home_name = ?;";

			int count = 0;
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(++count, home.getName());
			statement.setString(++count, home.getStreetNumber());
			statement.setString(++count, home.getStreetName());
			statement.setString(++count, home.getCity());
			statement.setString(++count, home.getRegion());
			statement.setString(++count, home.getZip());
			statement.setString(++count, home.getCountry());
			//where home_name =
			statement.setString(++count, home.getName());
			statement.execute();

			return true;

		}   catch(SQLException e){
			e.printStackTrace();
		}

		return false;
	}
	
	@Override
	public List<Home> findAll() {
		try(Connection conn = ConnectionUtil.getConnection()){ //try-with-resources 
			String sql = "SELECT * FROM homes;";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Home> list = new ArrayList<>();
			
			//ResultSets have a cursor (similar to Scanner or other I/O classes) that can be used 
			//with a while loop to iterate through all the data. 
			
			while(result.next()) {
				Home home = new Home();
				home.setName(result.getString("home_name"));
				home.setStreetNumber(result.getString("home_number"));
				home.setStreetName(result.getString("home_street"));
				home.setCity(result.getString("home_city"));
				home.setRegion(result.getString("home_region"));
				home.setZip(result.getString("home_zip"));
				home.setCountry(result.getString("home_country"));
//				home.setResidents(result.getInt("residents"));
				list.add(home);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Home findByName(String name) {
		try(Connection conn = ConnectionUtil.getConnection()){ //try-with-resources 
			String sql = "SELECT * FROM homes WHERE home_name = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, name);
			
			ResultSet result = statement.executeQuery();
			
			Home home = new Home();
			
			//ResultSets have a cursor (similar to Scanner or other I/O classes) that can be used 
			//with a while loop to iterate through all the data. 
			
			if(result.next()) {
				
				home.setName(result.getString("home_name"));
				home.setStreetNumber(result.getString("home_number"));
				home.setStreetName(result.getString("home_street"));
				home.setCity(result.getString("home_city"));
				home.setRegion(result.getString("home_region"));
				home.setZip(result.getString("home_zip"));
				home.setCountry(result.getString("home_country"));
//				home.setResidents(result.getInt("residents"));

			}
			
			return home;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean addHome(Home home) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "INSERT INTO homes (home_name, home_number, home_street, home_city, home_region, home_zip, home_country) "
					+ "VALUES (?,?,?,?,?,?,?);";
			
			int count = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(++count, home.getName());
			statement.setString(++count, home.getStreetNumber());
			statement.setString(++count, home.getStreetName());
			statement.setString(++count, home.getCity());
			statement.setString(++count, home.getRegion());
			statement.setString(++count, home.getZip());
			statement.setString(++count, home.getCountry());
//			statement.setInt(++count, home.getResidents());
			
			statement.execute();
			
			return true;

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteHome(String homeName) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM homes WHERE home_name = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, homeName);
			ps.execute();
			log.info("Deleted home: " + homeName);
			return true;
		} catch (SQLException e) {
			log.warn("failed deleting home: " + homeName);
			log.warn(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

}
