/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.HashMap;

/**
 * This class acts as a model to load the data from the metadata JSON file and 
 * construct system components (Campus Map, Building, Floor, POI) the application 
 * needs. It also updates the data in the map JSON file when a user modifies a 
 * system component. It will be used by the controllers to handle users 
 * interactions with the views.
* <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class MapData2 {
    
    /** An instance of campusMap */
    private CampusMap westernMap;
    
    /** An instance of fileHandler */
    private FileHandler dataFile;
    
    /** Represents the name of map currently displayed */
    String mapName;
    
    /** Represents the floor number currently displayed */
    int floorNumber;
    
    /** 
     * Constructor of an instance of MapData
     * @param filename the name of the metadata JSON file
     */
    public MapData2(String filename, String mapName){
    
       dataFile = new FileHandler(filename);
       
       westernMap = dataFile.getCampusData();
       
       this.mapName = mapName;
       
       this.floorNumber = 0;
    
    }
    
    /** 
     * Sets a MapData object using the provided FileHandler and Campus Map
     * @param file the FileHandler object of MapData
     * @param map the CampusMap object of MapData
     */
    public void setCampusMap(FileHandler file, CampusMap map){
    
        this.dataFile = file;
        
        this.westernMap = map;
    }
    
    /** 
     * Returns the CampusMap object of MapData
     * @return CampusMap the Campus Map object
    */
    public CampusMap getMap(){
        return this.westernMap;
    }
    
    /** 
     * Returns the FileHandler object of MapData
     * @return FileHandler the FileHandler object
    */
    public FileHandler getFile(){
        return this.dataFile;
    }
    
    /** 
     * Sets the current map name
     * @param name the name of the map
    */
    public void setMapName(String name){
    
        this.mapName = name;
    
    }
    
    /** 
     * Returns the Name of the current map
     * @return the String name of the current map
    */
    public String getMapName(){
    
        return this.mapName;
    }
    
    /** 
     * Sets the current floor number
     * @param num the floor number
    */
    public void setFloorNum(int num){
    
        this.floorNumber = num;
    
    }
    
    /** 
     * Sets the current floor number
     * @return the floor number
    */
    public int getFloorNum(){
    
        return this.floorNumber;
    
    }
}
