/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.wheretheduckami;

/**
 *
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

public class Floor {
    private int floorNum;
    private String description;
    private List<Layer> layers;
    private int numLayers;
    private String fileName;
    
    public Floor (int floorNum, String description) {
        this.floorNum = floorNum;
        this.description = description;
        this.numLayers = 0;
        this.layers = new ArrayList<>();
    }
    
    public void setFloorNumber(int floorNum) {
        this.floorNum = floorNum;
    }
    
    public int getFloorNumber() {
        return this.floorNum;
    }
    
    public void updateFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }
    
    public void setdescrip(String descrip) {
        this.description = descrip;
    }
    
    public void updateDescrip(String descrip) {
        this.description = descrip;
    }
    
    public String getDescrip() {
        return this.description;
    }
    
    public void setNumLayers(int n) {
        this.numLayers = n;
    }
    
    public int getNumLayers() {
        return this.numLayers;
    }
    
    public void addLayer(Layer layer) {
        this.layers.add(layer);
        this.numLayers++;
    }
    
    /* get ith layer from the list of layers */
    public Layer getLayer(int i) {
        return layers.get(i);
    }
    
    /* Delete ith layer */
    public void deleteLayer(int i) {
        for (int j=i; j<numLayers-1; j++) {
            layers.set(j, layers.get(j+1));
        }
        this.numLayers--;
                
    }
    
    public void setFileName(String name) {
        this.fileName = name;
    }
    
    public String getFileName() {   /////
        return this.fileName;
    }
    
}