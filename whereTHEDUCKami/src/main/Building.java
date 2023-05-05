/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.wheretheduckami;

/**
 * Building.java
 * This class contains the skeleton of information of all building objects.
 * It methods to get and set the building name, acronym, location, description,
 * number of floors and list of floors. It also has methods to read building information
 * from a JSON file and update the building location.
 * @author Ellen
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Building {
    private String name;
    private int x;
    private int y;
    private String description;
    private List<Floor> floors;      
    private int numFloors;
    private String acronym;
    
     /**
    * Creates an instance of the Building class with a given acronym.
    * 
    * @param acronym - the acronym of the building
    */
    public Building(String acronym) {
        this.acronym = acronym;
        this.numFloors = 0;
        this.floors = new ArrayList<>();
    }
    
    /**
    * Creates an instance of the Building class with given building name and acronym.
    * 
    * @param name            the name of the building
    * @param acronym        the acronym of the building
    */
    public Building(String name, String acronym) {  /////////////////
        this.name = name;
        this.acronym = acronym;
        this.numFloors = 0;
        this.floors = new ArrayList<>();
        //System.out.println("At the end of Building constructor. name="+this.name);
    }
    
    /**
     * Method to set name of building.
     * @param name               building's name
     */
    public void setBuildingName(String name){
        this.name = name;
    }
    
    /**
     * Method to get name of building.
     * @return name                building's name 
     */
    public String getBuildingName(){
        return name;
    }
    
    /**
     * Method to set acronym of building.
     * @param a               building's acronym
     */
    public void setBuildingAcronym(String a){
        this.acronym = a;
    }
    
    /**
     * Method to get acronym of building.
     * @return acronym
     */
    public String getBuildingAcronym() {
        return this.acronym;
    }
    
    /**
     * Method to set map location.
     * @param x               map's x coordinate
     * @param y               map's y coordinate
     */
    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }
    
     /**
     * Method to building location.
     * @return location         an array with two elements holding the x and y coordinate
     */
    public int[] updateBuildingLoca(){
        int[] location = new int[2];
        location[0] = this.x;
        location[1] = this.y;
        return location;
    }
    
    /**
     * Method to set acronym of building.
     * @param description               building's description
     */
    public void setDes(String description){
        this.description = description;
    }
    
    /**
     * Method to get description of building.
     * @return description                building's description              
     */
    public String getDes() {
        return this.description;
    }
    
    /**
     * Method to get number of floors on building.
     * @return numFloors                the number of building floors           
     */
    public int getNumFloors() {
        return this.numFloors;
    }
    
    /**
     * Method to get the list of floors of a building.
     * @return floors                list of floors of a building      
     */
    public List<Floor> getFloors() {
        return this.floors;
    }
    
    /**
     * Method to add a floor for a building.
     * @param f              floor
     */
    public void addFloor(Floor f) {
        this.floors.add(f);   //////////
        this.numFloors++;
    }
    
    /**
     * Method reads building information from a JSON file and creates a Building object.
     * @param POINameToObject
     * @throws FileNotFoundException
     * @throws IOException
     * @throws URISyntaxException
     * @throws ParseException 
     */
    public void buildingReader(HashMap<String, POI> POINameToObject) throws  FileNotFoundException, IOException, URISyntaxException, ParseException {
     
        /* Specify the file name to read. Examples are BuildingMC.json, BuildingPAB.json */
        String filename = "Building"+this.acronym+".json";
        
        JSONParser jsonparser = new JSONParser();
        // FileReader reader = new FileReader(".\\wheretheduckami\\building.json");
        FileReader reader = new FileReader(filename);
        Object obj = jsonparser.parse(reader);
       
        JSONObject buildingJsonObj = (JSONObject)obj;
        this.name = (String) buildingJsonObj.get("BuildingName");
        this.acronym = (String) buildingJsonObj.get("Acronym");
        this.description = (String) buildingJsonObj.get("BuildingDescription");
  
        //System.out.println("Line 106 in Building.java "+this.name); ////// Test
                
        JSONArray floorsJsonArray = (JSONArray) buildingJsonObj.get("Floors");
        Iterator<JSONObject> fiterator = floorsJsonArray.iterator();
        //System.out.println("Line 110 in Building.java "); ////// Test
        while (fiterator.hasNext()) {
            //System.out.println("Line 112 in Building.java "); ////// Test
            JSONObject floorJsonObj = (JSONObject)fiterator.next();
            //System.out.println("Line 114 in Building.java "); ////// Test
            //System.out.println("JSONObject: " + floorJsonObj.toJSONString());
            String floorDescrip = (String) floorJsonObj.get("FloorDescription");
            //System.out.println(floorDescrip+"Line 117 in Building.java "); ////// Test
            int floorNum = (int)(long)floorJsonObj.get("FloorNum");
            //System.out.println(floorNum);
            //System.out.printf("Line 116 in Building.java floorNUm. floorNum=%d\n", floorNum); ////// Test
            String floorMapFileName = (String)floorJsonObj.get("FloorMapFileName");   ////////////////
            
            Floor floor = new Floor((int)floorNum, floorDescrip); //Create a floor object
            floor.setFileName(floorMapFileName);
            //System.out.println("Line 125 in Building.java "+floorDescrip); ////// Test
            
            JSONArray layersJsonArray = (JSONArray) floorJsonObj.get("Layers");
            Iterator<JSONObject> layeriterator = layersJsonArray.iterator();
            while (layeriterator.hasNext()) {
                JSONObject layerJsonObj = (JSONObject)layeriterator.next();
                String layerName = (String)layerJsonObj.get("LayerName");
                Layer layer = new Layer(layerName);
                
                JSONArray POIsJsonArray = (JSONArray) layerJsonObj.get("POISet");
                Iterator<JSONObject> POIiterator = POIsJsonArray.iterator();
                while (POIiterator.hasNext()) {
                    JSONObject POIJsonObj = (JSONObject)POIiterator.next();
                    String POINum = (String)POIJsonObj.get("POINum");
                    String POIDescription = (String)POIJsonObj.get("POIDescription");
                    String POIName = (String)POIJsonObj.get("POIName");
                    String POIType = (String)POIJsonObj.get("POIType");
                    int POIX = (int)(long)POIJsonObj.get("Coordinate_X");
                    int POIY = (int)(long)POIJsonObj.get("Coordinate_Y");

                    //System.out.println("POI Type: "+POIType);
                    //POI poi = new POI(POIName, POINum, POIType, POI_x, POI_y);
                    POI poi = new POI(POIName, POINum, POIType, POIX, POIY, this.acronym, floorNum);
                    poi.setDescription(POIDescription);
                    //poi.setBuildingAcronym(this.acronym);
                    //poi.setFloorNum(floorNum);
                    
                    POINameToObject.put(POIName, poi); // put the poi object into a hashmap with the POIname as key
                    
                    layer.addPOI(poi);
                }
                
                floor.addLayer(layer);
            }
            
            this.addFloor(floor);
        }
        
    }
    
    /**
     * Method writes all the information about and inside the building into a nested JSON file.
     * @throws IOException 
     */
    public void buildingWriter() throws IOException{ /////// removed the input parameters
           
        JSONObject building = new JSONObject();
        building.put("BuildingName", this.name);
        building.put("Acronym", this.acronym);
        building.put("BuildingDescription", this.description);
        JSONArray floorList = new JSONArray();
        for (int i=0; i<numFloors;i++) {
            JSONObject floor = new JSONObject();
            floor.put("FloorNum", floors.get(i).getFloorNumber());
            floor.put("FloorDescription", floors.get(i).getDescrip());
            floor.put("FloorMapFileName", floors.get(i).getFileName());   ////////////////////////
            
            JSONArray layerCollection = new JSONArray();
            for (int j=0; j<floors.get(i).getNumLayers(); j++) {
                JSONObject layer = new JSONObject();
                layer.put("LayerName", floors.get(i).getLayer(j).getName()); ////
                JSONArray POISet = new JSONArray();
                for (int k=0; k<floors.get(i).getLayer(j).getNumPOIs(); k++) {
                    JSONObject POI = new JSONObject();
                    POI.put("POIName", floors.get(i).getLayer(j).getPOI(k).getName());
                    POI.put("POINum", floors.get(i).getLayer(j).getPOI(k).getNum());
                    POI.put("POIDescription", floors.get(i).getLayer(j).getPOI(k).getDescription());  
                    POI.put("POIType", floors.get(i).getLayer(j).getPOI(k).getType());
                    POI.put("Coordinate_X", floors.get(i).getLayer(j).getPOI(k).getX());
                    POI.put("Coordinate_Y", floors.get(i).getLayer(j).getPOI(k).getY());
                    //POI.put("POI_X", floors.get(i).getLayer(j).getPOI(k).getX());
                    //POI.put("POI_Y", floors.get(i).getLayer(j).getPOI(k).getY());
                    POISet.add(POI);
                }
                layer.put("POISet", POISet);
                layerCollection.add(layer);
            }
            floor.put("Layers", layerCollection);
            floorList.add(floor);
        } 
        building.put("Floors", floorList);

        String filename = "Building"+this.acronym+".json";
        try (FileWriter file = new FileWriter(filename)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(building.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}