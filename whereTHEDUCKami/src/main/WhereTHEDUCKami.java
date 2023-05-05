/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.cs2212.wheretheduckami;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ellen
 */
public class WhereTHEDUCKami {

    public static void main(String[] args) throws IOException {
        
        /* Declare an array of Building objects */
        ArrayList<Building> buildings = new ArrayList<>(); 
        int numBuildings = 0;
        
        /* Declare an array of User objects */
        ArrayList<User> users = new ArrayList<>(); 
        int numUsers = 0;
        
        HashMap<String, POI> POINameToObject = new HashMap<String, POI>();
        HashMap<String, Building> BuildingAcronymToObjectMap = new HashMap<String, Building>(); // This is needed for looking up building from user-defined POIs
        
        /* The following four lines are for creating objects from the ObjectCreation_JsonWriter() method, */
        /* which is not needed once the initial buildings and users json files have been created */
        boolean manualObjectCreation = false;
        
        if (manualObjectCreation) { 
            ObjectCreation_JsonWriter();
            java.lang.System.exit(0);
        }
        
        /* Create Building objects and their component objects from Building json files */
        Scanner sc;
        try {
            sc = new Scanner(new File("Buildings.csv")); // Buildings.csv file contains a list of comma-separated buidling acronyms
            sc.useDelimiter(",");
            while (sc.hasNext()) {
                try {
                    String buildingAcronym = sc.next();
                    Building building = new Building(buildingAcronym);
                    building.buildingReader(POINameToObject);
                    buildings.add(building);
                    System.out.printf("Before building the map: sc.nect()=%s\n", buildingAcronym);
                    BuildingAcronymToObjectMap.put(buildingAcronym, building);
                    System.out.printf("After building the map: sc.nect()=%s", buildingAcronym);
                    numBuildings++;
                } catch (URISyntaxException | ParseException ex) {
                    Logger.getLogger(WhereTHEDUCKami.class.getName()).log(Level.SEVERE, null, ex);   //////////
            }
        } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WhereTHEDUCKami.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        /* Create User objects and their component objects from User json files */
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("UserData.json");
        try {
            Object obj = jsonparser.parse(reader);
            JSONArray usersJsonArray = (JSONArray)obj;
            
            Iterator<JSONObject> useriterator = usersJsonArray.iterator();
            while (useriterator.hasNext()) {
                JSONObject userJsonObj = (JSONObject)useriterator.next();
                //System.out.println("JSONObject: " + userJsonObj.toJSONString());  ////testing
                String username = (String) userJsonObj.get("Username");
                String password = (String) userJsonObj.get("Password");
                String type = (String) userJsonObj.get("Type");
                User user = new User(username, password, type);
                user.userReader(POINameToObject); // Read the user information from its json file
                users.add(user);
                numUsers++;
            }
        } catch (ParseException ex) {
            Logger.getLogger(WhereTHEDUCKami.class.getName()).log(Level.SEVERE, null, ex);  //////
        }
        
        /* Create Login Form */
        try  
        {  
            //create instance of the CreateLoginForm  
            CreateLoginForm loginform = new CreateLoginForm(buildings, users, numBuildings, numUsers, POINameToObject, BuildingAcronymToObjectMap);  
            //loginform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginform.setLocation(600,700);  
            loginform.setSize(830,460);  //set size of the frame  
            //loginform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginform.setVisible(true);  //make form visible to the user  
            
            /* In case new users are created during login, the number of users is updated */
            numUsers = loginform.updateNumUsers();
        }  
        catch(Exception e)  
        {     
            //handle exception   
            JOptionPane.showMessageDialog(null, e.getMessage());  
        }  

        
        
        /* Write all buildings information into json files by calling the buildingWriter method for each building*/
        for (int i=0; i<numBuildings; i++) {
            buildings.get(i).buildingWriter();
        } 
        
        /*Write all users information into json files by calling the User.logout() method for each user. Can change it to only do this for the logged-in user.*/
        for (int i=0; i<numUsers; i++) {
            users.get(i).logout();
        }
    }
    
    /* Hard-coded User Creation and write into a JSON file. Can be removed once good building JSON files have been create */
    public static void ObjectCreation_JsonWriter() throws IOException {
        
        /* MIDDLESEX COLLEGE */
        Building MC;
        Floor MCf0, MCf1, MCf2, MCf3, MCf4;
        Layer accessMC0, classMC0, washMC0, restMC0;
        Layer accessMC1, classMC1, washMC1;
        Layer accessMC2, classMC2, washMC2, labMC2;
        Layer accessMC3, classMC3, washMC3, labMC3; 
        Layer accessMC4;
        POI MCAccess1, MCAccess2, MCAccess3, MCAccess4, MCAccess5, MCAccess6, MCAccess7, MCAccess8, MCAccess9, MCAccess10;
        POI MCClass1, MCClass2, MCClass3, MCClass4, MCClass5, MCClass6, MCClass7, MCClass8, MCClass9;
        POI MCWash1, MCWash2, MCWash3, MCWash4, MCWash5, MCWash6, MCWash7, MCWash8, MCWash9, MCWash10;
        POI MCLab1, MCLab2, MCLab3, MCLab4, MCLab5, MCLab6;
        
        
        /* MC Building Object */
        MC = new Building("MiddleSex College","MC");
        MC.setDes("Middlesex College, located on the outskirts of UC Hill, houses the Department of Mathematics and Computer Science.");
        
        /* Floor Objects of MC Building */
        MCf0 = new Floor(0, "MC Ground Floor");
        MCf1 = new Floor(1, "MC Floor 1");
        MCf2 = new Floor(2, "MC Floor 2");
        MCf3 = new Floor(3, "MC Floor 3");
        MCf4 = new Floor(4, "MC Floor 4");
        MC.addFloor(MCf0);
        MC.addFloor(MCf1);
        MC.addFloor(MCf2);
        MC.addFloor(MCf3);
        MC.addFloor(MCf4);
        MCf0.setFileName("map1_1.jpg");
        MCf1.setFileName("map1_2.jpg");
        MCf2.setFileName("map1_3.jpg");
        MCf3.setFileName("map1_4.jpg");
        MCf4.setFileName("map1_5.jpg");
        
        /* Layer Objects of Each Floor of MC Building */
        // Layers for Floor G:
        accessMC0 = new Layer("Accessibility");
        classMC0 = new Layer("Classroom");
        //System.out.println("Layer classMC0's name = "+classMC0.getName()); // TEST
        washMC0 = new Layer("Washroom");
        restMC0 = new Layer("Restaurant");
        MCf0.addLayer(accessMC0);
        MCf0.addLayer(classMC0);
        MCf0.addLayer(washMC0);
        MCf0.addLayer(restMC0);
        
        // Layers for Floor 1:
        accessMC1 = new Layer("Accessibility");
        classMC1 = new Layer("Classroom");
        washMC1 = new Layer("Washroom");
        MCf1.addLayer(accessMC1);
        MCf1.addLayer(classMC1);
        MCf1.addLayer(washMC1);
        
        // Layers for Floor 2:
        accessMC2 = new Layer("Accessibility");
        classMC2 = new Layer("Classroom");
        washMC2 = new Layer("Washroom");
        labMC2 = new Layer("Laboratory");
        MCf2.addLayer(accessMC2);
        MCf2.addLayer(classMC2);
        MCf2.addLayer(washMC2);
        MCf2.addLayer(labMC2);
        
        // Layers for Floor 3:
        accessMC3 = new Layer("Accessibility");
        classMC3 = new Layer("Classroom");
        washMC3 = new Layer("Washroom");
        labMC3 = new Layer("Laboratory");
        MCf3.addLayer(accessMC3);
        MCf3.addLayer(classMC3);
        MCf3.addLayer(washMC3);
        MCf3.addLayer(labMC3);
        
        // Layers for Floor 4:
        accessMC4 = new Layer("Accessibility");
        MCf4.addLayer(accessMC4);
        
        
        /* POI Type of Each Layer of Each Floor of MC Building*/
        // Accessibility POIs:
        MCAccess1 = new POI("MC Floor 0 Elevator A", "3B", "Accessibility");
        MCAccess1.setDescription("MC 3B is an elevator in Middlesex College."); ////
        MCAccess2 = new POI("MC Floor 0 Elevator C", null, "Accessibility");
        MCAccess3 = new POI("MC Floor 0 Exit 6", "19E", "Accessibility");
        accessMC0.addPOI(MCAccess1);
        accessMC0.addPOI(MCAccess2);
        accessMC0.addPOI(MCAccess3);
        MCAccess4 = new POI("MC Floor 1 Elevator A", "3B", "Accessibility");
        MCAccess5 = new POI("MC Floor 1 Elevator C", null, "Accessibility");
        accessMC1.addPOI(MCAccess4);
        accessMC1.addPOI(MCAccess5);
        MCAccess6 = new POI("MC Floor 2 Elevator A", "3B", "Accessibility");
        MCAccess7 = new POI("MC Floor 2 Elevator C", null, "Accessibility");
        accessMC2.addPOI(MCAccess6);
        accessMC2.addPOI(MCAccess7);
        MCAccess8 = new POI("MC Floor 3 Elevator A", "3B", "Accessibility");
        MCAccess9 = new POI("MC Floor 3 Elevator C", null, "Accessibility");
        accessMC3.addPOI(MCAccess8);
        accessMC3.addPOI(MCAccess9);
        MCAccess10 = new POI("MC Floor 4 Elevator C", null, "Accessibility");
        accessMC4.addPOI(MCAccess10);
        
        MCAccess4 = new POI("MC Exit 10", "111", "Accessibility");
        accessMC1.addPOI(MCAccess4);
        
        // Classroom POIs:
        MCClass1 = new POI("MC6", "6", "Classroom");
        MCClass2 = new POI("MC17", "17", "Classroom");
        MCClass2.setDescription("MC 17 is a lecture hall in Middlesex College."); ////
        classMC0.addPOI(MCClass1);
        classMC0.addPOI(MCClass2);
        MCClass3 = new POI("MC105B", "105B", "Classroom");
        MCClass4 = new POI("MC105", "105", "Classroom");
        MCClass5 = new POI("MC110", "110", "Classroom");
        MCClass5.setDescription("MC 110 is a lecture hall in Middlesex College."); ////
        classMC1.addPOI(MCClass3);
        classMC1.addPOI(MCClass4);
        classMC1.addPOI(MCClass5);
        MCClass6 = new POI("MC204", "204", "Classroom");
        classMC2.addPOI(MCClass6);
        MCClass7 = new POI("MC300", "300", "Classroom");
        MCClass8 = new POI("MC316", "316", "Classroom");
        MCClass8.setDescription("MC 316 is a computer science classroom in Middlesex College."); ////
        MCClass9 = new POI("MC320", "320", "Classroom");
        classMC3.addPOI(MCClass7);
        classMC3.addPOI(MCClass8);
        classMC3.addPOI(MCClass9);
        
        // Washroom POIs:
        MCWash1 = new POI("MC4C", "4C", "Washroom");
        MCWash1.setDescription("MC 5B is an unisex washroom in Middlesex College."); ////
        MCWash2 = new POI("MC4B", "4B", "Washroom");
        MCWash3 = new POI("MC5B", "5B", "Washroom");
        MCWash4 = new POI("MC28E", "28E", "Washroom");
        washMC0.addPOI(MCWash1);
        washMC0.addPOI(MCWash2);
        washMC0.addPOI(MCWash3);
        washMC0.addPOI(MCWash4);
        MCWash5 = new POI("MC100", "100", "Washroom");
        MCWash6 = new POI("MC139", "139", "Washroom");
        washMC1.addPOI(MCWash5);
        washMC1.addPOI(MCWash6);
        MCWash7 = new POI("MC205", "205", "Washroom");
        MCWash8 = new POI("MC278", "278", "Washroom");
        washMC2.addPOI(MCWash7);
        washMC2.addPOI(MCWash8);
        MCWash9 = new POI("MC311", "311", "Washroom");
        MCWash10 = new POI("MC388", "388", "Washroom");
        washMC3.addPOI(MCWash9);
        washMC3.addPOI(MCWash10);
        
        // Laboratory POIs:
        MCLab1 = new POI("MC230", "230", "Laboratory");
        MCLab2 = new POI("MC235", "235", "Laboratory");
        MCLab3 = new POI("MC240", "240", "Laboratory");
        MCLab3.setDescription("MC 240 is a lab in Middlesex College "); ////
        MCLab4 = new POI("MC244", "244", "Laboratory");
        labMC2.addPOI(MCLab1);
        labMC2.addPOI(MCLab2);
        labMC2.addPOI(MCLab3);
        labMC2.addPOI(MCLab4);
        MCLab5 = new POI("MC325", "325", "Laboratory");
        MCLab6 = new POI("MC342", "342", "Laboratory");
        labMC3.addPOI(MCLab5);
        labMC3.addPOI(MCLab6);
        
        // Restaurant POIs:
        POI MCRest = new POI("Grad Club", "19", "Restaurant");
        restMC0.addPOI(MCRest);
        MCRest.setDescription("The Grad Club is a restaurant in Middlesex College."); ////
        
        MC.buildingWriter();
        
        /* PHYSICS & ASTRONOMY */
        Building PAB;
        Floor PABf0, PABf1, PABf2, PABf3;
        Layer accessPAB0, classPAB0, washPAB0, labPAB0;
        Layer accessPAB1, classPAB1, washPAB1, labPAB1;
        Layer accessPAB2, classPAB2, washPAB2, labPAB2;
        Layer accessPAB3, classPAB3, washPAB3, labPAB3;
        POI PABAccess1, PABAccess2, PABAccess3, PABAccess4;
        POI PABClass1, PABClass2, PABClass3, PABClass4, PABClass5, PABClass6, PABClass7, PABClass8, PABClass9, PABClass10;
        POI PABWash1, PABWash2, PABWash3, PABWash4, PABWash5, PABWash6, PABWash7, PABWash8, PABWash9, PABWash10;
        POI PABLab1, PABLab2, PABLab3, PABLab4, PABLab5, PABLab6, PABLab7, PABLab8, PABLab9, PABLab10, PABLab11, PABLab12;
        POI PABLab13, PABLab14, PABLab15, PABLab16, PABLab17, PABLab18, PABLab19, PABLab20, PABLab21, PABLab22, PABLab23, PABLab24;
        POI PABLab25, PABLab26, PABLab27, PABLab28, PABLab29, PABLab30, PABLab31, PABLab32, PABLab33, PABLab34, PABLab35, PABLab36;
        POI PABLab37;
        
        /* PAB Building Object */
        PAB = new Building("Physics and Astronomy","PAB");
        PAB.setDes("The Physics and Astronomy building, historically known as the Science Building, houses the Department of Physics & Astronomy.");
        
        /* Floor Objects of PAB Building */
        PABf0 = new Floor(0, "PAB Ground Floor");
        PABf1 = new Floor(1, "PAB Floor 1");
        PABf2 = new Floor(2, "PAB Floor 2");
        PABf3 = new Floor(3, "PAB Floor 3");
        PAB.addFloor(PABf0);
        PAB.addFloor(PABf1);
        PAB.addFloor(PABf2);
        PAB.addFloor(PABf3);
        PABf0.setFileName("map3_1.jpg");
        PABf1.setFileName("map3_2.jpg");
        PABf2.setFileName("map3_3.jpg");
        PABf3.setFileName("map3_4.jpg");
        
        /* Layer Objects of Each Floor of PAB Building */
        // Layers for Floor G:
        accessPAB0 = new Layer("Accessibility");
        classPAB0 = new Layer("Classroom");
        washPAB0 = new Layer("Washroom");
        labPAB0 = new Layer("Laboratory");
        PABf0.addLayer(accessPAB0);
        PABf0.addLayer(classPAB0);
        PABf0.addLayer(washPAB0);
        PABf0.addLayer(labPAB0);
        
        // Layers for Floor 1:
        accessPAB1 = new Layer("Accessibility");
        classPAB1 = new Layer("Classroom");
        washPAB1 = new Layer("Washroom");
        labPAB1 = new Layer("Laboratory");
        PABf1.addLayer(accessPAB1);
        PABf1.addLayer(classPAB1);
        PABf1.addLayer(washPAB1);
        PABf1.addLayer(labPAB1);
        
        // Layers for Floor 2:
        accessPAB2 = new Layer("Accessibility");
        classPAB2 = new Layer("Classroom");
        washPAB2 = new Layer("Washroom");
        labPAB2 = new Layer("Laboratory");
        PABf2.addLayer(accessPAB2);
        PABf2.addLayer(classPAB2);
        PABf2.addLayer(washPAB2);
        PABf2.addLayer(labPAB2);
        
        // Layers for Floor 3:
        accessPAB3 = new Layer("Accessibility");
        classPAB3 = new Layer("Classroom");
        washPAB3 = new Layer("Washroom");
        labPAB3 = new Layer("Laboratory");
        PABf3.addLayer(accessPAB3);
        PABf3.addLayer(classPAB3);
        PABf3.addLayer(washPAB3);
        PABf3.addLayer(labPAB3);
        
        /* POI Type of Each Layer of Each Floor of PAB Building*/
        // Accessibility POIs:
        PABAccess1 = new POI("PAB Exit 3", null, "Accessibility");
        PABAccess2 = new POI("PAB Elevator A", null, "Accessibility");
        PABAccess3 = new POI("PAB Elevator B", null, "Accessibility");
        accessPAB0.addPOI(PABAccess1);
        accessPAB0.addPOI(PABAccess2);
        accessPAB1.addPOI(PABAccess2);
        accessPAB2.addPOI(PABAccess2);
        accessPAB3.addPOI(PABAccess2);
        accessPAB0.addPOI(PABAccess3);
        accessPAB1.addPOI(PABAccess3);
        accessPAB2.addPOI(PABAccess3);
        accessPAB3.addPOI(PABAccess3);
        PABAccess3 = new POI("PAB Exit 4", null, "Accessibility");
        PABAccess3.setDescription("PAB Exit 4 is an accessible exit in Physics and Astronomy."); ////
        accessPAB1.addPOI(PABAccess3);
        
        // Classroom POIs:
        PABClass1 = new POI("PAB34", "34", "Classroom");
        PABClass2 = new POI("PAB36", "36", "Classroom");
        classPAB0.addPOI(PABClass1);
        classPAB0.addPOI(PABClass2);
        PABClass3 = new POI("PAB100", "100", "Classroom");
        PABClass3.setDescription("PAB 100 is a lecture room in Physics and Astronomy."); ////
        PABClass4 = new POI("PAB106", "106", "Classroom");
        PABClass5 = new POI("PAB117", "117", "Classroom");
        PABClass5.setDescription("PAB 117 is a lecture room in Physics and Astronomy."); ////
        PABClass6 = new POI("PAB148", "148", "Classroom");
        PABClass7 = new POI("PAB150", "150", "Classroom");
        classPAB1.addPOI(PABClass3);
        classPAB1.addPOI(PABClass4);
        classPAB1.addPOI(PABClass5);
        classPAB1.addPOI(PABClass6);
        classPAB1.addPOI(PABClass7);
        
        // Washroom POIs:
        PABWash1 = new POI("PAB18", "18", "Washroom");
        PABWash2 = new POI("PAB20", "20", "Washroom");
        washPAB0.addPOI(PABWash1);
        washPAB0.addPOI(PABWash2);
        PABWash3 = new POI("PAB131", "131", "Washroom");
        PABWash4 = new POI("PAB133", "133", "Washroom");
        PABWash5 = new POI("PAB153", "153", "Washroom");
        PABWash6 = new POI("PAB154", "154", "Washroom");
        washPAB1.addPOI(PABWash3);
        washPAB1.addPOI(PABWash4);
        washPAB1.addPOI(PABWash5);
        washPAB1.addPOI(PABWash6);
        PABWash7 = new POI("PAB219", "219", "Washroom");
        PABWash8 = new POI("PAB221", "221", "Washroom");
        washPAB2.addPOI(PABWash7);
        washPAB2.addPOI(PABWash8);
        PABWash9 = new POI("PAB305", "305", "Washroom");
        PABWash10 = new POI("PAB307", "307", "Washroom");
        washPAB3.addPOI(PABWash9);
        washPAB3.addPOI(PABWash10);
        
        // Laboratory POIs:
        PABLab1 = new POI("PAB14", "14", "Laboratory");
        PABLab2 = new POI("PAB21", "21", "Laboratory");
        labPAB0.addPOI(PABLab1);
        labPAB0.addPOI(PABLab2);
        PABLab3 = new POI("PAB126", "126", "Laboratory");
        PABLab4 = new POI("PAB127", "127", "Laboratory");
        PABLab5 = new POI("PAB128", "128", "Laboratory");
        PABLab6 = new POI("PAB130", "130", "Laboratory");
        PABLab7 = new POI("PAB130A", "130A", "Laboratory");
        PABLab8 = new POI("PAB134", "134", "Laboratory");
        PABLab9 = new POI("PAB134A", "134A", "Laboratory");
        PABLab10 = new POI("PAB136", "136", "Laboratory");
        labPAB1.addPOI(PABLab3);
        labPAB1.addPOI(PABLab4);
        labPAB1.addPOI(PABLab5);
        labPAB1.addPOI(PABLab6);
        labPAB1.addPOI(PABLab7);
        labPAB1.addPOI(PABLab8);
        labPAB1.addPOI(PABLab9);
        labPAB1.addPOI(PABLab10);
        PABLab11 = new POI("PAB211", "211", "Laboratory");
        PABLab12 = new POI("PAB214", "214", "Laboratory");
        PABLab13 = new POI("PAB214A", "214A", "Laboratory");
        PABLab14 = new POI("PAB216", "216", "Laboratory");
        PABLab15 = new POI("PAB217", "217", "Laboratory");
        PABLab16 = new POI("PAB218", "218", "Laboratory");
        PABLab16.setDescription("PAB 218 is a lab in Physics and Astronomy"); ////
        PABLab17 = new POI("PAB218A", "218A", "Laboratory");
        PABLab18 = new POI("PAB222", "222", "Laboratory");
        PABLab19 = new POI("PAB222A", "222A", "Laboratory");
        PABLab20 = new POI("PAB225", "225", "Laboratory");
        PABLab21 = new POI("PAB226", "226", "Laboratory");
        PABLab22 = new POI("PAB226A", "226A", "Laboratory");
        PABLab23 = new POI("PAB226B", "226B", "Laboratory");
        PABLab24 = new POI("PAB226C", "226C", "Laboratory");
        PABLab25 = new POI("PAB227", "227", "Laboratory");
        PABLab26 = new POI("PAB241", "241", "Laboratory");
        PABLab27 = new POI("PAB241A", "241A", "Laboratory");
        PABLab28 = new POI("PAB242", "242", "Laboratory");
        PABLab29 = new POI("PAB242A", "242A", "Laboratory");
        PABLab30 = new POI("PAB246", "246", "Laboratory");
        PABLab31 = new POI("PAB251", "251", "Laboratory");
        PABLab32 = new POI("PAB253", "253", "Laboratory");
        PABLab33 = new POI("PAB253A", "253A", "Laboratory");
        PABLab34 = new POI("PAB254", "254", "Laboratory");
        PABLab35 = new POI("PAB255", "255", "Laboratory");
        labPAB2.addPOI(PABLab11);
        labPAB2.addPOI(PABLab12);
        labPAB2.addPOI(PABLab13);
        labPAB2.addPOI(PABLab14);
        labPAB2.addPOI(PABLab15);
        labPAB2.addPOI(PABLab16);
        labPAB2.addPOI(PABLab17);
        labPAB2.addPOI(PABLab18);
        labPAB2.addPOI(PABLab19);
        labPAB2.addPOI(PABLab20);
        labPAB2.addPOI(PABLab21);
        labPAB2.addPOI(PABLab22);
        labPAB2.addPOI(PABLab23);
        labPAB2.addPOI(PABLab24);
        labPAB2.addPOI(PABLab25);
        labPAB2.addPOI(PABLab26);
        labPAB2.addPOI(PABLab27);
        labPAB2.addPOI(PABLab28);
        labPAB2.addPOI(PABLab29);
        labPAB2.addPOI(PABLab30);
        labPAB2.addPOI(PABLab31);
        labPAB2.addPOI(PABLab32);
        labPAB2.addPOI(PABLab33);
        labPAB2.addPOI(PABLab34);
        labPAB2.addPOI(PABLab35);
        PABLab36 = new POI("PAB301", "301", "Laboratory");
        PABLab37 = new POI("PAB303", "303", "Laboratory");
        labPAB3.addPOI(PABLab36);
        labPAB3.addPOI(PABLab37);
        
        PAB.buildingWriter();
        
        /* NATURAL SCIENCE CENTER */
        Building NSC;
        Floor NSCf0, NSCf1, NSCf2;
        Layer accessNSC0, classNSC0, washNSC0;
        Layer accessNSC1, classNSC1, washNSC1, restNSC1;
        Layer accessNSC2, washNSC2;
        POI NSCAccess1, NSCAccess2, NSCAccess3, NSCAccess4, NSCAccess5, NSCAccess6;
        POI NSCClass1, NSCClass2, NSCClass3;
        POI NSCWash1, NSCWash2, NSCWash3, NSCWash4, NSCWash5, NSCWash6, NSCWash7, NSCWash8, NSCWash9;
        
        /* NSC Building Object */
        NSC = new Building("Natural Science Center","NSC");
        NSC.setDes("Natural Science Center is home to the Allyn & Betty Taylor Library and some of Western's largest lecture halls.");
        
        /* Floor Objects of MC Building */
        NSCf0 = new Floor(0, "NSC Ground Floor");
        NSCf1 = new Floor(1, "NSC Floor 1");
        NSCf2 = new Floor(2, "NSC Floor 2");
        NSC.addFloor(NSCf0);
        NSC.addFloor(NSCf1);
        NSC.addFloor(NSCf2);
        NSCf0.setFileName("map3_1.jpg");
        NSCf1.setFileName("map2_2.jpg");
        NSCf2.setFileName("map2_3.jpg");
        
        /* Layer Objects of Each Floor of NSC Building */
        // Layers for Floor G:
        accessNSC0 = new Layer("Accessibility");
        classNSC0 = new Layer("Classroom");
        //System.out.println("Layer classMC0's name = "+classMC0.getName()); // TEST
        washNSC0 = new Layer("Washroom");
        NSCf0.addLayer(accessNSC0);
        NSCf0.addLayer(classNSC0);
        NSCf0.addLayer(washNSC0);
        
        // Layers for Floor 1:
        accessNSC1 = new Layer("Accessibility");
        classNSC1 = new Layer("Classroom");
        washNSC1 = new Layer("Washroom");
        restNSC1 = new Layer("Restaurant");
        NSCf1.addLayer(accessNSC1);
        NSCf1.addLayer(classNSC1);
        NSCf1.addLayer(washNSC1);
        NSCf1.addLayer(restNSC1);
        
        // Layers for Floor 2:
        accessNSC2 = new Layer("Accessibility");
        washNSC2 = new Layer("Washroom");
        NSCf2.addLayer(accessNSC2);
        NSCf2.addLayer(washNSC2);
        
        /* POI Type of Each Layer of Each Floor of MC Building*/
        // Accessibility POIs:
        NSCAccess1 = new POI("NSC Floor 0 Elevator A", null, "Accessibility");
        accessNSC0.addPOI(NSCAccess1);
        NSCAccess2 = new POI("NSC Floor 1 Elevator A", null, "Accessibility");
        NSCAccess3 = new POI("NSC Exit 12", null, "Accessibility");
        NSCAccess4 = new POI("NSC Exit 13", null, "Accessibility");
        NSCAccess5 = new POI("NSC Exit 14", null, "Accessibility");
        accessNSC1.addPOI(NSCAccess2);
        accessNSC1.addPOI(NSCAccess3);
        accessNSC1.addPOI(NSCAccess4);
        accessNSC1.addPOI(NSCAccess5);
        NSCAccess6 = new POI("NSC Floor 2 Elevator A", null, "Accessibility");
        accessNSC2.addPOI(NSCAccess6);
        
        // Classroom POIs:
        NSCClass1 = new POI("NSC1", "1", "Classroom");
        NSCClass2 = new POI("NSC7", "7", "Classroom");
        classNSC0.addPOI(NSCClass1);
        classNSC0.addPOI(NSCClass2);
        NSCClass3 = new POI("NSC145", "145", "Classroom");
        classNSC1.addPOI(NSCClass3);
        
        // Washroom POIs:
        NSCWash1 = new POI("NSC20A", "20A", "Washroom");
        NSCWash2 = new POI("NSC20B", "20B", "Washroom");
        washNSC0.addPOI(NSCWash1);
        washNSC0.addPOI(NSCWash2);
        NSCWash3 = new POI("NSC102", "102", "Washroom");
        NSCWash4 = new POI("NSC104", "104", "Washroom");
        NSCWash5 = new POI("NSC143", "143", "Washroom");
        NSCWash6 = new POI("NSC147", "147", "Washroom");
        washNSC1.addPOI(NSCWash3);
        washNSC1.addPOI(NSCWash4);
        washNSC1.addPOI(NSCWash5);
        washNSC1.addPOI(NSCWash6);
        NSCWash7 = new POI("NSC219", "219", "Washroom");
        NSCWash8 = new POI("NSC220", "220", "Washroom");
        NSCWash9 = new POI("NSC221", "221", "Washroom");
        washNSC2.addPOI(NSCWash7);
        washNSC2.addPOI(NSCWash8);
        washNSC2.addPOI(NSCWash9);
        
        // Restaurant POIs:
        POI NSCRest1 = new POI("Tim Hortons", "111D", "Restaurant");
        POI NSCRest2 = new POI("Food Hub", "111A", "Restaurant");
        restNSC1.addPOI(NSCRest1);
        restNSC1.addPOI(NSCRest2);
        NSCRest1.setDescription("Tim Hortons, Baked Goods & Soups is a fast food restaurant in Natural Science Center."); ////
        NSCRest2.setDescription("Casa Burrito, Pizza Pizza, and Teriyaki Experience are fast food restaurants in Natural Science Center."); ////
        
        NSC.buildingWriter();
        
        /* Creating user1 */
        User user1 = new User("test1@gmail.com", "test1", "Normal");
       
        /* Add favorite POIs from the existing POIs */
        user1.addFavoritePOI(MCRest);
        user1.addFavoritePOI(MCClass2);
        user1.addFavoritePOI(MCLab3);
        user1.addFavoritePOI(PABClass3);
        user1.addFavoritePOI(PABLab16);
         
        /* Create user1's new user-defined POIs */
        user1.createUserDefinedPOI("Servos's Office", "MC387", "Office", "Prof. Daniel Servos' Office", "MC", 3);        
        user1.createUserDefinedPOI("Annabelle's Office", "MC340", "Office", "Annabelle Cloutier's Office", "MC", 3);
        user1.logout();
        
        User user2 = new User("test2@gmail.com", "test2", "Normal");
        
        /* Add favorite POIs from the existing POIs */
        user2.addFavoritePOI(MCClass5);
        user2.addFavoritePOI(MCWash1);
        //user2.addFavoritePOI(MCClass8);
        //user2.addFavoritePOI(PABClass5);
        user2.addFavoritePOI(PABLab16);
        user2.addFavoritePOI(PABAccess3);
        user2.addFavoritePOI(NSCRest1);
        user2.addFavoritePOI(NSCRest2);
        
        /* Create new user-defined POIs */
        user2.createUserDefinedPOI("NSC Cafeteria", "NSC111", "Cafeteria", "Open space with seating for eating.", "NSC", 1);    
        user2.createUserDefinedPOI("PAB Study Area", "PAB40", "Study Area", "Open space study area.", "PAB", 0);
        user2.logout();
        
        /* Create a developer user */
        User developer = new User("super@gmail.com", "super", "Developer");
        developer.logout();
        
        /* Write usernames, passwords and types in the UserData json file */
        JSONArray arr = new JSONArray();
        JSONObject obj;
        
        obj = new JSONObject();
        obj.put("Username","test1@gmail.com");
        obj.put("Password","test1");
        obj.put("Type", "Normal");         
        arr.add(obj);
        
        obj = new JSONObject();
        obj.put("Username","test2@gmail.com");
        obj.put("Password","test2");
        obj.put("Type", "Normal");         
        arr.add(obj);
        
        obj = new JSONObject();
        obj.put("Username","super@gmail.com");
        obj.put("Password","super");
        obj.put("Type", "Developer");         
        arr.add(obj);
                                               
        try {
            FileWriter ﬁle = new FileWriter("UserData.json");
            ﬁle.write(arr.toJSONString());
            ﬁle.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error occured");
        }
    }
}