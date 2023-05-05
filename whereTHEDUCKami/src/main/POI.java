/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.wheretheduckami;

/**
 *
 * @author Ellen
 */
public class POI {
    private String POIname;
    private String POINum;
    //private int POInum;
    private boolean highlight;
    private String description;
    private String type;
    //private Point location;
    private int x;
    private int y;
    String buildingAcronym; // Acronym of the building the POI is in
    int floorNum; // the floor the POI is on
    
    /**
     * Constructor creates instance of POI.
     * @param name
     * @param type 
     */
    public POI(String name, String type) {  
      this.POIname = name;
      this.POINum = null;
      this.type = type;
      this.highlight = false;
      this.x = 0;
      this.y = 0;
    }
    
    /**
     * Constructor creates instance of POI.
     * @param name
     * @param roomNum
     * @param type 
     */
    public POI(String name, String roomNum, String type) {  
      this.POIname = name;
      this.POINum = roomNum;
      this.type = type;
      this.highlight = false;
      this.x = 0;
      this.y = 0;
  }
  
    /**
     * Constructor creates instance of POI.
     * @param name
     * @param roomNum
     * @param type
     * @param x
     * @param y
     * @param ba
     * @param fnum 
     */
  public POI(String name, String roomNum, String type, int x, int y, String ba, int fnum) {  
      this.POIname = name;
      this.POINum = roomNum;
      this.type = type;
      this.highlight = false;
      this.x = x;
      this.y = y;
      this.buildingAcronym = ba;
      this.floorNum = fnum;
  }

  /**
   * Constructor creates instance of POI.
   * @param name
   * @param roomNum
   * @param type
   * @param bAcronym
   * @param fNum 
   */
  public POI(String name, String roomNum, String type, String bAcronym, int fNum) {  
      this.POIname = name;
      this.POINum = roomNum;
      this.type = type;
      this.highlight = false;
      this.buildingAcronym = bAcronym;
      this.floorNum = fNum;
      this.x = 0;
      this.y = 0;
  }
  
  /**
   * Constructor creates instance of POI.
   * @param POIname
   * @param roomNum
   * @param type
   * @param highlight
   * @param description
   * @param x
   * @param y 
   */
  public POI(String POIname, String roomNum, String type, boolean highlight, String description, int x, int y) { 
    this.POIname = POIname;
    this.POINum = roomNum;
    this.highlight = highlight;
    this.description = description;
    this.type = type;
    //this.location = location;
    this.x = x;
    this.y = y;
  }
  
  /**
   * Sets name of poi
   * @param name 
   */
  public void setName(String name) {
      this.POIname = name;
  }
  
  /**
   * Gets name of poi
   * @return 
   */
  public String getName() {
    return this.POIname;
  }
  
  /**
   * 
   * @param num 
   */
  public void setNum(String num) {
      this.POINum = num;
  }
  
  /**
   * 
   * @return POINum
   */
  public String getNum() {
    return this.POINum;
  } 
  
  /**
   * 
   * @return highlight
   */
  public boolean getHI() {
      return this.highlight;
  }
  
  /**
   * 
   * @return description 
   */
  public String getDescription() {
      return this.description;
  }
  
  /**
   * 
   * @param descrip 
   */
  public void setDescription(String descrip) {   //////
      this.description = descrip;
  }
  
  /**
   * 
   * @return type
   */
  public String getType() {
      return this.type;
  }
  
  /**
   * 
   * @param type 
   */
  public void setType(String type) {   //////
      this.type = type;
  }
  
  /**
   * 
   * @return x
   */
  public int getX() {
      return this.x;
  }
  
  /**
   * 
   * @param x 
   */
  public void setX(int x){
      this.x = x;
  }
  
  /**
   * 
   * @return y
   */
  public int getY() {
      return this.y;
  }
  
  /**
   * 
   * @param y 
   */
  public void setY(int y){
      this.y = y;
  } 
  
  /**
   * 
   * @param x
   * @param y 
   */
  public void setXY(int x, int y){
      this.x = x;
      this.y=y;
  }
  
  /**
   * 
   * @return 
   */
  public String getBuildingAcronym(){
      return this.buildingAcronym;
  }
  
  /**
   * 
   * @param ba 
   */
  public void setBuildingAcronym(String ba){
      this.buildingAcronym = ba;
  }
  
  /**
   * 
   * @return floorNum
   */
  public int getFloorNum(){
      return this.floorNum;
  }
  
  /**
   * 
   * @param fn 
   */
  public void setFloorNum(int fn){
      this.floorNum = fn;
  }
  
  /*
  public int[] getLocation() {
    int[] location = new int[2];
    location[0] = this.x;
    location[1] = this.y;
    return location;
  }
  */
  
  /*
  public Point getLocation() {
    return this.location;
  }
  /*
  public void setLocation(Point p) {
      this.location = p;
  }
  */

}