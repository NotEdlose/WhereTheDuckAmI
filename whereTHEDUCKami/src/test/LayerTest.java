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
 * @author nicholascorcoran
 */
public class LayerTest {
    private Layer layer;
    private POI poi;
    
    public LayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        layer = new Layer("Test Layer");
        poi = new POI("Test POI","Test room 12", "Building type",true,"description test", 10, 5);
    }
    
   
    @Test
    public void testSetName() {
        layer.setName("New Layer Name");
        assertEquals(layer.getName(), "New Layer Name");
    }
    
    @Test
    public void testAddPOI() {
        layer.addPOI(poi);
        assertEquals(layer.getNumPOIs(), 1);
        assertEquals(layer.getPOI(0), poi);
    }

    @Test
    public void testDeletePOI() {
        layer.addPOI(poi);
        layer.deletePOI(poi);
        assertEquals(layer.getNumPOIs(), 0);
    }

    @Test
    public void testUpdateName() {
        layer.updateName("New Layer Name");
        assertEquals(layer.getName(), "New Layer Name");
    }

    @Test
    public void testGetNumPOIs() {
        assertEquals(layer.getNumPOIs(), 0);
        layer.addPOI(poi);
        assertEquals(layer.getNumPOIs(), 1);
    }

}
