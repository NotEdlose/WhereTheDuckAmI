/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.wheretheduckami;

/**
 *
 * @author Ellen
 */

//import required classes and packages 
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
import java.lang.Exception;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

//create CreateLoginForm class to create login form  
//class extends JFrame to create a window where our component add  
//class implements ActionListener to perform an action on button click 
public class CreateLoginForm extends JFrame implements ActionListener {
    // Declare button, panel, label, and text ﬁeld  
    JButton loginButton,signupButton;
    JPanel newPanel;
    JLabel userLabel, passLabel, errorLabel, welcomeMessage, imageLabel;
    JTextField textField1, textField2;                  ////// remove "final" from the beginning
    //User user = new User(null,null);
    //User currentUser;
    ArrayList<Building> buildings;
    ArrayList<User> users;
    int numBuildings;
    //User currentUser;
    int numUsers;
    HashMap<String, POI> POINameToObject;
    HashMap<String, Building> BuildingAcronymToObjectMap;
   
    JSONArray arr = new JSONArray();
    ImageIcon westernImage;
    
    //calling constructor
    public CreateLoginForm(ArrayList<Building> buildings, ArrayList<User> users, int numBuildings, int numUsers, HashMap<String, POI> POINameToObject, HashMap<String, Building> BuildingAcronymToObjectMap) {     
        this.buildings = buildings; //pass the list of users from the main program so that it can be passed to UserInterface()
        this.users = users; // pass the list of users from the main program so that it can be passed to UserInterface()
        this.numBuildings = numBuildings;
        this.numUsers = numUsers;
        this.POINameToObject = POINameToObject;
        this.BuildingAcronymToObjectMap = BuildingAcronymToObjectMap;
        
        //create label for username 
        welcomeMessage = new JLabel();
        welcomeMessage.setText("Find out where THE DUCK you are!");
        welcomeMessage.setBounds(10,10,600,50); /////
        welcomeMessage.setFont(new Font("Serif", Font.PLAIN, 22));
        welcomeMessage.setForeground(Color.BLUE);
        
        //creates error label
        errorLabel = new JLabel();  
        errorLabel.setText("*Please enter valid username and password");      //set label value for textField1  
        errorLabel.setBounds(10,105,500,25); /////
        errorLabel.setForeground(Color.RED);
          
        //create label for username   
        userLabel = new JLabel();  
        userLabel.setText("UWO Email");      //set label value for textField1  
        userLabel.setBounds(10,70,80,25); /////
        
        //create text ﬁeld to get username from the user  
        textField1 = new JTextField(15);    //set length of the text  
        textField1.setBounds(100,70,185,25);  //////
        
        //create label for password  
        passLabel = new JLabel();  
        passLabel.setText("Password");      //set label value for textField2  
        passLabel.setBounds(10,100,80,25); ////
        
        //create text ﬁeld to get password from the user  
        textField2 = new JPasswordField(15);    //set length for the password  
        textField2.setBounds(100,100,185,25);  ////
        
        //create submit button  
        loginButton = new JButton("Login"); //set label to button  
        loginButton.setBounds(10,150,100,25); //////
        
        signupButton = new JButton("Sign-up");
        signupButton.setBounds(150,150,100,25);
        
        // Create an image label
        westernImage = new ImageIcon("WesternImage.jpg");
        //westernImage.setImage(westernImage.getImage().getScaledInstance(460,200, Image.SCALE_DEFAULT));
        //imageLabel = new JLabel(westernImage);
        imageLabel = new JLabel();
        imageLabel.setIcon(westernImage);
        imageLabel.setBounds(150,200,580,240);
        
        //create panel to put form elements  
        //newPanel = new JPanel(new GridLayout(3, 2)); 
        newPanel = new JPanel();
        newPanel.setLayout(null);
        
        newPanel.add(welcomeMessage); // Show the welcome message
        newPanel.add(errorLabel);
        newPanel.add(userLabel);    //set username label to panel  
        newPanel.add(textField1);   //set text ﬁeld to panel  
        newPanel.add(passLabel);    //set password label to panel  
        newPanel.add(textField2);   //set text ﬁeld to panel  
        newPanel.add(loginButton);  //set button to panel  
        newPanel.add(signupButton);//set button to panel
        newPanel.add(imageLabel);  // Add the Western image to the panel
          
        errorLabel.setVisible(false);
        
        //set border to panel   
        add(newPanel, BorderLayout.CENTER);  
        
        //perform action on button click   
        loginButton.addActionListener(this);     //add action listener to button  
        signupButton.addActionListener(this);
        setTitle("whereTHEDUCKami - an Interactive Map of Western University");         //set title
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                      ////////////////////////////////////
    }  
      
    //deﬁne abstract method actionPerformed() which will be called on button click   
    public void actionPerformed(ActionEvent ae) {     //pass action listener as a parameter 
        
        Object ob = null;
        JSONParser Jp = new JSONParser();
        ImageIcon westernMap;
        JLabel mapLabel;
        
        //fetch ﬁle--
        try{
            FileReader ﬁle = new FileReader("UserData.json");
            ob=Jp.parse(ﬁle);
            arr=(JSONArray) ob;
            ﬁle.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error Occured While fetching");
        }
        
        /*Create a hashmap for username and password pairs */        
        HashMap<String, String> userToPwdMap = new HashMap<String, String>();
        JSONObject jsonObj = new JSONObject();
        for (int i=0; i<arr.size(); i++) {
            jsonObj=(JSONObject)arr.get(i);
            userToPwdMap.put((String)jsonObj.get("Username"), (String)jsonObj.get("Password")); // put username and password into the hashmap 
        }
        
        /* For testing purpose. Can be deleted later */
        for (HashMap.Entry<String, String> entry : userToPwdMap.entrySet()) {
            System.out.println(entry.getKey()+": "+entry.getValue());
        }
        
        /* Get the username and password from the user input */
        String userName = textField1.getText();        //get user entered username from the textField1  
        String passWord = textField2.getText();        //get user entered pasword from the textField2
        
        int size = arr.size();
        errorLabel.setBounds(10,125,500,25);
        
        //check whether the credentials are authentic or not  
        if (ae.getSource() == loginButton){
            if (passWord.equals(userToPwdMap.get(userName))) {
                
                /* This does not show on the screen. But keep it for now */
                errorLabel.setForeground(Color.blue);
                errorLabel.setText("*User was found. Logging in...");
                errorLabel.setVisible(true);
                // Close the login window
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                
                User currentUser = getCurrentUser(userName);
                
                JFrame frame = new JFrame("User Interface");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new UserInterface(buildings, users, numBuildings, currentUser, POINameToObject, BuildingAcronymToObjectMap));    //// add a currentuser as parameter
                //frame.getContentPane().add(new UserInterface());    //// add a currentuser as parameter
                frame.pack();
                frame.setVisible(true);
                
            }
            else {
                errorLabel.setForeground(Color.red);
                errorLabel.setText("*Username was not found or Password does not match");
                errorLabel.setVisible(true);
            } 
        }
        else if (ae.getSource() == signupButton){
            JSONObject obj = new JSONObject();
            if (!userName.isEmpty()){
                if (!passWord.isEmpty()){
                    if (passWord.equals(userToPwdMap.get(userName))) {
                        errorLabel.setForeground(Color.red);
                        errorLabel.setText("*User has already been created");
                        errorLabel.setVisible(true);
                    }
                    else  {                        
                        this.numUsers++;
                        User newUser = new User(userName, passWord, "Normal");   /////////////////////////
                        this.users.add(newUser);
                        
                        obj.put("Username",userName);
                        obj.put("Password",passWord);
                        obj.put("Type", "Normal");    ///////////////////////////
                   
                        arr.add(obj);
                        //arr.add(obj);
                        
                        try {
                            FileWriter ﬁle = new FileWriter("UserData.json");
                            ﬁle.write(arr.toJSONString());
                            ﬁle.close();
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(null,"Error occured");
                        }
                        
                        //show creation success message  
                        errorLabel.setForeground(Color.green);
                        errorLabel.setText("Your account is created. You can log in now.");
                        errorLabel.setVisible(true);
                        
                        newUser.logout(); // This is to create the json file so that the file exist next time the user logs in.
                        
                    }
                }
            }
            else{
                //show error message  
                errorLabel.setText("*Please enter valid username and password");
                errorLabel.setVisible(true);
            }
        }
    }
    
    private User getCurrentUser(String userName) {
        for (int i=0; i<users.size(); i++) {
            if (userName.equals(users.get(i).getUserName()))
                return users.get(i);  // return the user object that has the input userName
        }
        return null;
    } 
    
    public int updateNumUsers() {
        return this.numUsers;
    }
    
}