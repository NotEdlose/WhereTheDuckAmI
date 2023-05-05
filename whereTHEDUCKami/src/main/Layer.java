/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.wheretheduckami;

/**
 *
 * @author Ellen
 */

import java.util.ArrayList;
import java.util.List;
public class Layer {
    private String name;
    private List<POI> setOfPOI ; // previously protected final
    private boolean highLightStatus;
    private int numPOIs;
    
    public Layer(String name) {
        this.name = name;
        this.highLightStatus = false; //off by default
        this.setOfPOI = new ArrayList<>();
        this.numPOIs = 0;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void updateName(String name) {
        this.name = name;
    }
    
    public void setButton(boolean layerButton) {   ///// should remove if not needed
       
    }
    
    public void displayLayers(){
        
    }
    
    public void addPOI(POI poiAdd){
        this.setOfPOI.add(poiAdd);
        this.numPOIs++;
        
    }
    public void deletePOI(POI poiDelete){
        this.setOfPOI.remove(poiDelete);
        this.numPOIs--;
    }
    
    /* Get the ith POI */
    public POI getPOI(int i) {
        return this.setOfPOI.get(i);
    }
    public void updatePOI(){
        
    }
    
    public int getNumPOIs() {
        return this.numPOIs;
    }
    
}