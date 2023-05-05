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
 * @author Alaa
 */
public class BuildingTest {
    Building building;
    
    public BuildingTest() {
        
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
     * Test of setBuildingName method, of class Building.
     */
    @Test
    public void testSetBuildingName() {
        System.out.println("setBuildingName");
        String name = "Middlesex";
        Building building = new Building("Middlesex","building");
        building.setBuildingName(name);
      
    }

    /**
     * Test of getBuildingName method, of class Building.
     */
    @Test
    public void testGetBuildingName() {
        System.out.println("getBuildingName");
        Building building = new Building("Middlesex","building");
        String expResult = "Middlesex";
        String result = building.getBuildingName();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setBuildingAcronym method, of class Building.
     */
    @Test
    public void testSetBuildingAcronym() {
        System.out.println("setBuildingAcronym");
        String a = "building";
        Building building = new Building("Middlesex","building");
        building.setBuildingAcronym(a);
       
    }

    /**
     * Test of getBuildingAcronym method, of class Building.
     */
    @Test
    public void testGetBuildingAcronym() {
        System.out.println("getBuildingAcronym");
        Building building = new Building("Middlesex","building");
        String expResult = "building";
        String result = building.getBuildingAcronym();
        assertEquals(expResult, result);
      
    }

    /**
     * Test of setLocation method, of class Building.
     */
    @Test
    public void testSetLocation() {
        System.out.println("setLocation");
        int x = 0;
        int y = 0;
        Building building = new Building("Middlesex","building");
        building.setLocation(x, y);
        
    }

    /**
     * Test of updateBuildingLoca method, of class Building.
     */
    @Test
    public void testUpdateBuildingLoca() {
        System.out.println("updateBuildingLoca");
        Building building = new Building("Middlesex","building");
        int[] expResult = {0,0};
        int[] result = building.updateBuildingLoca();
        assertArrayEquals(expResult, result);
        
    }

    /**
     * Test of setDes method, of class Building.
     */
    @Test
    public void testSetDes() {
        System.out.println("setDes");
        String description = "building in UWO";
        Building building = new Building("Middlesex","building");
        building.setDes(description);
        
    }

    /**
     * Test of getDes method, of class Building.
     */
    @Test
    public void testGetDes() {
        System.out.println("getDes");
        Building building = new Building("Middlesex","building");
        String expResult = building.getDes();
        String result = building.getDes();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getNumFloors method, of class Building.
     */
    @Test
    public void testGetNumFloors() {
        System.out.println("getNumFloors");
        Building building = new Building("Middlesex","building");
        int expResult = 0;
        int result = building.getNumFloors();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getFloors method, of class Building.
     */
    @Test
    public void testGetFloors() {
        System.out.println("getFloors");
        Building building = new Building("Middlesex","building");
        List<Floor> expResult = building.getFloors();
        List<Floor> result = building.getFloors();
        assertEquals(expResult, result);
      
    }

    /**
     * Test of addFloor method, of class Building.
     */
    @Test
    public void testAddFloor() {
        System.out.println("addFloor");
        Floor f = new Floor(1,"floor one");
        Building building = new Building("Middlesex","building");
        building.addFloor(f);
       
    }

    /**
     * Test of buildingReader method, of class Building.
     */
    @Test
    public void testBuildingReader() throws Exception {
        System.out.println("buildingReader");
        POI poi1 = new POI("POI 1","7","classroom");
        HashMap<String, POI> POINameToObject = new HashMap<>();
        Building building = new Building("Middlesex","building");
        building.buildingReader(POINameToObject);
        
    }

    /**
     * Test of buildingWriter method, of class Building.
     */
    @Test
    public void testBuildingWriter() throws Exception {
        System.out.println("buildingWriter");
        Building building = new Building("Middlesex","building");
        building.buildingWriter();
     
    }
    
}
