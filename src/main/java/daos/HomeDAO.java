package daos;

import java.util.List;

import models.Home;

public interface HomeDAO {
	
	public List<Home> findAll();
	public Home findByName(String name);
	public boolean updateHome(Home home);
	public boolean addHome(Home home);
	public boolean deleteHome(String homeName);


}
