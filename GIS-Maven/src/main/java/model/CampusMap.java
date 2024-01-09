/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
* <h1>CampusMap</h1>
*This class defines a campus map with a map file to display
*it on the application and
*a list of buildings to manage the building inside the campus
*
* @author  Thai
* @version 1
* @since   2022-11-11
*/
public class CampusMap {
    
    /**The name of the file which contains the map, the .png file*/
    private String mapFile;
    
    /** The list of buildings on the map */
    private ArrayList<Building> buildingList;
    
    /** The points of interest on the campus map */
    private ArrayList<POI> POIList;
   
    /**
    * Constructor for the class Campus Map
    *
    *@param file the .png name of the picture
    */
    public CampusMap(String file){
     
        this.mapFile = file;
        
        this.POIList = new ArrayList<>();
        
        this.buildingList = new ArrayList<>();
  
    }
    
    /**
    *Constructor for the class Campus Map
    *@param POIList the list of POIs inside of the building
    *@param bldgList the list of buildings inside of the building
    *@param file the .png name of the picture
    */
    public CampusMap(String file, ArrayList<POI> POIList, ArrayList<Building> bldgList){
     
        this.mapFile = file;
        
        this.POIList = POIList;
        
        this.buildingList = bldgList;
 
    }
    
   /**
    *Adds a  building
    *@param building the building to add
    * @return true if the building is added, false else
    */
    public Boolean addBuilding(Building building){
        
        if(building != null){
            this.buildingList.add(building);
            return true;
        }
        return false;
    }
    
    
    /**
    *Return a building object
    *@return the building object
    */
    public ArrayList<Building> getBuilding(){
        return this.buildingList;
    }
    
    /**
    *Returns the list of POIs inside the campus map
    *@return the list of POIs
    */
    public ArrayList<POI> getPOIs(){
        return this.POIList;
    }
    
    /**
    *Returns map file of the campus map
    *@return the map file
    */
    public String getMapFile(){
    
        return this.mapFile;
    
    }
    
    /**
    *Returns the building at a specific index in the list of building
    *@param index the index of the building to return
    *@return the building looked for
    * @throws EmpytListException if the list of building is empty
    */
    public Building getBuilding(int index) throws EmpytListException{
    
        if(!this.buildingList.isEmpty()){
            return this.buildingList.get(index);
        }
        else{
        
            throw new EmpytListException("List of Building is empty");
        
        }
    }
}
