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
import static org.junit.Assert.*;

/**
 *
 * @author Alaa
 */
public class POITest {
    
    public POITest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setName method, of class POI.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "MC POI";
        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
        instance.setName(name);
   
    }

    /**
     * Test of getName method, of class POI.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
        String expResult = "MC POI";
        String result = instance.getName();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setNum method, of class POI.
     */
    @Test
    public void testSetNum() {
        System.out.println("setNum");
        String num = "132";
        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
        instance.setNum(num);
  
    }

    /**
     * Test of getNum method, of class POI.
     */
    @Test
    public void testGetNum() {
        System.out.println("getNum");
        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
        String expResult = "132";
        String result = instance.getNum();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getHI method, of class POI.
     */
    @Test
    public void testGetHI() {
        System.out.println("getHI");
        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
        boolean expResult = true;
        boolean result = instance.getHI();
        assertEquals(expResult, result);
      
    }

    /**
     * Test of getDescription method, of class POI.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
        String expResult = "POI classroom in MC";
        String result = instance.getDescription();
        assertEquals(expResult, result);
   
    }

    /**
     * Test of setDescription method, of class POI.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String descrip = "POI classroom in MC";
        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
        instance.setDescription(descrip);
     
    }

    /**
     * Test of getType method, of class POI.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
        String expResult = "Classroom";
        String result = instance.getType();
        assertEquals(expResult, result);
 
    }

    /**
     * Test of setType method, of class POI.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        String type = "Classroom";
        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
        instance.setType(type);
  
    }

    /**
     * Test of getLocation method, of class POI.
     */
//    @Test
//    public void testGetLocation() {
//        System.out.println("getLocation");
//        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
//        int[] expResult = {0,0};
//        int[] result = instance.getLocation();
//        assertArrayEquals(expResult, result);
//       
//    }

    /**
     * Test of setLocation method, of class POI.
     */
//    @Test
//    public void testSetLocation() {
//        System.out.println("setLocation");
//        int x = 0;
//        int y = 0;
//        POI instance = new POI("MC POI","132","Classroom",true,"POI classroom in MC",0,0);
//        instance.setLocation(x, y);
//  
//    }
    
}
