/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.cs2212.wheretheduckami;

import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nicholascorcoran
 */
public class UserTest {
    

    private User user;
    private User user2;
    private POI poi;
    
    @Before
    public void setUp() {
        user = new User("testuser","correctPassword","userType");
        //user2 = new User("testuser2", "correctPassword");
        poi = new POI("Test POI", "123", "test");
        
    }
    
    @Test
    public void testConstructor() {
        assertEquals("testuser", user.getUserName());
        assertEquals("correctPassword", user.getPassWord());
        assertEquals(0, user.getNumUserDefinedPOIs());
        assertEquals(0, user.getNumFavoritePOIs());
    }
    
    @Test
    public void testAddFavoritePOI() {
        user.addFavoritePOI(poi);
        List<POI> favoritePOIs = user.getFavoritePOIs();
        assertEquals(1, favoritePOIs.size());
        assertEquals("Test POI", favoritePOIs.get(0).getName());
    }
    
    @Test
    public void testDeleteFavoritePOI() {
        user.addFavoritePOI(poi);
        user.deleteFavoritePOI(poi);
        List<POI> favoritePOIs = user.getFavoritePOIs();
        assertEquals(0, favoritePOIs.size());
    }
    
    @Test
    public void testCreateUserDefinedPOI() {
        user.createUserDefinedPOI("Test POI", "123", "test", "test description");
        List<POI> userDefinedPOIs = user.getUserDefinedPOIs();
        assertEquals(1, userDefinedPOIs.size());
        assertEquals("Test POI", userDefinedPOIs.get(0).getName());
    }
    
    @Test
    public void testRemoveUserDefinedPOI() {
        user.createUserDefinedPOI("Test POI", "123", "test", "test description");
        user.removeUserDefinedPOI("Test POI");
        List<POI> userDefinedPOIs = user.getUserDefinedPOIs();
        assertEquals(0, userDefinedPOIs.size());
    }
    
    @Test
    public void testVerifyLogin() {
        assertFalse(user.verifyLogin("testuser","f"));
//        assertFalse(user.verifyLogin("wronguser", ""));
//        assertFalse(user.verifyLogin("testuser", "wrongpassword"));
//        assertTrue(user2.verifyLogin("testuser2", "correctPassword"));
        
        
    }
}
