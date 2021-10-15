package services;

import models.Home;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HomeServiceTest {
    static HomeService homeService = new HomeService();

    @Test
    void newHome() {
        Home home = new Home("homeName", "number", "street", "city", "region", "zip", "country");
        homeService.newHome(home);
        List<Home> homeList = homeService.findAllHomes();
        assertEquals(homeList.size(), 3);
    }

    @Test
    void findAllHomes() {
         List<Home> homeList = homeService.findAllHomes();
         assertEquals(homeList.size(), 3);
    }



    @Test
    void findByName() {
        Home home = homeService.findByName("homeName");
        assertEquals(home.getStreetNumber(), "number");
    }

    @Test
    void updateHome() {
        Home home = new Home("homeName", "number", "home street", "home city", "home region", "home zip", "Space");

        homeService.updateHome(home);
        home = homeService.findByName("homeName");

        assertEquals(home.getCountry(), "Space");
    }


//    @AfterAll
//    static void deleteHome() {
//        homeService.deleteHome("homeName");
//        assertNull(homeService.findByName("homeName"));
//    }
//
//    @BeforeAll
//    static void beforeAll() {
//
//    }


}