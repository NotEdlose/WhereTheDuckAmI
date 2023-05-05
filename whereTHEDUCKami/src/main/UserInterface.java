/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.wheretheduckami;

/**
 *
 * @author Ellen
 */

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Point;
import java.awt.event.MouseAdapter;

public class UserInterface extends JFrame implements ActionListener, MouseListener {

    private JPanel buttonPanel, floorPanel, layerPanel;
    private JPanel containerPanel;
    private JScrollPane scrollPane;
    private JLabel imageLabel, infoLabel;
    private int currentIndex;
    private int currentMap;
    //private ImageIcon mapImageIcon;
    //Image mapImage;
    ImageIcon mapImageIcon; 
    //private JPanel mapPanel;
    //private ArrayList<Point> pinPositions;
    private ArrayList<JLabel> pinLabels; // a list of pins on the map 
    private JLabel floorLabel;  // A label indicating which floor is being viewed
    //private JTextArea infoTextArea;
    private JLabel infoArea;
    Point mouseClickedPoint;
 
    
    private ArrayList<Building> buildings;
    private ArrayList<User> users;
    private int numBuildings;
    private User currentUser;
    private HashMap<String, POI> POINameToObject;
    HashMap<String, Building> BuildingAcronymToObjectMap;
    
    //private POI newlyCreatedPOI;
    
    private double scale = 1.0;
    
    private WeatherApp weather = new WeatherApp();

    public UserInterface(ArrayList<Building> buildings, ArrayList<User> users, int numBuildings, User currentUser, HashMap<String, POI> POINameToObject, HashMap<String, Building> BuildingAcronymToObjectMap) {
        this.buildings=buildings;
        this.users=users;
        this.numBuildings = numBuildings;
        this.currentUser = currentUser;
        this.POINameToObject = POINameToObject;
        this.BuildingAcronymToObjectMap = BuildingAcronymToObjectMap;
        
        floorPanel = new JPanel();
        layerPanel = new JPanel(new GridLayout(10,2));
        
        pinLabels = new ArrayList();
        floorLabel = new JLabel();
        infoArea = new JLabel();

        mouseClickedPoint = new Point();
        
        this.infoLabel = new JLabel("Welcome user "+currentUser.getUserName()+" to Western's Interactive Map!");
        infoLabel.setBounds(200,50,500,25);
        infoLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        infoLabel.setForeground(Color.BLUE);
        add(infoLabel, BorderLayout.NORTH);
                
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        
        //Creat the Buidling menu
        JMenu buildingMenu = new JMenu("Buildings");
        for (int i=0; i<numBuildings; i++) {
            final int index = i;  //This is needed because i is not accessible in the ActionListerner clock below. A "final" variable is.
            JMenuItem menuItem = new JMenuItem(buildings.get(i).getBuildingName());
            menuItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    showBuildingInfo(buildings.get(index)); /////////////////////
                    showFloorButtons(buildings.get(index));
                    buildingPopUp(buildings.get(index));     ///////////////////////
                }
            });

            buildingMenu.add(menuItem);
        }
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
            public void actionPerformed(ActionEvent e) {
                currentUser.logout();    
                
                /* Write all buildings information into json files by calling the buildingWriter method for each building*/
                if (currentUser.getType().equals("Developer")) {// Should add checking whether the user is the developer.
                    for (int i=0; i<numBuildings; i++) {  try {
                        buildings.get(i).buildingWriter();
                        } catch (IOException ex) {
                            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } 
                }
                java.lang.System.exit(0);

            }
        });
        buildingMenu.add(exitMenuItem);
        
        /*
        // Create the user's Favorite POI menu
        JMenu favoriteMenu = new JMenu("Favorite POIs");
        for (int i=0; i<currentUser.getNumFavoritePOIs(); i++) {
             final int index = i;  //This is needed because i is not accessible in the ActionListerner clock below. A "final" variable is
            String fPOIName = currentUser.getFavoritePOIs().get(i).getName();
            JMenuItem menuItem = new JMenuItem(fPOIName);
            
            menuItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    favPOIPopUp(currentUser.getFavoritePOIs().get(index));
                }
            });
            
            favoriteMenu.add(menuItem);
        }
        
        JMenu userPOIMenu = new JMenu("My POIs");
        for (int i=0; i<currentUser.getNumUserDefinedPOIs(); i++) {
            final int index = i;  //This is needed because i is not accessible in the ActionListerner clock below. A "final" variable is
            String fPOIName = currentUser.getUserDefinedPOIs().get(i).getName();
            JMenuItem menuItem = new JMenuItem(fPOIName);
            
            menuItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    userPOIPopUp(currentUser.getUserDefinedPOIs().get(index));
                }
            });
            
            userPOIMenu.add(menuItem);
        }
        */
        
        // Create the user's Favorite POI menu with Edit functions
        JMenu editFavoriteMenu = new JMenu("Favorite POIs");
        JMenuItem showFavPOIItem = new JMenuItem("Show Favorite POIs");
        showFavPOIItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    //imageLabel.addMouseListener((MouseListener) this); 
                    showFavPOIPopUp();
                }
        });
        editFavoriteMenu.add(showFavPOIItem);
        
        JMenuItem addFavPOIItem = new JMenuItem("Add Favorite POI");
        addFavPOIItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    addFavPOIPopUp();
                }
        });
        editFavoriteMenu.add(addFavPOIItem);
        
        JMenuItem removeFavPOIItem = new JMenuItem("Remove Favorite POI");
        removeFavPOIItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    removeFavPOIPopUp();
                }
        });
        editFavoriteMenu.add(removeFavPOIItem);
        
        //Create the user's Favorite POI menu with Edit functions
        JMenu editUserPOIMenu = new JMenu("My POIs");
        JMenuItem showUserPOIItem = new JMenuItem("Show My POIs");
        showUserPOIItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    //imageLabel.addMouseListener((MouseListener) this); 
                    showUserPOIPopUp();
                }
        });
        editUserPOIMenu.add(showUserPOIItem);
        
        JMenuItem addUserPOIItem = new JMenuItem("Add My POIs");
        addUserPOIItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    //imageLabel.addMouseListener((MouseListener) this); 
                    addUserPOIPopUp();
                }
        });
        editUserPOIMenu.add(addUserPOIItem);
        
        JMenuItem removeUserPOIItem = new JMenuItem("Remove My POIs");
        removeUserPOIItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    removeUserPOIPopUp();
                }
        });
        editUserPOIMenu.add(removeUserPOIItem);
        
        /* Create the Search menu */
        JMenu searchMenu = new JMenu("Search");
        
        // POI search menu item
        JMenuItem POISearchItem = new JMenuItem("POI Search");
        POISearchItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    POISearchPopUp();
                }
        });
        // Layer search menu item
        JMenuItem layerSearchItem = new JMenuItem("Layer Search");
        layerSearchItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    layerSearchPopUp();
                }
        });
        
        searchMenu.add(POISearchItem);
        searchMenu.add(layerSearchItem);
        //searchMenu.add(floorSearchItem);
        
        // Create the Developer Menu
        JMenu developerMenu = new JMenu("Developer");
        JMenuItem addPOIMenuItem = new JMenuItem("Add POI");
        addPOIMenuItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    
                    addBuiltInPOI().setXY(mouseClickedPoint.x, mouseClickedPoint.y);
                    
                }
        });
        JMenuItem editPOIMenuItem = new JMenuItem("Edit POI");
        editPOIMenuItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    //editBuiltInPOI();
                }
        });
        
        developerMenu.add(addPOIMenuItem);
        developerMenu.add(editPOIMenuItem);
        
        
         // Create the help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpMenuItem = new JMenuItem("Help");
        helpMenuItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    helpPopUp();
                }
        });
        
        // Create the about menu
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() { // add an ActionListener to the menu item
                public void actionPerformed(ActionEvent e) {
                    // code to execute when the menu item is clicked
                    aboutPopUp();
                }
        });
        helpMenu.add(helpMenuItem);
        helpMenu.add(aboutMenuItem);
        
        // Add the menus into the menu bar
        menuBar.add(buildingMenu);
        //menuBar.add(favoriteMenu);
        //menuBar.add(userPOIMenu);
        menuBar.add(editFavoriteMenu);
        menuBar.add(editUserPOIMenu);
        menuBar.add(searchMenu);
        menuBar.add(helpMenu);
        if (currentUser.getType().equals("Developer")) 
            menuBar.add(developerMenu);
        
        setJMenuBar(menuBar);
        
        setTitle("Western Interactive Map");
        setSize(1600, 1000);
        
        // Create the container panel
        containerPanel = new JPanel(new BorderLayout());
        
        // Create the scroll pane for the map image
        imageLabel = new JLabel();
        imageLabel.setLayout(null);
        /////
        //mapPanel = new JPanel();
        //mapPanel.add(imageLabel);
        //scrollPane = new JScrollPane(mapPanel); 
        /////
        scrollPane = new JScrollPane(imageLabel);             
        containerPanel.add(scrollPane, BorderLayout.CENTER);  ////a window is addeded to the container, causing an error
        
        mapImageIcon = new ImageIcon("WesternImage.jpg");
        imageLabel.setIcon(mapImageIcon);

        buttonPanel = new JPanel(new GridLayout(10,2));
       
        JButton homeScreen = new JButton("Home Screen"); //Create Homescreen button
        homeScreen.addActionListener(this);
        buttonPanel.add(homeScreen);
        for (int i=0; i<numBuildings; i++) {
            // Create the building button
            JButton mapButton = new JButton(buildings.get(i).getBuildingName());
            mapButton.addActionListener(this);
            buttonPanel.add(mapButton);
        }
        
        buttonPanel.add(new WeatherApp(),BorderLayout.SOUTH);

        JScrollPane buildingListScrollPane = new JScrollPane(buttonPanel);
        
        buildingListScrollPane.setPreferredSize(new Dimension(170, 750));
        Border border = BorderFactory.createTitledBorder("Select Building");
        buildingListScrollPane.setBorder(border);
        containerPanel.add(buildingListScrollPane,BorderLayout.WEST);
 
        JPanel zoomButtonPanel = new JPanel();
        JButton zoomIn = new JButton("Zoom Out");
        zoomIn.addActionListener(event -> {
            scale -= 0.1;
            int newWidth = (int)(mapImageIcon.getIconWidth() * scale);
            int newHeight = (int)(mapImageIcon.getIconHeight() * scale);
            Image scaledImage = mapImageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            imageLabel.setIcon(scaledIcon);
        });

        JButton zoomOut = new JButton("Zoom In");
        zoomOut.addActionListener(event -> {
            scale += 0.1;
            int newWidth = (int)(mapImageIcon.getIconWidth() * scale);
            int newHeight = (int)(mapImageIcon.getIconHeight() * scale);
            Image scaledImage = mapImageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            imageLabel.setIcon(scaledIcon);
        });
        zoomButtonPanel.add(zoomOut);
        zoomButtonPanel.add(zoomIn);
        
        containerPanel.add(zoomButtonPanel, BorderLayout.SOUTH);

        add(containerPanel);   ///////////////////////////////////////
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Load the appropriate image based on which button was clicked
        
        if (e.getActionCommand().equals("Home Screen")) {
            mapImageIcon = new ImageIcon("WesternImage.jpg");
            imageLabel.setIcon(mapImageIcon); 
            
            remove(infoLabel);
            infoLabel.setText(" Welcome User "+currentUser.getUserName());      
            add(infoLabel, BorderLayout.NORTH);
            
            // Remove the previous floor info label
            imageLabel.remove(floorLabel); 
            
            // Remove the pins associated with the previous map
            for (JLabel pinLabel : pinLabels) {              
                imageLabel.remove(pinLabel);
            }        
            pinLabels.clear(); // Clear the pinLabel list to remove all the pins on the previous map
            
            //Here may need the following
            //imageLabel.addMouseListener(this); 
            
            revalidate();
            repaint();
        }
        else {
            for (int i=0; i<numBuildings; i++) {
                if (e.getActionCommand().equals(buildings.get(i).getBuildingName())) {
                    mapImageIcon = new ImageIcon(buildings.get(i).getFloors().get(1).getFileName());
                    
                    // Some floor image is too big, so scale it down 
                    Image image = mapImageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    imageLabel.setIcon(scaledIcon);
                   
                    // Add a floor label on the map
                    imageLabel.remove(floorLabel); // Remove the previous label
                    floorLabel = new JLabel("Floor 1");
                    floorLabel.setBounds(80,40,220,25); /////
                    floorLabel.setFont(new Font("Serif", Font.PLAIN, 20));
                    floorLabel.setForeground(Color.BLUE);
                    imageLabel.add(floorLabel);
                    
                    imageLabel.remove(infoArea); // Remove the information from previous map
                    
                    // Remove the pins associated with the previous map
                    for (JLabel pinLabel : pinLabels) {              
                        imageLabel.remove(pinLabel);
                    }
                    
                    pinLabels.clear(); // Clear the pinLabel list to remove all the pins on the previous map

                    //Here may need the following
                    //imageLabel.addMouseListener(this);

                    showBuildingInfo(buildings.get(i));
                    showFloorButtons(buildings.get(i));
                }
            }
        }        
    }
   
    private void showBuildingInfo(Building b) {
        remove(infoLabel);
        infoLabel.setText(" You are viewing "+b.getBuildingName()+" ("+b.getBuildingAcronym()+")");      
        add(infoLabel, BorderLayout.NORTH);
 
        revalidate();
        repaint();
    }
    
    
    private void buildingPopUp(Building b) {
        //System.out.println("At the beginning of showFloorButtons");
        // Create a new JDialog as the pop-up window
        JDialog popupWindow = new JDialog(this, b.getBuildingName(), true);
        popupWindow.setSize(910, 150);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(100, 50);
        

        // Add a JLabel to the pop-up window
        //JLabel label = new JLabel(b.getDes());
        String jText = ("  Building name: "+b.getBuildingName()+"\n"
                        + "  Building acronym: "+b.getBuildingAcronym()+"\n"
                        + "  Description: "+b.getDes()+"\n"
                        + "  Number of Floors: "+Integer.toString(b.getNumFloors()));
        JTextArea textArea = new JTextArea(jText);
        popupWindow.getContentPane().add(textArea);

        // Display the pop-up window
        popupWindow.setVisible(true);

    } 
    
    private void favPOIPopUp (POI p) {
       // Create a new JDialog as the pop-up window
        JDialog popupWindow = new JDialog(this, p.getName(), true);
        popupWindow.setSize(910, 100);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(100, 50);
        

        // Add a JLabel to the pop-up window
        //JLabel label = new JLabel(b.getDes());
        String jText = ("  POI name: "+p.getName()+"\n"
                        + "  Room number: "+p.getNum()+"\n"
                        + "  POI type: "+p.getType()+"\n"
                        + "  POI Description: "+p.getDescription());
        JTextArea textArea = new JTextArea(jText);
        popupWindow.getContentPane().add(textArea);

        // Display the pop-up window
        popupWindow.setVisible(true);

    }
    
    private void userPOIPopUp (POI p) {
       // Create a new JDialog as the pop-up window
        JDialog popupWindow = new JDialog(this, p.getName(), true);
        popupWindow.setSize(910, 100);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(100, 50);
        

        // Add a JLabel to the pop-up window
        //JLabel label = new JLabel(b.getDes());
        String jText = ("  POI name: "+p.getName()+"\n"
                        + "  Room number: "+p.getNum()+"\n"
                        + "  POI type: "+p.getType()+"\n"
                        + "  POI Description: "+p.getDescription());
        JTextArea textArea = new JTextArea(jText);
        popupWindow.getContentPane().add(textArea);

        // Display the pop-up window
        popupWindow.setVisible(true);

    }
    
    private void floorSearchPopUp() {  
        
        // Create text ﬁeld to get user's search 
        JTextField floorSearchField = new JTextField(15);    //set length of the text  
        floorSearchField.setBounds(100,70,185,25);
        
        // Create a new JLabel to prompt the user to enter their search query
        JLabel searchPromptLabel = new JLabel("Enter your floor search query:");
        searchPromptLabel.setBounds(20, 70, 150, 25);
        
        // Create a new JButton to submit the search query
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(300, 70, 80, 25);
        searchButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // Execute the search query
                String query = floorSearchField.getText();
                
                // TODO: Add code to execute the search based on the query
                // For example, you could display the search results in a new window
            }
        });
        
        // Create a new JDialog as the pop-up window
        JDialog popupWindow = new JDialog(this, "Floor Search Menu", true);
        popupWindow.setSize(800, 400);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(150, 50);
        

        // Add a JTextArea to the pop-up window
        popupWindow.getContentPane().add(floorSearchField);

        // Display the pop-up window
        popupWindow.setVisible(true);
    }
    
    private void layerSearchPopUp() {
        
        JLabel label=new JLabel("<html> Please select the building on the left panel <br>"
                + " - then select the floor on the top panel<br>"
                + " - then use the layer buttons on the right panel to select the layer</html>");
        
        // Create a new JDialog as the pop-up window
        JDialog popupWindow = new JDialog(this, "Layer Search Message", true);
        popupWindow.setSize(800, 100);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(150, 50);
        

        // Add a JTextArea to the pop-up window
        popupWindow.getContentPane().add(label);

        // Display the pop-up window
        popupWindow.setVisible(true);
    }
    
    private void POISearchPopUp() {
        
        // Create a new JDialog as the pop-up window
        JDialog popupWindow = new JDialog(this, "POI Search Window", true);
        popupWindow.setLayout(null);   ///////
        popupWindow.setSize(600, 200);
        
       
        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(450, 400);
        
        // Create a new JLabel to prompt the user to enter their search query
        JLabel searchPromptLabel = new JLabel("Enter your POI search query (such as MC110):");
        searchPromptLabel.setBounds(30, 50, 250, 25);
        
        // Create text ﬁeld to get user's search 
        JTextField POISearchField = new JTextField(15);    //set length of the text  
        POISearchField.setBounds(60,90,200,25);
       

        // Create a new JButton to submit the search query
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(320, 90, 80, 25); 
        searchButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // Execute the search query
                String query = POISearchField.getText();
                
                if (POINameToObject.get(query)!=null) {
                    displayPOIInfoWithFloorMap(POINameToObject.get(query));
                    //putPinOnPOI(POINameToObject.get(query).getX(), POINameToObject.get(query).getY());
                    popupWindow.dispose();
                }
                else {
                    /*JLabel errorLabel = new JLabel("POI not found!");
                    errorLabel.setBounds(30, 40, 250, 25);
                    errorLabel.setForeground(Color.red);
                    //errorLabel.setLocation(10,10);
                    popupWindow.getContentPane().add(errorLabel);
                    errorLabel.setVisible(true);
                    */
                    JOptionPane jOptionPane = new JOptionPane();
                    JOptionPane.showMessageDialog(null, "There is no POI named "+query, "POI not found",jOptionPane.ERROR_MESSAGE);
                    //popupWindow.dispose();

                } 
                
            }
        });
        
        
        // Add the components the pop-up window
        popupWindow.getContentPane().add(searchPromptLabel);
        popupWindow.getContentPane().add(POISearchField);
        popupWindow.getContentPane().add(searchButton);
        
        // Display the pop-up window
        popupWindow.setVisible(true);
    }
    
    private void helpPopUp () {
        String jText = ( "** HELP MENU **\r\n\n"
                 + "How to choose and navigate maps:\r\n "
                 + "- On the 'Select Building' Panel on the left, click on the building you wish to navigate\r\n "
                 + "- The Floor buttons will show up on the top. You can select the foor by clicking on a floor button\r\n"
                 + "- After the floor button is clicked, lay buttons will show up on the right.\r\n"
                 + "- After click on one of the lay buttons, pins will be placed on the POIs in the selected layer\r\n"
                 + "  and the information about the layer will be displayed on the top-left area of the map.\r\n\n"
                 + "How to search for POI:\r\n"
                 + "- Click on Search menu and and select POI search\r\n\n"
                 + "How to search for a layer on a floor:\r\n"
                 + "- On the Layer button panel on the right, click on the layber button you would like to search for\r\n\n"
                 + "How to favorite a POI:\r\n"
                 + "- Click on the star icon to mark a POI as favorite");
        
         // Create a new JDialog as the pop-up window
        JDialog popupWindow = new JDialog(this, "Help Menu", true);
        popupWindow.setSize(1000, 400);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(250, 200);
        

        // Add a JTextArea to the pop-up window
        JTextArea textArea = new JTextArea(jText);
        popupWindow.getContentPane().add(textArea);

        // Display the pop-up window
        popupWindow.setVisible(true);
    }
    
    private void aboutPopUp () {
        String jText = ( "** ABOUT MENU **\r\n\n"
                 + "APPLICATIN NAME: WhereTHEDUCKami\r\n\n"
                 + "VERSION: 1.0\r\n\n"
                 + "RELEASE DATE: 06/04/2023\r\n\n"
                 + "DEVELOPERS: \r\n Ellen Anne Huang\r\n Alaa Karzoun\r\n Edwin Or\r\n Nicholas Raymond Corcoran\r\n Weiyi Zeng\r\n\n"
                 + "CONTACT INFO: \r\n ehuang69@uwo.ca\r\n akarzoun@uwo.ca\r\n eor3@uwo.ca\r\n ncorcor@uwo.ca\r\n wzeng29@uwo.ca\r\n"
                 );
        
         // Create a new JDialog as the pop-up window
        JDialog popupWindow = new JDialog(this, "About Menu", true);
        popupWindow.setSize(800, 400);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(150, 50);
        

        // Add a JTextArea to the pop-up window
        JTextArea textArea = new JTextArea(jText);
        popupWindow.getContentPane().add(textArea);

        // Display the pop-up window
        popupWindow.setVisible(true);
    }
    
    //Incomplete
    // Display POI information with the floor map it is on
    private void displayPOIInfoWithFloorMap(POI p) {
        // Show floor image
        Building b = BuildingAcronymToObjectMap.get(p.getBuildingAcronym());
        int floorNum = p.getFloorNum();
        
        showBuildingInfo(b);
        mapImageIcon = new ImageIcon(b.getFloors().get(floorNum).getFileName());
        Image image = mapImageIcon.getImage();
        Image scaledImage = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(scaledIcon);
                
        // Add a floor label on the map
        imageLabel.remove(floorLabel); // Remove the previous label
        //floorLabel = new JLabel("Floor "+ Integer.toString(b.getFloors().get(floorNum).getFloorNumber()));
        floorLabel = new JLabel("Floor "+ Integer.toString(floorNum));
        floorLabel.setBounds(700,40,120,25); /////
        floorLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        floorLabel.setForeground(Color.BLUE);
        imageLabel.add(floorLabel);
        
        imageLabel.remove(infoArea); // Remove previous display in the infoTextArea
        //JLabel label = new JLabel(b.getDes());
        String jText = ("<html>  POI name: "+p.getName()+"<br>"
                        + "  Room number: "+p.getNum()+"<br>"
                        + "  POI type: "+p.getType()+"<br>"
                        + "  POI Description: "+p.getDescription()+"<br>"
                        + "  Building: " +p.getBuildingAcronym()+"<br>"
                        + "  Floor: "+ Integer.toString(p.getFloorNum())+"</html>");
                        
        //JTextArea textArea = new JTextArea(jText);
        //infoTextArea = new JTextArea(jText);
        infoArea = new JLabel(jText);
        infoArea.setBounds(80,40,500,200); /////
        infoArea.setFont(new Font("Serif", Font.PLAIN, 18));
        infoArea.setForeground(Color.RED);
        imageLabel.add(infoArea);
        infoArea.setVisible(true);

        
        //The following may be moved to a clearMap() method.
        // Remove the pins associated with the previous map
        for (JLabel pinLabel : pinLabels) {              
            imageLabel.remove(pinLabel);
        }
                    
        pinLabels.clear(); // Clear the pinLabel list to remove all the pins on the previous map
        
        
        putPinOnPOI(p.getX(), p.getY());
        
        // Here is to enable POI name input to get X and Y for connecting a point on the map to the POI name
        //imageLabel.addMouseListener(this);  
        
        showFloorButtons(b);
        // Show layer buttons on the EAST
        showLayerButtons(b.getFloors().get(floorNum));   
    }
    
    private void showFloorButtons(Building b) {
        floorPanel.removeAll();
        
        // Create the buttons
        for (int i=0; i<b.getNumFloors(); i++) {
            final int index = i;  //This is needed because i is not accessible in the ActionListerner clock below. A "final" variable is
            JButton floorButton = new JButton("Floor "+Integer.toString(b.getFloors().get(index).getFloorNumber()));
            
            floorButton.addActionListener(event -> {
                
                imageLabel.remove(infoArea);
                // Show floor image
                mapImageIcon = new ImageIcon(b.getFloors().get(index).getFileName());
                Image image = mapImageIcon.getImage();
                Image scaledImage = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                imageLabel.setIcon(scaledIcon);
                
                // Add a floor label on the map
                imageLabel.remove(floorLabel); // Remove the previous label
                floorLabel = new JLabel("Floor "+ Integer.toString(b.getFloors().get(index).getFloorNumber()));
                floorLabel.setBounds(80,40,120,25); /////
                floorLabel.setFont(new Font("Serif", Font.PLAIN, 20));
                floorLabel.setForeground(Color.BLUE);
                imageLabel.add(floorLabel);
               
                // Remove the pins associated with the previous map
                for (JLabel pinLabel : pinLabels) {              
                    imageLabel.remove(pinLabel);
                }
                    
                pinLabels.clear(); // Clear the pinLabel list to remove all the pins on the previous map
               
                //May need the following
                //imageLabel.addMouseListener(this);
                
// Show layer buttons on the EAST
                showLayerButtons(b.getFloors().get(index));    
            });
            floorPanel.add(floorButton);
        }
        containerPanel.add(floorPanel, BorderLayout.NORTH);
        //pack();
        revalidate();
        repaint();
    }   
    
    private void showLayerButtons(Floor f) {
        layerPanel.removeAll();
        
        for (int i=0; i<f.getNumLayers(); i++) {
            final int index = i;  //This is needed because i is not accessible in the ActionListerner clock below. A "final" variable is
            JButton layerButton = new JButton(f.getLayer(index).getName());   /////Remove "Layer"
            
            layerButton.addActionListener(event -> {
                showLayerInfo(f.getLayer(index));    
            });
            layerPanel.add(layerButton);
        }
        containerPanel.add(layerPanel, BorderLayout.EAST);
        //pack();
        revalidate();
        repaint();
    }  
    
    // Show Layer Information
    private void showLayerInfo(Layer layer) {
        
        // Remove the information from previous map
        imageLabel.remove(infoArea); 
        
        int numPOIs = layer.getNumPOIs();
        String jText = ("<html>  There are "+Integer.toString(numPOIs)+" built-in POIs <br>"
                        + "  in the "+layer.getName()+" layer.<br>"
                        + "  They are pinned in the map.</html>");
                        
        infoArea = new JLabel(jText);
        infoArea.setBounds(80,40,500,200); /////
        infoArea.setFont(new Font("Serif", Font.PLAIN, 18));
        infoArea.setForeground(Color.RED);
        imageLabel.add(infoArea);
        infoArea.setVisible(true);
        
        // Remove the pins associated with the previous map
        for (JLabel pLabel : pinLabels) {              
            imageLabel.remove(pLabel);
        }
                    
        pinLabels.clear();  //  Clear the existing pins

        // Put pins on all the pois in the layer
        for (int i=0; i<layer.getNumPOIs(); i++) {
            POI poi = layer.getPOI(i); //get the ith POI
            String type = poi.getType();
            if (type.equals(layer.getName())) {
                putPinOnPOI(poi.getX(), poi.getY());
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // Get the x and y coordinates of the mouse click
        int x = e.getX();
        int y = e.getY();
        mouseClickedPoint.x=x;
        mouseClickedPoint.y=y;
        System.out.printf("mouseClickedPoint.x=%d mouseClickedPoint.y=%d\n", mouseClickedPoint.x, mouseClickedPoint.y);
       
        //String poiName=getPOIname(x,y);
        
        // Here for Manual connection of POI name and coordinates
        //POIInputPopUp(x,y);
        
        Point mapLocation = imageLabel.getLocation();
        Point containerLocation = containerPanel.getLocation();
        ImageIcon pinIcon = new ImageIcon("pin.png");
        JLabel pinLabel = new JLabel(pinIcon);
        
        pinLabels.add(pinLabel); // Add the pinlabel into the list pineLabels (an instance variable for this class) 
        
        int w=pinIcon.getIconWidth();
        int h= pinIcon.getIconHeight();
        
        //pinLabel.setBounds(x-mapLocation.x, y-mapLocation.y, pinIcon.getIconWidth(), pinIcon.getIconHeight());
        /////
        
        double scale=0.10;
        int newWidth = (int)(pinIcon.getIconWidth() * scale);
        int newHeight = (int)(pinIcon.getIconHeight() * scale);
        //System.out.printf("newWith=%d, newHeight=%d\n", newWidth, newHeight);
        Image scaledImage = pinIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        pinLabel.setIcon(scaledIcon);
        ////
        
        x = x-w/2;
        y = y-h/2;
        
        pinLabel.setBounds(x, y, pinIcon.getIconWidth(), pinIcon.getIconHeight());
        //pinLabel.setLocation(x,y);
        imageLabel.add(pinLabel);
  
        revalidate();
        repaint();
        
        /*
        Point p = getMapCoordinates(x, y);
        System.out.printf("x=%d, y=%d\n",x,y);
        // Print the coordinates to the console
        System.out.println("Mouse clicked at: " + p.getX() + ", " + p.getY());
        */
            
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        // create a new JLabel
        /*
        JLabel label = new JLabel("Mouse position: (0, 0)");
        label.setBounds(10, 10, 200, 20);
        label.setVisible(true);
        */
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void putPinOnPOI(int x, int y) {
        ImageIcon pinIcon = new ImageIcon("pin.png");
        JLabel pinLabel = new JLabel(pinIcon);
        
        /*
        // Remove the pins associated with the previous map
        for (JLabel pLabel : pinLabels) {              
            imageLabel.remove(pLabel);
        }
                    
        pinLabels.clear();  //  Clear the existing pins
        */
        
        pinLabels.add(pinLabel); // Add the pinlabel into the list pineLabels (an instance variable for this class) 
        
        int w=pinIcon.getIconWidth();
        int h= pinIcon.getIconHeight();
                
        double scale=0.13;
        int newWidth = (int)(pinIcon.getIconWidth() * scale);
        int newHeight = (int)(pinIcon.getIconHeight() * scale);
        //System.out.printf("newWith=%d, newHeight=%d\n", newWidth, newHeight);
        Image scaledImage = pinIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        pinLabel.setIcon(scaledIcon);
        ////
        
        x = x-w/2;
        y = y-h/2;
        
        //x = x-w/2+20;  //testing
        //y = y-h/2+20;
        
        pinLabel.setBounds(x, y, pinIcon.getIconWidth(), pinIcon.getIconHeight());
        //pinLabel.setLocation(x,y);
        imageLabel.add(pinLabel);
  
        revalidate();
        repaint();
                   
    }
    
    // Only for testing
    Point getMapCoordinates(int x, int y) {   // Just for testing
        
      // Get the size and position of an imageLabel 
      int labelX = imageLabel.getX();
      int labelY = imageLabel.getY();
      int labelWidth = imageLabel.getWidth();
      int labelHeight = imageLabel.getHeight();
      
            Image image = mapImageIcon.getImage();
      int mapImageWidth = image.getWidth(null);
      int mapImageHeight = image.getHeight(null);
      
      //int mapImageWidth = mapImageIcon.getIconWidth();
      //int mapImageHeight = mapImageIcon.getIconHeight();
     
      Graphics2D g2d = (Graphics2D) imageLabel.getGraphics();
      AffineTransform transform = g2d.getTransform();
      double zoomLevelX = transform.getScaleX();
      double zoomLevelY = transform.getScaleY();
      //int zoomLevel = (int) (Math.max(zoomLevelX, zoomLevelY)*100);
      //int zoomLevel = (int) (Math.max(zoomLevelX, zoomLevelY));
      
      //double scalingFactor = zoomLevel/100.0;
      
      //double relativeX = (x - labelX)/(labelWidth * scalingFactor);
      //double relativeY = (y - labelY)/(labelHeight * scalingFactor);
     
      double relativeX = (x - labelX)/(labelWidth * zoomLevelX)*100;
      double relativeY = (y - labelY)/(labelHeight * zoomLevelY)*100;

      Point p = new Point((int)relativeX, (int)relativeY);
      return p;
      
    }
    
    // Get the POI name for the POI on location (x,y) on a map. This is for the collecting data purpose.  
    private String getPOIname(int x,int y) {
        Scanner s = new Scanner(System.in);
        System.out.printf("Please enter the POI name: ");
        String name = s.nextLine();
        return(name);
    }
    
    private void POIInputPopUp(int x, int y) {
        
        JDialog popupWindow = new JDialog(this, "POI Name Input Window", true);
        popupWindow.setLayout(null);   ///////
        popupWindow.setSize(600, 200);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(450, 800);
                
        // Create a new JLabel to prompt the user to enter the POI name
        JLabel searchPromptLabel = new JLabel("Enter the POI Name that you clicked on (such as MC110):");
        searchPromptLabel.setBounds(30, 50, 350, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField POISearchField = new JTextField(15);    //set length of the text  
        POISearchField.setBounds(60,90,200,25);
        
        // Create a new JButton to submit the input
        JButton searchButton = new JButton("Submit");
        searchButton.setBounds(320, 90, 80, 25); 
        searchButton.addActionListener(new ActionListener() {
            String poiName;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Execute the search query
                poiName = POISearchField.getText();
                if (poiName!=null) {
                    POINameToObject.get(poiName).setX(x);
                    POINameToObject.get(poiName).setY(y);
                    popupWindow.dispose();
                }
                
                
            }
            
        });
        
        // Create a new JDialog as the pop-up window
        

        // Add the components the pop-up window
        //popupWindow.add(searchPromptLabel);
        //popupWindow.add(POISearchField);
        //popupWindow.add(searchButton);
        
        popupWindow.getContentPane().add(searchPromptLabel);
        popupWindow.getContentPane().add(POISearchField);
        popupWindow.getContentPane().add(searchButton);
        
        // Display the pop-up window
        popupWindow.setVisible(true);
    }

    private void addFavPOIPopUp(){
        JDialog popupWindow = new JDialog(this, "Add Favorite POI Window", true);
        popupWindow.setLayout(null);   ///////
        popupWindow.setSize(600, 200);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(450, 800);
                
        // Create a new JLabel to prompt the user to enter the POI name
        JLabel searchPromptLabel = new JLabel("Enter the POI Name that you would like to add (such as MC110):");
        searchPromptLabel.setBounds(30, 50, 350, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField POISearchField = new JTextField(15);    //set length of the text  
        POISearchField.setBounds(60,90,200,25);
        
        // Create a new JButton to submit the input
        JButton searchButton = new JButton("Submit");
        searchButton.setBounds(320, 90, 80, 25); 
        searchButton.addActionListener(new ActionListener() {
            String poiName;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insert the POI
                poiName = POISearchField.getText();
                if (poiName!=null) {
                    //System.out.printf("poiName to be added: %s\n", poiName);  ////Testing
                    if (POINameToObject.get(poiName)!=null)
                        currentUser.addFavoritePOI(POINameToObject.get(poiName));
                    else {
                        JOptionPane jOptionPane = new JOptionPane();
                        JOptionPane.showMessageDialog(null, "There is no POI named "+poiName, "POI not found",jOptionPane.ERROR_MESSAGE);
                    }
                    popupWindow.dispose();
                }
            }
            
        });
                
        popupWindow.getContentPane().add(searchPromptLabel);
        popupWindow.getContentPane().add(POISearchField);
        popupWindow.getContentPane().add(searchButton);
        
        // Display the pop-up window
        popupWindow.setVisible(true);
    }
    
    private void removeFavPOIPopUp(){
       JDialog popupWindow = new JDialog(this, "Remove Favorite POI Window", true);
        popupWindow.setLayout(null);   ///////
        popupWindow.setSize(600, 200);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(450, 800);
                
        // Create a new JLabel to prompt the user to enter the POI name
        JLabel searchPromptLabel = new JLabel("Enter the POI Name that you would like to remove (such as MC110):");
        searchPromptLabel.setBounds(30, 50, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField POISearchField = new JTextField(15);    //set length of the text  
        POISearchField.setBounds(60,90,200,25);
        
        // Create a new JButton to submit the input
        JButton searchButton = new JButton("Submit");
        searchButton.setBounds(320, 90, 80, 25); 
        searchButton.addActionListener(new ActionListener() {
            String poiName;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the POI
                poiName = POISearchField.getText();
                if (POINameToObject.get(poiName)!=null)
                        currentUser.deleteFavoritePOI(POINameToObject.get(poiName));
                    else {
                        JOptionPane jOptionPane = new JOptionPane();
                        JOptionPane.showMessageDialog(null, "There is no POI named "+poiName, "POI not found",jOptionPane.ERROR_MESSAGE);
                    }
                    popupWindow.dispose();
            }
            
        });
                
        popupWindow.getContentPane().add(searchPromptLabel);
        popupWindow.getContentPane().add(POISearchField);
        popupWindow.getContentPane().add(searchButton);
        
        // Display the pop-up window
        popupWindow.setVisible(true); 
    }
 
    // Add User-defined POIs
    private void addUserPOIPopUp(){
        
        //String poiName = null, poiType=null, poiBuilding=null, poiFloor=null;
        POI newPOI = new POI(null, null);
        //boolean flag=true;
        
        JDialog popupWindow = new JDialog(this, "Add User-defined POI Window", true);
        popupWindow.setLayout(null);   ///////
        popupWindow.setSize(800, 400);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(800, 800);
        
        
        // Allow the user to click on a POI to create a user-defined POI
        //imageLabel.addMouseListener(this); 
        
        // Create a new JLabel to prompt the user to enter the POI name
        JLabel namePromptLabel = new JLabel("Enter the POI name that you would like to add (such as MC110):");
        namePromptLabel.setBounds(30, 50, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField nameField = new JTextField(15);    //set length of the text  
        nameField.setBounds(450,50,200,25);
        
        // Create a new JLabel to prompt the user to enter the POI type
        JLabel typePromptLabel = new JLabel("Enter the POI type (such as Classroom):");
        typePromptLabel.setBounds(30, 80, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField typeField = new JTextField(15);    //set length of the text  
        typeField.setBounds(450,80,200,25);
        
        // Create a new JLabel to prompt the user to enter the building acronym
        JLabel buildingPromptLabel = new JLabel("Enter the building acronym (such as MC):");
        buildingPromptLabel.setBounds(30, 110, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField buildingField = new JTextField(15);    //set length of the text  
        buildingField.setBounds(450,110,200,25);

        // Create a new JLabel to prompt the user to enter the floor number
        JLabel floorPromptLabel = new JLabel("Enter the floor number (such as 1):");
        floorPromptLabel.setBounds(30, 150, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField floorField = new JTextField(15);    //set length of the text  
        floorField.setBounds(450,150,200,25);

        
        
        JLabel clickPromptLabel = new JLabel("Please click on a POI on the map after clicking the submit button:");
        clickPromptLabel.setBounds(30, 180, 500, 25);
        
        // Create a new JButton to submit the input
        JButton searchButton = new JButton("Submit");
        searchButton.setBounds(320, 210, 80, 25); 
        searchButton.addActionListener(new ActionListener() {
            String poiName, poiType, poiBuilding, poiFloor;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insert the POI
                poiName = nameField.getText();
                poiType = typeField.getText();
                poiBuilding = buildingField.getText();
                poiFloor = floorField.getText();
                if ((poiName!=null)&&(poiType!=null)) {
                    newPOI.setName(poiName);
                    newPOI.setType(poiType);
                    newPOI.setBuildingAcronym(poiBuilding);
                    newPOI.setFloorNum(Integer.parseInt(poiFloor));
                    System.out.printf("poiName to be added: %s\n", poiName);  ////Testing
                    currentUser.addUserDefinedPOI(newPOI);
                }
                else {
                    //flag = false;
                    JOptionPane jOptionPane = new JOptionPane();
                    JOptionPane.showMessageDialog(null, "POI name or type is empty", "UseaddUserDefinedPOIr Input Error",jOptionPane.ERROR_MESSAGE);
                } 
                popupWindow.dispose();
                
            }
            
        });
                
        popupWindow.getContentPane().add(namePromptLabel);
        popupWindow.getContentPane().add(nameField);
        popupWindow.getContentPane().add(typePromptLabel);
        popupWindow.getContentPane().add(typeField);
        popupWindow.getContentPane().add(clickPromptLabel);
        popupWindow.getContentPane().add(buildingPromptLabel);
        popupWindow.getContentPane().add(buildingField);
        popupWindow.getContentPane().add(floorPromptLabel);
        popupWindow.getContentPane().add(floorField);
        popupWindow.getContentPane().add(searchButton);
        
        // Display the pop-up window
        popupWindow.setVisible(true);
        
        // POI newPOI = new POI(poiName, poiType);
        // Allow the user to click on a POI to create a user-defined POI
        if (newPOI.getName()!=null) {
            imageLabel.addMouseListener(this);
            System.out.printf("x=%d y=%d\n",mouseClickedPoint.x, mouseClickedPoint.y);
            newPOI.setX(mouseClickedPoint.x);
            newPOI.setY(mouseClickedPoint.y);
        }
   
    }
    
    private void removeUserPOIPopUp(){
        JDialog popupWindow = new JDialog(this, "Remove Favorite POI Window", true);
        popupWindow.setLayout(null);   ///////
        popupWindow.setSize(600, 200);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(450, 800);
                
        // Create a new JLabel to prompt the user to enter the POI name
        JLabel searchPromptLabel = new JLabel("Enter the POI Name that you would like to remove (such as MC110):");
        searchPromptLabel.setBounds(30, 50, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField POISearchField = new JTextField(15);    //set length of the text  
        POISearchField.setBounds(60,90,200,25);
        
        // Create a new JButton to submit the input
        JButton searchButton = new JButton("Submit");
        searchButton.setBounds(320, 90, 80, 25); 
        searchButton.addActionListener(new ActionListener() {
            String poiName;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the POI
                poiName = POISearchField.getText();
                if (currentUser.searchUserDefinedPOI(poiName))
                        currentUser.removeUserDefinedPOI(poiName);
                    else {
                        JOptionPane jOptionPane = new JOptionPane();
                        JOptionPane.showMessageDialog(null, "There is no user-defined POI named "+poiName, "POI not found",jOptionPane.ERROR_MESSAGE);
                    }
                    popupWindow.dispose();
            }
            
        });
                
        popupWindow.getContentPane().add(searchPromptLabel);
        popupWindow.getContentPane().add(POISearchField);
        popupWindow.getContentPane().add(searchButton);
        
        // Display the pop-up window
        popupWindow.setVisible(true); 
    }

    private void showUserPOIPopUp() {
        JDialog popupWindow = new JDialog(this, "Show User-defined POIs Window", true);
        popupWindow.setLayout(new GridLayout(10,2));   ///////
        popupWindow.setSize(200, 400);
        //buttonPanel = new JPanel(new GridLayout(10,2));

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(300, 400);
        
        for (int i=0; i<currentUser.getNumUserDefinedPOIs(); i++) {
            final int index = i;  //This is needed because i is not accessible in the ActionListerner clock below. A "final" variable is
            JButton poiButton = new JButton(currentUser.getUserDefinedPOI(index).getName());   /////
            
            poiButton.addActionListener(event -> {
                showUserDefinedPOIInfo(index);    
            });
            popupWindow.getContentPane().add(poiButton);
        }
        //popupWindow.add(layerPanel, BorderLayout.EAST);
        //pack();
        popupWindow.setVisible(true);
        //revalidate();
        //repaint();
    }
    
    private void showUserDefinedPOIInfo(int i) {
        POI p = currentUser.getUserDefinedPOI(i);
        displayPOIInfoWithFloorMap(p);
        JDialog popupWindow = new JDialog(this, p.getName(), true);
        popupWindow.setSize(910, 100);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(500, 400);
        

        // Add a JLabel to the pop-up window
        //JLabel label = new JLabel(b.getDes());
        String jText = ("  POI name: "+p.getName()+"\n"
                        + "  Room number: "+p.getNum()+"\n"
                        + "  POI type: "+p.getType()+"\n"
                        + "  POI Description: "+p.getDescription());
        JTextArea textArea = new JTextArea(jText);
        popupWindow.getContentPane().add(textArea);

        // Display the pop-up window
        popupWindow.setVisible(true);
    }
    
    
    private void showFavPOIPopUp() {
        JDialog popupWindow = new JDialog(this, "Show Favorite POIs Window", true);
        popupWindow.setLayout(new GridLayout(10,2));   ///////
        popupWindow.setSize(200, 400);
        //buttonPanel = new JPanel(new GridLayout(10,2));

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(300, 400);
        
        for (int i=0; i<currentUser.getNumFavoritePOIs(); i++) {
            final int index = i;  //This is needed because i is not accessible in the ActionListerner clock below. A "final" variable is
            JButton poiButton = new JButton(currentUser.getFavoritePOI(index).getName());   /////
            
            poiButton.addActionListener(event -> {
                showFavoritePOIInfo(index);    
            });
            popupWindow.getContentPane().add(poiButton);
        }
        //popupWindow.add(layerPanel, BorderLayout.EAST);
        //pack();
        popupWindow.setVisible(true);
        //revalidate();
        //repaint();
    }
    
    private void showFavoritePOIInfo(int i) {
        POI p = currentUser.getFavoritePOI(i);
        displayPOIInfoWithFloorMap(p);

        JDialog popupWindow = new JDialog(this, p.getName(), true);
        popupWindow.setSize(910, 100);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(500, 400);
        

        // Add a JLabel to the pop-up window
        //JLabel label = new JLabel(b.getDes());
        String jText = ("  POI name: "+p.getName()+"\n"
                        + "  Room number: "+p.getNum()+"\n"
                        + "  POI type: "+p.getType()+"\n"
                        + "  POI Description: "+p.getDescription());
        JTextArea textArea = new JTextArea(jText);
        popupWindow.getContentPane().add(textArea);

        // Display the pop-up window
        popupWindow.setVisible(true);
    }
    
    private POI addBuiltInPOI(){
        
        //imageLabel.addMouseListener(this);
        
        //String poiName = null, poiType=null, poiBuilding=null, poiFloor=null;
        POI newPOI = new POI(null, null);
        //boolean flag=true;
        
        JDialog popupWindow = new JDialog(this, "Add User-defined POI Window", true);
        popupWindow.setLayout(null);   ///////
        popupWindow.setSize(800, 400);

        // Set the location of the pop-up window relative to the main window
        //popupWindow.setLocationRelativeTo(this);
        popupWindow.setLocation(800, 800);
        
        
        // Allow the user to click on a POI to create a user-defined POI
        //imageLabel.addMouseListener(this); 
        
        // Create a new JLabel to prompt the user to enter the POI name
        JLabel namePromptLabel = new JLabel("Enter the POI name that you would like to add (such as MC110):");
        namePromptLabel.setBounds(30, 50, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField nameField = new JTextField(15);    //set length of the text  
        nameField.setBounds(450,50,200,25);
        
        // Create a new JLabel to prompt the user to enter the POI type
        JLabel typePromptLabel = new JLabel("Enter the POI type (such as Classroom):");
        typePromptLabel.setBounds(30, 80, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField typeField = new JTextField(15);    //set length of the text  
        typeField.setBounds(450,80,200,25);
        
        // Create a new JLabel to prompt the user to enter the building acronym
        JLabel buildingPromptLabel = new JLabel("Enter the building acronym (such as MC):");
        buildingPromptLabel.setBounds(30, 110, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField buildingField = new JTextField(15);    //set length of the text  
        buildingField.setBounds(450,110,200,25);

        // Create a new JLabel to prompt the user to enter the floor number
        JLabel floorPromptLabel = new JLabel("Enter the floor number (such as 1):");
        floorPromptLabel.setBounds(30, 150, 450, 25);
        
        // Create text ﬁeld to get user's input 
        JTextField floorField = new JTextField(15);    //set length of the text  
        floorField.setBounds(450,150,200,25);

        
        JLabel clickPromptLabel = new JLabel("Please click on a POI on the map after clicking the submit button:");
        clickPromptLabel.setBounds(30, 180, 500, 25);
        
        // Create a new JButton to submit the input
        JButton searchButton = new JButton("Submit");
        searchButton.setBounds(320, 210, 80, 25); 
        searchButton.addActionListener(new ActionListener() {
            String poiName, poiType, poiBuilding, poiFloor;
            Building b;
            Floor f;
            Layer layer;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insert the POI
                poiName = nameField.getText();
                poiType = typeField.getText();
                poiBuilding = buildingField.getText();
                poiFloor = floorField.getText();
                if ((poiName!=null)&&(poiType!=null)) {
                    newPOI.setName(poiName);
                    newPOI.setType(poiType);
                    newPOI.setBuildingAcronym(poiBuilding);
                    newPOI.setFloorNum(Integer.parseInt(poiFloor));
                    System.out.printf("poiName to be added: %s\n", poiName);  ////Testing
                    
                    for (int i=0; i<buildings.size(); i++) {
                        if (poiBuilding.equals(buildings.get(i).getBuildingAcronym()))
                            b=buildings.get(i);
                    }
                    f = b.getFloors().get(Integer.parseInt(poiFloor));
                    
                    int j;                    
                    for (j=0; j<f.getNumLayers(); j++) {
                        if (poiType.equals(f.getLayer(j).getName())) {
                            layer = f.getLayer(j);
                            break;
                        }    
                    }
                    if (j<f.getNumLayers()) {
                        System.out.printf("Before: Number of classrooms= %d\n", layer.getNumPOIs());
                        layer.addPOI(newPOI);
                        System.out.printf("After: Number of classrooms= %d\n", layer.getNumPOIs());
                    }
                    else {
                        Layer newlayer = new Layer(poiType);
                        newlayer.addPOI(newPOI);
                        f.addLayer(newlayer);
                    } 
                        
                    //currentUser.addUserDefinedPOI(newPOI);
                }
                else {
                    //flag = false;
                    JOptionPane jOptionPane = new JOptionPane();
                    JOptionPane.showMessageDialog(null, "POI name or type is empty", "UseaddUserDefinedPOIr Input Error",jOptionPane.ERROR_MESSAGE);
                } 
                popupWindow.dispose();
                
            }
            
        });
                
        popupWindow.getContentPane().add(namePromptLabel);
        popupWindow.getContentPane().add(nameField);
        popupWindow.getContentPane().add(typePromptLabel);
        popupWindow.getContentPane().add(typeField);
        popupWindow.getContentPane().add(clickPromptLabel);
        popupWindow.getContentPane().add(buildingPromptLabel);
        popupWindow.getContentPane().add(buildingField);
        popupWindow.getContentPane().add(floorPromptLabel);
        popupWindow.getContentPane().add(floorField);
        popupWindow.getContentPane().add(searchButton);
        
        // Display the pop-up window
        popupWindow.setVisible(true);
        
        // POI newPOI = new POI(poiName, poiType);
        // Allow the user to click on a POI to create a user-defined POI
        if (newPOI.getName()!=null) {
            imageLabel.addMouseListener(this);
            //System.out.printf("Inside addBuiltInPOI: mouseClickedPoint.x=%d mouseClickedPoint.y=%d\n",mouseClickedPoint.x, mouseClickedPoint.y);
            //System.out.printf("New POI name=%s\n", newPOI.getName());
            newPOI.setX(mouseClickedPoint.x);
            newPOI.setY(mouseClickedPoint.y);
            //System.out.printf("newPOI.x=%d newPOI.y=%d\n",newPOI.getX(), newPOI.getY());
            //newlyCreatedPOI = newPOI;
            return(newPOI);
        }
        return null;
    }
    //editBuiltInPOI();
    
}