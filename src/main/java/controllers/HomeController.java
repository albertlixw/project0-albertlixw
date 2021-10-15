package controllers;

import java.util.List;
import java.util.Scanner;

import models.Home;
import models.User;
import services.HomeService;

public class HomeController {
	
	private HomeService homeService = new HomeService();
	private Scanner scan = new Scanner(System.in);
	
	public void displayAllHomes() {
		System.out.println("These are your homes:");
		List<Home> list = homeService.findAllHomes();
		for(Home home:list) {
			System.out.println(home);
		}
	}

	public void displayOneHome(String name) {
		System.out.println("Here is home "+name+":");
		Home home = homeService.findByName(name);
		System.out.println(home);
	}
	
	public void updateHome(){

		System.out.println("Please enter the name of the home you want to update");
		Home home = homeService.findByName(scan.nextLine());
		System.out.println(home.toString());
		System.out.println("Welcome to the home builder menu. What will be your home's name?");
		String name = scan.nextLine();
		System.out.println("What is the home's street number?");
		String number = scan.nextLine();
		System.out.println("What is the home's street name?");
		String stName = scan.nextLine();
		System.out.println("What is the home's city?");
		String city = scan.nextLine();
		System.out.println("What is the home's region?");
		String region = scan.nextLine();
		System.out.println("What is the home's zip code?");
		String zip = scan.nextLine();
		System.out.println("What is the home's country?");
		String country = scan.nextLine();

		home = new Home(name, number, stName, city, region, zip, country);

		homeService.updateHome(home);
	}
	public void addHomeToUser(User user){
		System.out.println("Which home do you wanna give user id: "+ user.getId() + "?");
		String homeName = scan.nextLine();
		Home home = homeService.findByName(homeName);
		user.setHome(home);
		System.out.println("User "+ user.getId() + " now has " + home + " as home! ");
	}
	public void addHome() {
		System.out.println("Welcome to the home builder menu. What will be your home's name?");
		String name = scan.nextLine();
		System.out.println("What is the home's street number?");
		String number = scan.nextLine();
		System.out.println("What is the home's street name?");
		String stName = scan.nextLine();
		System.out.println("What is the home's city?");
		String city = scan.nextLine();
		System.out.println("What is the home's region?");
		String region = scan.nextLine();
		System.out.println("What is the home's zip code?");
		String zip = scan.nextLine();
		System.out.println("What is the home's country?");
		String country = scan.nextLine();
		
		Home home = new Home(name, number, stName, city, region, zip, country);
		
		if(homeService.newHome(home)) {
			System.out.println("Your home was successfully created");
		}else {
			System.out.println("Something went wrong. We could not register your home. Please try again.");
		}
	}

	public void deleteHome() {
		System.out.println("Which homeName to delete?");
		String homeName = scan.nextLine();
		homeService.deleteHome(homeName);
	}
}
