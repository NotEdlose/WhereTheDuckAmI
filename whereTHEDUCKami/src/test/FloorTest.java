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
public class FloorTest {
    
    public FloorTest() {
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
     * Test of setFloorNumber method, of class Floor.
     */
    @Test
    public void testSetFloorNumber() {
        System.out.println("setFloorNumber");
        int floorNum = 1;
        Floor instance = new Floor(1,"floor 1");
        instance.setFloorNumber(floorNum);
      
    }

    /**
     * Test of getFloorNumber method, of class Floor.
     */
    @Test
    public void testGetFloorNumber() {
        System.out.println("getFloorNumber");
        Floor instance = new Floor(1,"floor 1");
        int expResult = 1;
        int result = instance.getFloorNumber();
        assertEquals(expResult, result);
     
    }

    /**
     * Test of updateFloorNum method, of class Floor.
     */
    @Test
    public void testUpdateFloorNum() {
        System.out.println("updateFloorNum");
        int floorNum = 0;
        Floor instance = new Floor(1,"floor 1");
        instance.updateFloorNum(floorNum);
        
    }

    /**
     * Test of setdescrip method, of class Floor.
     */
    @Test
    public void testSetdescrip() {
        System.out.println("setdescrip");
        String descrip = "fllor 1";
        Floor instance = new Floor(1,"floor 1");
        instance.setdescrip(descrip);
        
    }

    /**
     * Test of updateDescrip method, of class Floor.
     */
    @Test
    public void testUpdateDescrip() {
        System.out.println("updateDescrip");
        String descrip = "floor 0";
        Floor instance = new Floor(1,"floor 1");
        instance.updateDescrip(descrip);
       
    }

    /**
     * Test of getDescrip method, of class Floor.
     */
    @Test
    public void testGetDescrip() {
        System.out.println("getDescrip");
        Floor instance = new Floor(1,"floor 1");
        String expResult = "floor 1";
        String result = instance.getDescrip();
        assertEquals(expResult, result);
    
    }

    /**
     * Test of setNumLayers method, of class Floor.
     */
    @Test
    public void testSetNumLayers() {
        System.out.println("setNumLayers");
        int n = 0;
        Floor instance = new Floor(1,"floor 1");
        instance.setNumLayers(n);
       
    }

    /**
     * Test of getNumLayers method, of class Floor.
     */
    @Test
    public void testGetNumLayers() {
        System.out.println("getNumLayers");
        Floor instance = new Floor(1,"floor 1");
        int expResult = 0;
        int result = instance.getNumLayers();
        assertEquals(expResult, result);
     
    }

    /**
     * Test of addLayer method, of class Floor.
     */
    @Test
    public void testAddLayer() {
        System.out.println("addLayer");
        Layer layer = new Layer("testLayer");
        Floor instance = new Floor(1,"floor 1");
        instance.addLayer(layer);
        
    }

    /**
     * Test of getLayer method, of class Floor.
     */
    @Test
    public void testGetLayer() {
        System.out.println("getLayer");
        int i = 0;
        Floor instance = new Floor(1,"floor 1");
        Layer expResult = new Layer("testLayer");
        instance.addLayer(expResult);
        Layer result = instance.getLayer(i);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of deleteLayer method, of class Floor.
     */
    @Test
    public void testDeleteLayer() {
        System.out.println("deleteLayer");
        int i = 0;
        Floor instance = new Floor(1,"floor 1");
        instance.deleteLayer(i);
        
    }

    /**
     * Test of setFileName method, of class Floor.
     */
    @Test
    public void testSetFileName() {
        System.out.println("setFileName");
        String name = "fileA.json";
        Floor instance = new Floor(1,"floor 1");
        instance.setFileName(name);
      
    }

    /**
     * Test of getFileName method, of class Floor.
     */
    @Test
    public void testGetFileName() {
        System.out.println("getFileName");
        Floor instance = new Floor(1,"floor 1");
        instance.setFileName("fileA.json");
        String result = instance.getFileName();
        assertEquals("fileA.json", result);
        
    }
    
}
