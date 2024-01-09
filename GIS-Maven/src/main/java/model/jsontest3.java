/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.*;

public class jsontest3 {

	public static void main(String[] args) {
            
               //try{
		
		FileHandler wrkJSON = new FileHandler("/resources/map6.json");
		
		CampusMap map = wrkJSON.getCampusData();
                
                ArrayList<Building> bldgList = map.getBuilding();
                
                for(int i=0; i< bldgList.size();i++){
                
                    System.out.println(bldgList.get(i).getName());
                
                }
                
               // String bldgName = "Alumni Hall";
                
                int floorNum = 0;
                
                //POI oldPOI = new POI("Alumni Hall", 0, "AH_0_WA_12", 3,"Men's Washroom in first floor AH", false, new Vec2(1838,1454));
                
                //POI newPOI = new POI("Alumni Hall", 0, "AH_0_WA_12", 4,"Men's Washroom in first floor AH", false, new Vec2(1838,1454));
                
                POI oldPOI = new POI("Test Building3", 0, "test_0_B_test", 0,"test", false, new Vec2(2821,2506));
                
                //POI newPOI = new POI("Alumni Hall", 0, "AH_0_WA_12", 4,"Men's Washroom in first floor AH", false, new Vec2(1838,1454));
                        
                //wrkJSON.addBuiltInPOI(bldgName, floorNum, oldPOI);
                
                Vec2 location = new Vec2(2161,4787);
                
                Vec2 location2 = new Vec2(2161,4788);
                
                ArrayList<Floor> floorList = new ArrayList<>();
                
         
             
                Floor oldFloor = new Floor("Test Building 5", 1, "AH2.png");
                Floor newFloor = new Floor("Test Building 5", 0, "AH1.png");
                
                //newFloor.addPOI(oldPOI);
                
                //floorList.add(newFloor);
                
               // Building newBldg  = new Building("Test Building3", location2, "Test Building3",floorList);
                
                //Building oldBldg  = new Building("Test Building3", location, "Test Building3",floorList);
                
                wrkJSON.editFloor(oldFloor,newFloor,"Test Building 5");
                
                }

                
               
        
        
                 
        }
        
        
       


