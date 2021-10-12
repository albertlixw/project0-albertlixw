package services;

import java.util.List;

//import daos.HomeDAO;
import daos.*;
import models.*;

public class HomeService {
	
	private HomeDAO homeDao = new HomeDAOImpl();
	
	public List<Home> findAllHomes() {
		return homeDao.findAll();
	}

	public Home findByName(String name) {
		return homeDao.findByName(name);
		
	}
	
	public boolean newHome(Home home) {
		return homeDao.addHome(home);
	}
	

}
