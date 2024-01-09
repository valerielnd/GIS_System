/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author valer
 */
public class MapData {
    
    private String mapFile;
    
    private String mapName;
    
    private CampusMap westernMap;
    
    public MapData(String file, String name){
    
        this.mapFile = file;
        
        this.mapName = name;
        
        westernMap = new CampusMap(file);
        
        Building mCollege = new Building("Middle Sex College",new Vec2(3,2),"MC college");
        
        Floor floorMC1 = new Floor("Middle Sex College",1,"AFAR-BF-1.png");
        
        POI POI1 = new POI("Middle Sex College",1,"POI1",1,"First POI",true,new Vec2(4,5));
        
        floorMC1.addPOI(POI1);
        
        mCollege.addFloor(floorMC1);
        
        westernMap.addBuilding(mCollege);
    
    }
    
    public void setMapFile(String mapfile){
        this.mapFile = mapfile;
    }
    
    public void setMapName(String name){
        this.mapName = name;
    }
    public String getMapFile(){
     return this.mapFile;
    }
    public String getMapName(){
     return this.mapName;
    }
    
    public CampusMap getMap(){
        return this.westernMap;
    }
}

