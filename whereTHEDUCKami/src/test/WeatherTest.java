/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.cs2212.wheretheduckami;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 *
 * @author nicholascorcoran
 */
public class WeatherTest {
    
     Weather weather;
    
    public WeatherTest() {
    }
    
   
    
    @Before
    public void setUp() {
        this.weather = new Weather("ab4503d6f453b8fe5bcdba6e734b0b30","6058560");
    }
    
   
    /**
     * Test of constructor method, of class Weather.
     */
    @Test
    public void testConstructor() {
         assertEquals("ab4503d6f453b8fe5bcdba6e734b0b30", this.weather.getAPIKey());
         assertEquals("6058560", this.weather.getCity());
    }
    /**
     * Test of getTemperature method, of class Weather.
     */
    @Test
    public void testGetTemperature() {
        System.out.println("getTemperature");
        //this.weather.setTemperature(10);
        assertNotNull(this.weather.getTemperature());
        
    }
    /**
     * Test of main method, of class Weather.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Weather.main(args);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
