/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.wheretheduckami;

/**
 * User.java
 * This class contains user information representing all users.
 * @author Ellen
 */

import java.util.List;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private String userName;
    private String realName; //optional
    private String passWord;
    private List<POI> userDefinedPOIs;
    private List<POI> favoritePOIs;
    private int numUserDefinedPOIs;
    private int numFavoritePOIs;
    private String type;
 
    public User(String username) {
        this.userName = username;
        this.passWord = null;
        this.numUserDefinedPOIs = 0;
        this.numFavoritePOIs = 0;
        this.userDefinedPOIs = new ArrayList<>();
        this.favoritePOIs = new ArrayList<>();
        this.type = "Normal";
    }
    
    
     /* Constructor: Creates a user instance defining its username, password, and user-defined & favorited POIs. */
    public User(String username, String pwd, String type) {
        this.userName = username;
        this.passWord = pwd;
        this.numUserDefinedPOIs = 0;
        this.numFavoritePOIs = 0;
        this.userDefinedPOIs = new ArrayList<>();
        this.favoritePOIs = new ArrayList<>();
        this.type = type;
    }
    
    /* Method to get username of user. 
    * @return username
    */
    public String getUserName() {
        return this.userName;
    }
    
    /* Method to set username of user. 
    * @param uname               user's username
    */
    public void setUserName(String uname) {
        this.userName = uname;
    }
    
    /* Method to get password of user. 
    * @return password
    */
    public String getPassWord() {
        return this.passWord;
    }
    
    /* Method to set password of user. 
    * @param pwd               user's password
    */
    public void setPassWord(String pwd) {
        this.passWord = pwd;
    }
    
    public int getNumFavoritePOIs() {
        return this.numFavoritePOIs;
    }
    
    public int getNumUserDefinedPOIs() {
        return this.numUserDefinedPOIs;
    }
    
    /* Method to verify user login. 
    * @param username               user's username
    * @param pwd                        user's password
    * @return true or false
    */
    public boolean verifyLogin(String username, String pwd) {
        if (this.userName.equals(username)&&this.passWord.equals(pwd)) 
            return true;
        else
            return false;
    }
    
    /* Method to add favorite POIs. 
    * @param poi        POI object
    */
    public void addFavoritePOI(POI poi) {
        favoritePOIs.add(poi);  /////
        numFavoritePOIs++;
    }
    
    /* Method to delete favorite POIs. 
    * @param poi        POI object
    */
    public void deleteFavoritePOI(POI poi) {  // Assume the input parameter is a POI object. Can change it to poiName if necessary
        String deletedPOIName = poi.getName();
        for (int i = 0; i < this.numFavoritePOIs; i++) {
            if (deletedPOIName.equals(this.favoritePOIs.get(i).getName())) {
                this.favoritePOIs.remove(i);
                numFavoritePOIs--;
            }
        }
    }
    
    public List<POI> getFavoritePOIs() {
        return this.favoritePOIs;
    }
    
    /* Get the ith user-defined POI */
    public POI getUserDefinedPOI(int i) {
        return this.userDefinedPOIs.get(i);
    }
    
    public POI getFavoritePOI(int i) {
        return this.favoritePOIs.get(i);
    }
    
    /* Method to create user-defined POIs. 
    * @param name               POI name
    * @param roomNum         POI room number
    * @param type                 POI type
    * @param descrip             POI description
    */
    public void createUserDefinedPOI(String name, String roomNum, String type, String descrip) {
        POI p = new POI(name, roomNum, type);
        p.setDescription(descrip);
        this.userDefinedPOIs.add(p);   ////
        numUserDefinedPOIs++;
    }
    
    public void createUserDefinedPOI(String name, String roomNum, String type, String descrip, String buildingAcronym, int fnum) {
        POI p = new POI(name, roomNum, type, buildingAcronym, fnum);
        p.setDescription(descrip);
        this.userDefinedPOIs.add(p);   ////
        numUserDefinedPOIs++;
    }
    
    public void createUserDefinedPOI(String name, String roomNum, String type, String descrip, int x, int y, String buildingAcronym, int fnum) {
        POI p = new POI(name, roomNum, type, x, y, buildingAcronym, fnum);
        p.setDescription(descrip);
        this.userDefinedPOIs.add(p);   ////
        numUserDefinedPOIs++;
    }
    
    public void addUserDefinedPOI(POI p) {
        //POI p = new POI(name, roomNum, type, x, y, buildingAcronym, fnum);
        //p.setDescription(descrip);
        this.userDefinedPOIs.add(p);   ////
        numUserDefinedPOIs++;
    }
    
    
    /* Method to remove user-defined POIs. 
    * @param name               POI name
    */
    public void removeUserDefinedPOI(String name) {
        for (int i=0; i<this.numUserDefinedPOIs; i++) {
            if (name.equals(userDefinedPOIs.get(i).getName())) {
                this.userDefinedPOIs.remove(i);
                this.numUserDefinedPOIs--;
            }
        }
    }
    
    public boolean searchUserDefinedPOI(String name) {
        for (int i=0; i<this.numUserDefinedPOIs; i++) {
            if (name.equals(userDefinedPOIs.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    /*
    public void removeUserDefinedPOI(String name) {
        for (int i=0; i<this.numUserDefinedPOIs; i++) {
            if (name.equals(userDefinedPOIs.get(i).getName())&&i<numUserDefinedPOIs-1) {
                userDefinedPOIs..set(i, favoritePOIs.get(i+1));
                numUserDefinedPOIs--;
            }
        }
    }
    */
    
    /* Method to get user-defined POIs. */
    public List<POI> getUserDefinedPOIs() {
        return this.userDefinedPOIs;
    }
    
    String getType () {
        return (type);
    }
    
    void setType(String type){
        this.type = type;
    }
    /* Method to read the user information (e.g., favorite POIs and user-defined POIs from JSON file into the user object.
    * @param POINameToObject            hashmap objects with POI name as key and POI as name
    */
    public void userReader(HashMap<String, POI> POINameToObject) throws ParseException{
       
        /* Specify the file name to read. An example is Usertest1@gmail.com.json */
        String filename = "User"+this.userName+".json";
        
        FileReader reader = null;
        JSONParser jsonparser = new JSONParser();
        try {
            reader = new FileReader(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object obj;
        try { 
            obj = jsonparser.parse(reader);
            JSONObject userJsonObj = (JSONObject)obj;
            this.userName = (String) userJsonObj.get("Username");
            this.passWord = (String) userJsonObj.get("Password");
            this.type = (String) userJsonObj.get("Type");
            
            //this.numFavoritePOIs = (int)(long) userJsonObj.get("Number of Favorite POIs");
            //this.numUserDefinedPOIs = (int)(long) userJsonObj.get("Number of User-defined POIs");
            
            /* Read and create user-defined POIs */
            JSONArray userDefinedPOIsJsonArray = (JSONArray) userJsonObj.get("User-defined POIs");
            Iterator<JSONObject> uPOIiterator = userDefinedPOIsJsonArray.iterator();
            //System.out.println("Line 150 in User.java "); ////// Test
            while (uPOIiterator.hasNext()) {
                JSONObject uPOIJsonObj = (JSONObject)uPOIiterator.next();
                //System.out.println("Line 114 in Building.java "); ////// Test
                //System.out.println("JSONObject: " + uPOIJsonObj.toJSONString());
                String POIName = (String) uPOIJsonObj.get("User-defined POIName");
                String POINum = (String) uPOIJsonObj.get("User-defined POINum");
                String POIType = (String) uPOIJsonObj.get("User-defined POIType");
                int POIX = (int)(long)uPOIJsonObj.get("Coordinate_X");
                int POIY = (int)(long)uPOIJsonObj.get("Coordinate_Y");
                String POIDescription = (String) uPOIJsonObj.get("User-defined POIDescription");
                
                String buildingAcronym = (String) uPOIJsonObj.get("Building");
                int floorNum = (int)(long) uPOIJsonObj.get("Floor_Num");
                             
                //System.out.printf("User defined POI Description: %s\n", POIDescription);  /////////////////
                
                this.createUserDefinedPOI(POIName, POINum, POIType, POIDescription, POIX, POIY, buildingAcronym, floorNum);                
            }     
            
            /* Read favorite POIs */
            JSONArray favoritePOIsJsonArray = (JSONArray) userJsonObj.get("Favorite POIs");
            Iterator<JSONObject> fPOIiterator = favoritePOIsJsonArray.iterator();
            //System.out.println("Line 166 in User.java "); ////// Test
            while (fPOIiterator.hasNext()) {
                JSONObject fPOIJsonObj = (JSONObject)fPOIiterator.next();
                //System.out.println("Line 114 in Building.java "); ////// Test
                //System.out.println("JSONObject: " + fPOIJsonObj.toJSONString());
                String POIName = (String) fPOIJsonObj.get("Favorite POIName");
                //String POINum = (String) fPOIJsonObj.get("Favorite POINum");
                //String POIType = (String) fPOIJsonObj.get("Favorite POIType");
                //String POIDescription = (String) fPOIJsonObj.get("Favorite POIDescrption");
                
                //System.out.printf("in Line 178 of User.java: POIName = %s\n", POIName);
                this.addFavoritePOI(POINameToObject.get(POIName));                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
  
    }
    
    
    /* Method to save user information in memory by writing it into a JSON file after the user logs out. */
    public void logout() {
        JSONObject user = new JSONObject();
        user.put("Username", this.userName);
        user.put("Password", this.passWord);
        user.put("Number of Favorite POIs", this.numFavoritePOIs);
        user.put("Number of User-defined POIs", this.numUserDefinedPOIs);
        user.put("Type", this.type);
        
        /* Write Favorite POIs into JSON file */
        JSONArray fPOIs = new JSONArray();
        //System.out.printf("Line 206 in User.java: username=%s numFavoritePOIs=%d\n", this.userName, numFavoritePOIs);
        for (int i=0; i<numFavoritePOIs;i++) {
            JSONObject fPOI = new JSONObject();
            //System.out.printf("Line 209 in User.java: i=%d\n", i);
            System.out.printf("getName()=%s\n", favoritePOIs.get(i).getName());
            fPOI.put("Favorite POIName", favoritePOIs.get(i).getName());
            fPOI.put("Favorite POINum", favoritePOIs.get(i).getNum());
            fPOI.put("Favorite POIDescription", favoritePOIs.get(i).getDescription());
            fPOI.put("Favorite POIType", favoritePOIs.get(i).getType());
            fPOI.put("Coordinate_X", favoritePOIs.get(i).getX());
            fPOI.put("Coordinate_Y", favoritePOIs.get(i).getY());
            fPOIs.add(fPOI);
        }
        user.put("Favorite POIs", fPOIs);
        
        /* Write User-defined POIs into JSON file */
        JSONArray uPOIs = new JSONArray();
        for (int i=0; i<numUserDefinedPOIs;i++) {    //////
            JSONObject uPOI = new JSONObject();
            //System.out.printf("Line 223 in User.java: i=%d\n", i);
            //System.out.printf("getName()=%s\n", userDefinedPOIs.get(i).getName());
            uPOI.put("User-defined POIName", userDefinedPOIs.get(i).getName());
            uPOI.put("User-defined POINum", userDefinedPOIs.get(i).getNum());
            uPOI.put("User-defined POIType", userDefinedPOIs.get(i).getType());
            uPOI.put("User-defined POIDescription", userDefinedPOIs.get(i).getDescription());
            uPOI.put("Coordinate_X", userDefinedPOIs.get(i).getX());
            uPOI.put("Coordinate_Y", userDefinedPOIs.get(i).getY());
            uPOI.put("Building", userDefinedPOIs.get(i).getBuildingAcronym());
            uPOI.put("Floor_Num", userDefinedPOIs.get(i).getFloorNum());
            uPOIs.add(uPOI);
        }
        user.put("User-defined POIs", uPOIs);
        
        String filename = "User"+this.userName+".json";
        try (FileWriter file = new FileWriter(filename)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(user.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
}