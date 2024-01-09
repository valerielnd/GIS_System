/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;

/**
 * Provides methods to read, modify the user and map metadata JSON files using the JSON-Java library
 * <br><br>
 *@author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 2.0
 */
public class FileHandler {
	
        /** The JSON file name */
	private String filename;
	
        /**
         * Constructs an object fileHandler
         * @param file the name of the JSON file
         */
	public FileHandler(String file) {
		
		this.filename = file;
	}

        /**
         * Gets the current user data from the user JSN file
         * Finds the user using the user email
         * @param email the email of the user to find
         * @return the User object with all the user data
         * @throws MetadataFileException if an error occur while getting the user data
         */
	public User getUserData(String email)throws MetadataFileException{
		
		JSONArray userArray = loadUserData(this.filename);
		
		JSONObject nextUser;
		
		User currentUser = null;  
		
		try {
			for (Object user: userArray) {
	            
                            nextUser  = (JSONObject) user;

                            if (nextUser.getString("email").equals(email)) {

                                String userEmail = nextUser.getString("email");

                                String userPassword = nextUser.getString("password");

                                String userSecQuestion = nextUser.getString("Question");

                                String userSecAnswer = nextUser.getString("Answer");

                                int userType = nextUser.getInt("Developer");

                                JSONArray POIList = nextUser.getJSONArray("POIList");

                                ArrayList<POI> userPOIs = getPOIList(POIList);

                                currentUser = new User(userEmail, userPassword, userSecQuestion, userSecAnswer, userType);

                                currentUser.setPOIList(userPOIs);
                            } 
	            
                        }
			
                        return currentUser;
		}
		catch(Exception e) {
			
			throw new MetadataFileException("Error while reading the user from the usr JSON file");
		}
		
	}
	
	/**
         * Saves the new user information to the JSON user file
         * @param newUser the user which information to save
         * @return true if the information was saved else false
         * @throws MetadataFileException if an error occur while saving the user information
         */
	public Boolean AddUserTofile(User newUser) throws MetadataFileException{
		
		Boolean result = false;
		
		JSONObject userToAdd = createUserObject(newUser);
		
		JSONArray users = loadUserData(this.filename);
                
                try{
		
                    if(!UserExist(newUser)) {

                        users.put(userToAdd);
                        
                        result = saveJSONArray(users,this.filename);

                    }
                    return result;
                }
                catch(Exception e){
                
                    throw new MetadataFileException("Error while saving the new user to the user JSON file");
                
                }
		
	}
	
	/**
         * Updates a user information in the JSON user file
         * @param oldUser the user object containing the user old information
         * @param newUser the user object containing the user new information
         * @return true if the new information was saved else false
         * @throws MetadataFileException if an error occur while updating the user information
         */
	public Boolean UpdateUser(User oldUser, User newUser) throws MetadataFileException{
            
		Boolean result = false;
                
                try{
             
                    JSONObject userToAdd = createUserObject(newUser);

                    JSONArray users = loadUserData(this.filename);

                    int userPosition = getUserPos(oldUser,users);

                    if((userPosition >= 0) && (userPosition < users.length())) {

                            users.put(userPosition, userToAdd);

                            

                            result = saveJSONArray(users,this.filename);

                            return result;

                    }
                    return result;
                }
                catch(Exception e){
                        
                      throw new MetadataFileException("Error while updating the user information in the user JSON file");   
                        
                }
	}
        
        /**
         * Updates a POI information in the metadata JSON file
         * @param bldgName the name of the building where the POI is located
         * @param floorNum the floor number where the POI is located
         * @param oldPOI the POI object to update
         * @param newPOI the POI object with the new information
         * @return true if the POI information was updated or false else
         * @throws MetadataFileException if an error occur while updating the POI information
         */
        public Boolean updateBuiltInPOI(String bldgName, int floorNum, POI oldPOI, POI newPOI)throws MetadataFileException{
            
            Boolean result = false;
        
            try{
                JSONObject POItoUpdate = createPOIObject(newPOI);

                JSONObject campusInfo = loadMapData(this.filename); 

                JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");

                int buildingPosition = getBldgPos(bldgName, campusBuildings);

                if(buildingPosition >= 0){

                    JSONObject building = campusBuildings.getJSONObject(buildingPosition);

                    JSONArray floors = building.getJSONArray("FloorList");

                    int floorPosition = getFloorPos(floorNum, floors);

                    if(floorPosition >= 0){

                        JSONObject floor = floors.getJSONObject(floorPosition);

                        JSONArray POIs = floor.getJSONArray("POIList");

                        floor.remove("POIList");

                        int POIPosition = getPOIPos(oldPOI.getPOIName(),POIs);

                        if(POIPosition >= 0){

                            POIs.put(POIPosition,POItoUpdate); 

                            floor.put("POIList",POIs);

                            result = saveJSONObject(campusInfo,this.filename);

                        }

                    }
                }
                return result;
            }
            catch(Exception e){
            
                 throw new MetadataFileException("Error while updating the POI information in the JSON file"); 
            
            }
        }
        
        
         /**
         * Deletes a POI information from the metadata JSON file
         * @param bldgName the name of the building where the POI is located
         * @param floorNum the floor number where the POI is located
         * @param POItodel the POI object to delete
         * @return true if the POI information was deleted or false else
         * @throws MetadataFileException if an error occur while deleting the POI information
         */
        public Boolean deleteBuiltInPOI(String bldgName, int floorNum, POI POItodel)throws MetadataFileException{
            
            Boolean result = false;
        
            try{
                JSONObject campusInfo = loadMapData(this.filename); 

                JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");

                int buildingPosition = getBldgPos(bldgName, campusBuildings);

                if(buildingPosition >= 0){

                    JSONObject building = campusBuildings.getJSONObject(buildingPosition);

                    JSONArray floors = building.getJSONArray("FloorList");

                    int floorPosition = getFloorPos(floorNum, floors);

                    if(floorPosition >= 0){

                        JSONObject floor = floors.getJSONObject(floorPosition);

                        JSONArray POIs = floor.getJSONArray("POIList");

                        floor.remove("POIList");

                        int POIPosition = getPOIPos(POItodel.getPOIName(),POIs);

                        if(POIPosition >= 0){

                            POIs.remove(POIPosition);

                            floor.put("POIList",POIs);

                            result = saveJSONObject(campusInfo,this.filename);

                        }

                    }
                }
                return result;
            }
            catch(Exception e){
            
                throw new MetadataFileException("Error while deleting the POI information from the JSON file");
            }
        }
        
        
         /**
         * Adds a POI information to the metadata JSON file
         * @param bldgName the name of the building where the POI is located
         * @param floorNum the floor number where the POI is located
         * @param POItoAdd the POI object to add
         * @return true if the POI information was added or false else
         * @throws MetadataFileException if an error occur while adding the POI information
         */
        public Boolean addBuiltInPOI(String bldgName, int floorNum, POI POItoAdd)throws MetadataFileException{
            
            Boolean result = false;
            
            try{
        
                JSONObject newPOI = createPOIObject(POItoAdd);

                JSONObject campusInfo = loadMapData(this.filename); 

                JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");

                int buildingPosition = getBldgPos(bldgName, campusBuildings);

                if(buildingPosition >= 0){

                    JSONObject building = campusBuildings.getJSONObject(buildingPosition);

                    JSONArray floors = building.getJSONArray("FloorList");

                    int floorPosition = getFloorPos(floorNum, floors);

                    if(floorPosition >= 0){

                        JSONObject floor = floors.getJSONObject(floorPosition);

                        JSONArray POIs = floor.getJSONArray("POIList");

                        floor.remove("POIList");

                        int POIPosition = getPOIPos(POItoAdd.getPOIName(),POIs);

                        if(POIPosition < 0){

                            POIs.put(newPOI);

                            floor.put("POIList",POIs);

                            result = saveJSONObject(campusInfo,this.filename);

                        }

                    }
                }
                return result;
            }
            catch(Exception e){
                   throw new MetadataFileException("Error while adding the POI information in the JSON file"); 
            }
        
        }
        
         /**
         * Adds a campus POI information to the metadata JSON file
         * @param bldgName the name of the building where the POI is located
         * @param floorNum the floor number where the POI is located
         * @param POItoAdd the POI object to add
         * @return true if the POI information was added or false else
         * @throws MetadataFileException if an error occur while adding the POI information
         */
        public Boolean addCampusPOI(String bldgName, int floorNum, POI POItoAdd) throws MetadataFileException{
            
            Boolean result = false;
            
            try{
        
            JSONObject newPOI = createPOIObject(POItoAdd);
            
            JSONObject campusInfo = loadMapData(this.filename); 
            
            JSONArray campusPOIs = campusInfo.getJSONArray("CampusPOIList");
            
            int POIPosition = getPOIPos(POItoAdd.getPOIName(),campusPOIs);
                    
            if(POIPosition < 0){

                campusPOIs.put(newPOI);

                result = saveJSONObject(campusInfo,this.filename);
            }
                       
            return result;
            }
            catch(Exception e){
                throw new MetadataFileException("Error while adding the campus POI information in the JSON file");
            
            }
        
        }
        
         /**
         * Updates a campus POI information to the metadata JSON file
         * @param bldgName the name of the building where the POI is located
         * @param floorNum the floor number where the POI is located
         * @param oldPOI the POI object to update
         * @param newPOI the POI object with the new information
         * @return true if the POI information was updated or false else
         * @throws MetadataFileException if an error occur while updating the POI information
         */
         public Boolean updateCampusPOI(String bldgName, int floorNum, POI oldPOI, POI newPOI) throws MetadataFileException{
            
            Boolean result = false;
            
            try{
        
                JSONObject updatedPOI = createPOIObject(newPOI);

                JSONObject campusInfo = loadMapData(this.filename); 

                JSONArray campusPOIs = campusInfo.getJSONArray("CampusPOIList");

                int POIPosition = getPOIPos(oldPOI.getPOIName(),campusPOIs);

                if(POIPosition >= 0){

                    campusPOIs.put(POIPosition,updatedPOI);

                    result = saveJSONObject(campusInfo,this.filename);
                }

                return result;
            }
            catch(Exception e){
            
                throw new MetadataFileException("Error while updating the campus POI information in the JSON file");
            }
        
        }
         
        /**
         * Deletes a campus POI information to the metadata JSON file
         * @param bldgName the name of the building where the POI is located
         * @param floorNum the floor number where the POI is located
         * @param POItodel the POI object to delete
         * @return true if the POI information was updated or false else
         * @throws MetadataFileException if an error occur while deleting the POI information
         */
        public Boolean deleteCampusPOI(String bldgName, int floorNum, POI POItodel) throws MetadataFileException{
            
            Boolean result = false;
            
            try{
            
                JSONObject campusInfo = loadMapData(this.filename); 

                JSONArray campusPOIs = campusInfo.getJSONArray("CampusPOIList");

                int POIPosition = getPOIPos(POItodel.getPOIName(),campusPOIs);

                if(POIPosition >= 0){

                    campusPOIs.remove(POIPosition);

                    result = saveJSONObject(campusInfo,this.filename);
                }

                return result;
            }
            catch(Exception e){
            
                throw new MetadataFileException("Error while deleting the campus POI information in the JSON file");
            
            }
        
        }
        
         /**
         * Adds a new floor information to the metadata JSON file
         * @param newFloor the new floor Object to add
         * @param bldgName the name of the building where the floor is located
         * @return true if the new floor information was added or false else
         * @throws MetadataFileException if an error occur while adding  the new floor
         */
        public Boolean addFloor(Floor newFloor, String bldgName)throws MetadataFileException{
        
             Boolean result = false;
             try {
                JSONObject campusInfo = loadMapData(this.filename); 

                JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");
             
                JSONObject floorToAdd = createFloorObject(newFloor);

                int buildingPosition = getBldgPos(bldgName, campusBuildings);
             
                if(buildingPosition >= 0){

                    for(int i =0; i< campusBuildings.length(); i++){
                    
                        JSONObject item = campusBuildings.getJSONObject(i);
                        
                        String name = item.getString("BuildingName");
                       
                        if(name.equals(bldgName)){
                        
                            JSONArray floors = item.getJSONArray("FloorList");
                            
                            int floorPosition = getFloorPos(newFloor.getfloorNUm(),floors);
                           
                            if(floorPosition < 0){
                           
                                floors.put(floorToAdd);

                                result = saveJSONObject(campusInfo,this.filename);
                            }

                        }
                 
                    }

                   }

                return result;
             }
             catch(Exception e){
             
                 throw new MetadataFileException("Error while adding the new building in the JSON file");
             
             }
        }
        
         /**
         * Edits a building information to the metadata JSON file
         * @param oldFloor the floor Object to edit
         * @param newFloor the floor Object containing the new information
         * @param bldgName the name of the building where the floor is located
         * @return true if the floor information was updated or false else
         */
        public Boolean editFloor(Floor oldFloor, Floor newFloor, String bldgName){
            
            Boolean result = false;
            
            JSONObject campusInfo = loadMapData(this.filename); 
            
            JSONArray campusPOIs = campusInfo.getJSONArray("CampusPOIList");
            
            JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");
           
            JSONObject floorToAdd = createFloorObject(newFloor);
            
            boolean resultNumMap = true;
            
            boolean resulfileMap = true;
          
            int buildingPosition = getBldgPos(bldgName, campusBuildings);
            
            int floorPosition;
                  
            if(buildingPosition >= 0){

              

                    JSONObject building = campusBuildings.getJSONObject(buildingPosition);

                    String name = building.getString("BuildingName");

                    if(name.equals(bldgName)){

                        JSONArray floors = building.getJSONArray("FloorList");

                        floorPosition = getFloorPos(oldFloor.getfloorNUm(),floors);

                        if(floorPosition >= 0){

                            if(oldFloor.getfloorNUm() != newFloor.getfloorNUm()){
                                
                               
                                JSONObject floorModified = changeFloorNumInFloor(floorToAdd, newFloor.getfloorNUm());

                                floors.put(floorPosition, floorModified);

                                resultNumMap = true;
                            }

                            if(!oldFloor.getMapFile().equals(newFloor.getMapFile())){


                                JSONObject floorMapMod = changeMapInFloor(floorToAdd, newFloor.getMapFile());

                                floors.put(floorPosition,floorMapMod);

                                resulfileMap = true;                                    

                            }
                        }

                    }

            }

        
            if(resulfileMap && resultNumMap){
            
                result = saveJSONObject(campusInfo,this.filename);
                
                return result;
            }
            
            return result;
        }
        
        
        /**
         * Edits a building information to the metadata JSON file
         * @param floorToDel the floor Object to delete
         * @param bldgName the name of the building where the floor is located
         * @return true if the floor information was updated or false else
         */
        public Boolean deleteFloor(Floor floorToDel, String bldgName){
            
            Boolean result = false;
            
            JSONObject campusInfo = loadMapData(this.filename); 
            
           
            JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");
         
            int buildingPosition = getBldgPos(bldgName, campusBuildings);
            
            int floorPosition;
            
            
                
            if(buildingPosition >= 0){

                   
              
                    JSONObject building = campusBuildings.getJSONObject(buildingPosition);

                    String name = building.getString("BuildingName");
                   

                    if(name.equals(bldgName)){
                        
                       

                        JSONArray floors = building.getJSONArray("FloorList");

                        floorPosition = getFloorPos(floorToDel.getfloorNUm(),floors);

                        if(floorPosition >= 0){

                           floors.remove(floorPosition);
                           
                           result = saveJSONObject(campusInfo,this.filename);
                           
                        }

                    }

            }
            
            return result;
        }
      
         /**
         * Adds a new building information to the metadata JSON file
         * @param newBldg the new building Object to add
         * @return true if the new building information was added or false else
         * @throws MetadataFileException if an error occur while deleting the POI information
         */
        public Boolean addBuilding(Building newBldg)throws MetadataFileException{
        
             Boolean result = false;
             try {
                JSONObject campusInfo = loadMapData(this.filename); 

                JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");

                JSONObject bldgToAdd = createBlgObject(newBldg);

                String bldgName = newBldg.getName();

                int buildingPosition = getBldgPos(bldgName, campusBuildings);

                if(buildingPosition < 0){

                    campusBuildings.put(bldgToAdd);

                    result = saveJSONObject(campusInfo,this.filename);

                   }

                return result;
             }
             catch(Exception e){
             
                 throw new MetadataFileException("Error while adding the new building in the JSON file");
             
             }
        }
        
        /**
         * Deletes a building information from the metadata JSON file
         * @param bldgToDel the building Object to edit
         * @return true if the building information was deleted or false else
         */
        public Boolean deleteBuilding(Building bldgToDel)throws MetadataFileException{
            
            try{
        
                Boolean result = false;

                JSONObject campusInfo = loadMapData(this.filename); 

                JSONArray campusPOIs = campusInfo.getJSONArray("CampusPOIList");

                JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");

                String bldgName = bldgToDel.getName();

                boolean resultMap = true;

                boolean resultCampPOI = true;

                int buildingPosition = getBldgPos(bldgName, campusBuildings);

                int buildingPosition2 = getBldgPosInCampPoI(bldgName, campusPOIs);

                if(buildingPosition >= 0){

                    try{  
                        campusBuildings.remove(buildingPosition);
                    }
                    catch(Exception e){

                        resultMap = false;

                    }

                }

                if(buildingPosition2 >= 0){

                    try{ 
                        campusPOIs.remove(buildingPosition2);
                    }
                    catch(Exception e){

                        resultCampPOI = false;

                    }
                }
                if(resultCampPOI && resultMap){

                    result = saveJSONObject(campusInfo,this.filename);

                    return result;

                }
                return result;
            }
             catch(Exception e){
             
                 throw new MetadataFileException("Error while adding the new building in the JSON file");
             
            }
             
        }
         /**
         * Edits a building information to the metadata JSON file
         * @param oldBldg the building Object to edit
         * @param newBldg the building Object containing the new information
         * @return true if the building information was updated or false else
         */
        public Boolean editBuilding(Building oldBldg, Building newBldg){
            
            Boolean result = false;
            
            JSONObject campusInfo = loadMapData(this.filename); 
            
            JSONArray campusPOIs = campusInfo.getJSONArray("CampusPOIList");
            
            JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");
            
            String bldgName = oldBldg.getName();
            
            JSONObject bldgToUpdate = createBlgObject(newBldg);
            
            boolean resultNameMap = true;
            
            boolean resultNameCampPOI = true;
            
            boolean resultLocationMap = true;
            
            boolean resultLocationCampPOI = true;
            
            boolean resultDescMap = true;
            
            boolean resultDescCampPOI = true;
            
            int buildingPosition = getBldgPos(bldgName, campusBuildings);
                
            int buildingPosition2 = getBldgPosInCampPoI(bldgName, campusPOIs);
            
            if(!bldgName.equals(newBldg.getName())){
            
                if(buildingPosition >= 0){
                
                    try{
                       JSONObject newBuilding = changeBldgNameInBldg(bldgToUpdate,newBldg.getName());

                       campusBuildings.put(buildingPosition, newBuilding);
                    }
                    catch(Exception e){
                        resultNameMap = false;

                    }
              
                }
                
            
                if(buildingPosition2 >= 0){
                
                    try{
                        JSONObject newBldgPOI = changeBldgNameInCampPOI(campusPOIs.getJSONObject(buildingPosition2),newBldg.getName());

                        campusPOIs.put(buildingPosition2,newBldgPOI);
                    }
                    catch(Exception e){
                    
                        resultNameCampPOI = false;
                    
                    }
                
                }
            
            }
            
            if(!oldBldg.getLocation().equals(newBldg.getLocation())){
                
                if(buildingPosition >= 0){

                    try{
                       JSONObject newBuildingPos = changeBldgPosInBldg(bldgToUpdate,newBldg.getLocation());

                       campusBuildings.put(buildingPosition, newBuildingPos);
                    }
                    catch(Exception e){

                           resultLocationMap = false;

                    }
                }
            
                if(buildingPosition2 >= 0){
                
                    try{
                        JSONObject newBldgPOIPos = changeBldgPosInPOI(campusPOIs.getJSONObject(buildingPosition2),newBldg.getLocation());

                        campusPOIs.put(buildingPosition2,newBldgPOIPos );
                    }
                    catch(Exception e){

                           resultLocationCampPOI = false;

                    }
                
              
                }
               
            }
        
            if(!oldBldg.getDescription().equals(newBldg.getDescription())){
     
                 
                 if(buildingPosition >= 0){
                     
                     try{
                 
                        JSONObject newBuildingDesc = changeBldgDescInBldg(bldgToUpdate,newBldg.getDescription());

                        campusBuildings.put(buildingPosition, newBuildingDesc);
                     }
                     catch(Exception e){
                     
                         resultDescMap = false;
                     }
                 
                 }
                 if(buildingPosition2 >= 0){
                
                    try{
                        JSONObject newBldgPOIDesc = changeBldgDescInPOI(campusPOIs.getJSONObject(buildingPosition2),newBldg.getDescription());

                        campusPOIs.put(buildingPosition2,newBldgPOIDesc );
                    }
                    catch(Exception e){

                           resultDescCampPOI = false;

                    }
                
              
                }
              
            }
                       
            if(resultNameMap && resultNameCampPOI && resultDescMap && resultDescCampPOI && resultLocationMap && resultLocationCampPOI){
            
                result = saveJSONObject(campusInfo,this.filename);
                
                return result;
            }
            
            return result;
        }
	
	/**
         * Removes a user from the JSON file
         * @param user the suer to remove
         * @return true if the user is removed false otherwise
         */
	public Boolean removeUser(User user) {
		
		Boolean result = false;
		
		JSONArray users = loadUserData(this.filename);
		
		int userPosition = getUserPos(user,users);
                
               
		if((userPosition > 0) && (userPosition < users.length())) {
			
			users.remove(userPosition);
			
			result = saveJSONArray(users,this.filename);
			
			return result;
			
		}
		return result;
                
	}
        /**
        * Reads the map metadata JSON file and returns a campusMap object
        * @return a campusMap object with all the metadata information
        */
        public CampusMap getCampusData(){
            
            try{
            
                JSONObject campusInfo = loadMapData(this.filename);

                String campusMapfile = campusInfo.getString("campusMap");

                JSONArray campusPOIs = campusInfo.getJSONArray("CampusPOIList");

                ArrayList<POI> POIList = new ArrayList<POI>();
                
                POIList = getPOIList(campusPOIs);
                
                JSONArray campusBuildings = campusInfo.getJSONArray("BuildingList");
                
                ArrayList<Building> buildingList = getBuildings(campusBuildings);
                
                CampusMap map = new CampusMap(campusMapfile,POIList,buildingList);
                
                return map;
                
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
            
        
        }
        
        
        
	/** Helper Functions */
	
	/**
         * Saves the JSONArray provided in the JSON file provided
         * @param array the Array to save in the file
         * @param filename the name of the JSON file
         * @return true if the Array was saved in the file or false
         */
	private Boolean saveJSONArray(JSONArray array, String filename) {
            
                try (FileWriter Data = new FileWriter(filename)) {
		        Data.write(array.toString(4)); 
		        return true;
		 } catch (JSONException e) {
				e.printStackTrace();
				return false;
		 } catch (IOException e) {
				e.printStackTrace();
				return false;
		}
	}
        
        /**
         * Saves the JSONObject provided in the JSON file provided
         * @param object the Object to save in the file
         * @param filename the name of the JSON file
         * @return true if the Object was saved in the file or false
         */
	private Boolean saveJSONObject(JSONObject object, String filename) {
            
                try (FileWriter Data = new FileWriter(filename)) {
		        Data.write(object.toString(4)); 
		        return true;
		 } catch (JSONException e) {
				e.printStackTrace();
				return false;
		 } catch (IOException e) {
				e.printStackTrace();
				return false;
		}
	}
	
	/**
         * Creates a JSONobject using the information inside a USer object
         * @param user the user for which to create the JSONObject
         * @return the JSONObject created or null
         */
	private JSONObject createUserObject(User user) {
		
		JSONObject userToAdd = new JSONObject();
		
                if(user != null){
                    userToAdd.put("email", user.getEmail());
                    userToAdd.put("password", user.getPassword());
                    userToAdd.put("Question", user.getSecQuestion());
                    userToAdd.put("Answer", user.getSecAnswer());
                    userToAdd.put("Developer", user.getUserType());

                    JSONArray POIList = new JSONArray();

                    ArrayList<POI> list = user.getPOIs();

                    for(int j= 0; j < list.size(); j++) {

                            JSONObject POI = new JSONObject();
                            POI.put("POIName",list.get(j).getPOIName());
                            POI.put("Description",list.get(j).getDesc());
                            POI.put("Type", list.get(j).getType());
                            POI.put("Fav", list.get(j).getFav());
                            POI.put("BuildingName", list.get(j).getBldg());
                            POI.put("FloorNumber", list.get(j).getFloor());
                            JSONObject loc = new JSONObject();
                            loc.put("x", list.get(j).getLocation().getx());
                            loc.put("y", list.get(j).getLocation().gety());
                            POI.put("Location", loc);
                            POIList.put(POI);

                    }

                    userToAdd.put("POIList", POIList);

                    return userToAdd;
                }
                return null;
	}
        
        /**
         * Creates a JSONobject using the information inside a POI object
         * @param newPOI the POI for which to create the JSONObject
         * @return the JSONObject created or null
         */
        private JSONObject createPOIObject(POI newPOI){
        
            JSONObject POIToAdd = new JSONObject();
            
            if(newPOI !=null){
     
                POIToAdd.put("POIName",newPOI.getPOIName());
                POIToAdd.put("Description",newPOI.getDesc());
                POIToAdd.put("Type", newPOI.getType());
                POIToAdd.put("Fav", newPOI.getFav());
                POIToAdd.put("BuildingName", newPOI.getBldg());
                POIToAdd.put("FloorNumber", newPOI.getFloor());
                JSONObject loc = new JSONObject();
                loc.put("x", newPOI.getLocation().getx());
                loc.put("y", newPOI.getLocation().gety());
                POIToAdd.put("Location", loc);
                
                return POIToAdd;
            }
            
            return null;
        }
        
         /**
         * Creates a JSONobject using the information inside a Building object
         * @param newBldg the building for which to create the JSONObject
         * @return the JSONObject created or null
         */
        private JSONObject createBlgObject(Building newBldg){
        
            JSONObject bldgToAdd = new JSONObject();
            
            if(newBldg != null){
     
                bldgToAdd.put("BuildingName",newBldg.getName());
                bldgToAdd.put("Building Description",newBldg.getDescription());
                JSONObject loc = new JSONObject();
                loc.put("x", newBldg.getLocation().getx());
                loc.put("y", newBldg.getLocation().gety());
                bldgToAdd.put("Building Location", loc);
                
                ArrayList<Floor> floors = newBldg.getFloors();
                
                JSONArray bldgFloors = new JSONArray();
            
                for(int i =0; i <floors.size();i++){
                
                    Floor newFloor = floors.get(i);
                    
                    JSONObject floorToAdd = createFloorObject(newFloor);
                    
                    if(floorToAdd != null){
                        bldgFloors.put(floorToAdd);
                    }
                }
                
                bldgToAdd.put("FloorList", bldgFloors);
       
                return bldgToAdd;
            }
            
            return null;
        }
        
         /**
         * Creates a JSONobject using the information inside a Floor object
         * @param newFloor the floor for which to create the JSONObject
         * @return the JSONObject created or null
         */
        private JSONObject createFloorObject(Floor newFloor){
        
            JSONObject FloorToAdd = new JSONObject();
            
            if(newFloor !=null){
     
                FloorToAdd.put("BuildingName",newFloor.getBldgName());
                FloorToAdd.put("FloorNumber",newFloor.getfloorNUm());
                FloorToAdd.put("FloorMap", newFloor.getMapFile());
                
                ArrayList<POI> floorPOIs = newFloor.getPOIs();
                
                JSONArray POIs = new JSONArray();
                for(int i =0; i< floorPOIs.size();i++){
                
                     POI indPOI = floorPOIs.get(i);
                     
                     JSONObject POItoAdd = createPOIObject(indPOI);
                     
                     if(POItoAdd != null){
                        POIs.put(POItoAdd);
                     }
                }
               
                FloorToAdd.put("POIList", POIs);
                
                return FloorToAdd;
        
            }
            
            return null;
        }
        
        /**
          * Changes a building name in a Building Object in the metadata JSON file
          * @param building which name to change
          * @param newName the new name of the building
          * @return the building JSONObject with the new name
          */
         private JSONObject changeBldgNameInCampPOI(JSONObject POItoUpdate, String newName){
        
         
            if(POItoUpdate != null){
                
                   POItoUpdate.put("POIName", newName);

            }
            return POItoUpdate;
         }
	
         /**
          * Changes a building name in a Building Object in the metadata JSON file
          * @param building which name to change
          * @param newName the new name of the building
          * @return the building JSONObject with the new name
          */
        private JSONObject changeBldgNameInBldg(JSONObject building, String newName){
            
            if(building != null){
             
                    building.put("BuildingName", newName);
                    
                    JSONArray floors = building.getJSONArray("FloorList");
                    
                    JSONArray newFloors = changeBldgNameInFloor(floors,newName);
                    
                    building.remove("FloorList");
                    
                    building.put("FloorList", newFloors);
         
            }
        
            return building;
        }
        
         /**
          * Changes a building name in the floors object inside the building in the metadata JSON file
          * @param floors the array containing the floors inside the building
          * @param newName the new name of the building
          * @return the array of floors updated 
          */
        private JSONArray changeBldgNameInFloor(JSONArray floors, String newName){
        
            if(floors != null){
            
                for(int i = 0; i < floors.length(); i++){
                
                    JSONObject item = floors.getJSONObject(i);
                    
                    item.put("BuildingName", newName);
                    
                    JSONArray POIs = item.getJSONArray("POIList");
                    
                    JSONArray newPOIs = changeBldgNameInPOI(POIs,newName);
                    
                    item.remove("POIList");
                    
                    item.put("POIList",newPOIs);
            
                }
            
            }
        
           return floors; 
        }
        
        /**
         * Changes a building name in the POIs object inside the building in the metadata JSON file
         * @param POIs the array containing the POIs inside the building
         * @param newName he new name of the building
         * @return the array of POIs updated
         */
         private JSONArray changeBldgNameInPOI(JSONArray POIs, String newName){
        
            if(POIs != null){
            
                for(int i = 0; i < POIs.length(); i++){
                
                    JSONObject item = POIs.getJSONObject(i);
                    
                    item.put("BuildingName", newName);
                }
            
            }
            return POIs;
        
        }
        
          /**
          * Changes a building description in a Building Object in the metadata JSON file
          * @param building which description to change
          * @param newDesc the new description of the building
          * @return the building JSONObject with the new description
          */
          private JSONObject changeBldgDescInBldg(JSONObject building, String newDesc){
            
            if(building != null){
             
                building.put("Building Description", newDesc);
   
            }
        
            return building;
        }
         
        /**
          * Changes a building description in a Building Object in the metadata JSON file
          * @param building which description to change
          * @param newDesc the new description of the building
          * @return the building JSONObject with the new description
          */
         private JSONObject changeBldgDescInPOI(JSONObject POItoUpdate, String newDesc){
        
         
            if(POItoUpdate != null){
                
                   POItoUpdate.put("Description", newDesc);

            }
            return POItoUpdate;
         }
        /**
          * Changes a building position in a Building Object in the metadata JSON file
          * @param building which position to change
          * @param position the new position of the building
          * @return the building JSONObject with the new position
          */
         private JSONObject changeBldgPosInBldg(JSONObject building, Vec2 position){
        
            if(building != null){
                
                    JSONObject loc = new JSONObject();
                    
                    loc.put("x", position.getx());
                    
                    loc.put("y", position.gety());
             
                    building.put("Building Location", loc);
       
            }
        
            return building;
        }
        
          /**
          * Changes a building position in the POIs object inside the building in the metadata JSON file
          * @param POIs the array containing the POIs inside the building
          * @param position the new position of the building
          * @return the array of POIs updated 
          */
        private JSONObject changeBldgPosInPOI(JSONObject POItoUpdate, Vec2 position){
           
            if(POItoUpdate != null){
            
                
                JSONObject loc = new JSONObject();

                loc.put("x", position.getx());

                loc.put("y", position.gety());

               POItoUpdate.put("Location", loc);
               
            }
        
           return POItoUpdate;
        }
	
        
        /**
          * Changes a floor map file in a floor Object in the metadata JSON file
          * @param floor which map file to change
          * @param file the new map file of the floor (.png)
          * @return the floor JSONObject with the new map file
          */
          private JSONObject changeMapInFloor(JSONObject floor, String file){
            
            if(floor != null){
               
                floor.put("FloorMap", file);
                
          
            }
        
            return floor;
        }
        
         /**
          * Changes a floor number in a floor Object in the metadata JSON file
          * @param floor which number to change
          * @param floorNum the new number of the floor
          * @return the floor JSONObject with the new number
          */
          private JSONObject changeFloorNumInFloor(JSONObject floor, int floorNum){
            
            if(floor != null){
             
                floor.put("FloorNumber", floorNum);
                
                JSONArray floorPOIs = floor.getJSONArray("POIList");
                
                changeFloorNumInPOI(floorPOIs,floorNum);
   
            }
        
            return floor;
        }
          
          
         /**
          * Changes a floor number in the POIs inside the floor in the metadata JSON file
          * @param POIs the array of POIs inside the floor
          * @param floorNum the new number of the floor
          * @return the array of POIs with the new floor number
          */ 
          private JSONArray changeFloorNumInPOI(JSONArray POIs, int floorNum){
            
            if(POIs != null){
             
               for(int i = 0; i < POIs.length(); i++){
                   
                   JSONObject floorPOI = POIs.getJSONObject(i);
                   
                   floorPOI.put("FloorNumber", floorNum);
        
               }
               
            }
            return POIs;
          }
          
        /**
         * Gets the User object position in the provided JSONArray
         * @param user to find the position in the array
         * @param users the array where the user is located
         * @return the position of the user in the array
         */
	private int getUserPos(User user, JSONArray users) {
		
            int pos = -1;
            
            try{
                for (int i = 0; i < users.length(); i++) {

                    JSONObject item = users.getJSONObject(i);

                    if (user.getEmail().equals(item.getString("email"))) {
                         pos = i;
                    }
                }
                return pos;
            }
            catch (Exception e) {
                    
                return pos;
            }
	}
        
        /**
         * Gets the Building object position in the provided JSONArray
         * @param bldgName the name of the building to find
         * @param Buildings the array where the building is located
         * @return the position of the building in the array
         */
	private int getBldgPos(String bldgName, JSONArray Buildings) {
		
            int pos = -1;
            
            try{
		
                for (int i = 0; i < Buildings.length(); i++) {

                    JSONObject item = Buildings.getJSONObject(i);

                    if (bldgName.equals(item.getString("BuildingName"))) {

                        pos = i;
                    }
                }
                return pos;
            }
            catch (Exception e) {
                    
                return pos;
            }
	}
         /**
         * Gets the Building object position in the provided JSONArray
         * @param bldgName the name of the building to find
         * @param Buildings the array where the building is located
         * @return the position of the building in the array
         */
	private int getBldgPosInCampPoI(String bldgName, JSONArray Buildings) {
		
            int pos = -1;
            
            try{
		
                for (int i = 0; i < Buildings.length(); i++) {

                    JSONObject item = Buildings.getJSONObject(i);

                    if (bldgName.equals(item.getString("POIName"))) {

                        pos = i;
                    }
                }
                return pos;
            }
            catch (Exception e) {
                    
                return pos;
            }
	}
        
        /**
         * Gets the Floor object position in the provided JSONArray
         * @param floorNum the number of the floor to find
         * @param floors the array where the floors is located
         * @return the position of the floor in the array
        */
	private int getFloorPos(int floorNum, JSONArray floors) {
		
            int pos = -1;
		
		for (int i = 0; i < floors.length(); i++) {
	        
			JSONObject item = floors.getJSONObject(i);
	        
			if (floorNum == item.getInt("FloorNumber")) {
	            
                        pos = i;
	        }
	    }
            return pos;
	}
	
        /**
         * Gets the POI object position in the provided JSONArray
         * @param POIName the name of the POI to find
         * @param POIs the array where the POI is located
         * @return the position of the POI in the array
        */
	private int getPOIPos(String POIName, JSONArray POIs) {
		
            int pos = -1;
            
            try{
		
                for (int i = 0; i < POIs.length(); i++) {

                        JSONObject item = POIs.getJSONObject(i);

                        if (POIName.equals(item.getString("POIName"))) {

                            pos = i;
                        }
                }
                return pos;
            }
            catch (Exception e) {
                    
                System.out.println("Error reading file");
                
                return pos;

            }
	}
	
	/**
         * Checks if the current user is already in the user file using its email
         * @param user the User object to use for checking the information
         * @return true if the user exists in the file otherwise false
         * @throws MetadataFileException if an error occur while checking the user information
         */
	public Boolean UserExist(User user) throws MetadataFileException{
            
                JSONArray users = loadUserData(this.filename);
                
                try{
                    JSONObject nextUser;

                    for(Object j : users) {

                            nextUser = (JSONObject) j;

                            if(user.getEmail().equals(nextUser.getString("email"))) {

                                return true;
                            }
                    }
                    return false;
                }
                catch (Exception e) {
                    
                         throw new MetadataFileException("Error while reading the user file");
			
		}
                
	}
        
        
       
	
	/**
         * Reads the user file and return its content as a JSONArray
         * @param filename
         * @return the JSONArray with all the users data or null
         */
	private JSONArray loadUserData(String filename) {
		
		String users = readFile(filename);
		
		
                if(users != null){
                    
                    JSONArray userArray = new JSONArray(users);
                    
                    return userArray;
                }
		return null;
		
		
	}
        
        
        /**
         * Reads the metadata file and return its content as a JSONObject
         * @param filename the name of the JSON file
         * @return the JSONObject with all the metadata or null
         */
        private JSONObject loadMapData(String filename){
        
            String mapInfo = readFile(filename);
            
            if(mapInfo != null){
            
                JSONObject campusInfo = new JSONObject(mapInfo);

                return campusInfo;
            }
            return null;
        }
	
	
        /**
         * Reads a JSON file in a String
         * @param filename the name of the JSON file
         * @return a string containing the JSON file contents
         */
	private String readFile(String filename) {
        
            
            String JSONContents = new String();
		
		try {
		
			JSONContents = new String(Files.readAllBytes(new File(filename).toPath()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return JSONContents;
            
	}

        
        /**
         * Gets the POI list inside inside the JSON array and returns the list as a ArrayList
         * @param filename the name of the JSON file
         * @return the POI list in an ArrayList
         */
        private ArrayList<POI>  getPOIList(JSONArray POIList){
        
            ArrayList<POI> POIs = new ArrayList<>();
            
            JSONObject nextPOI;

            if(POIList.length()>0){
                try{

                        for (Object POIObj: POIList) {

                                nextPOI  = (JSONObject) POIObj;

                                String POIName = nextPOI.getString("POIName");

                                String description = nextPOI.getString("Description");

                                int type = nextPOI.getInt("Type");

                                Boolean Fav = nextPOI.getBoolean("Fav");

                                String bldgName = nextPOI.getString("BuildingName");

                                int floorNum = nextPOI.getInt("FloorNumber");

                                JSONObject location = nextPOI.getJSONObject("Location");

                                Vec2 POILocation = new Vec2(location.getInt("x"),location.getInt("y"));

                                POI newPOI = new POI(bldgName,floorNum,POIName,type,description,Fav,POILocation);

                                POIs.add(newPOI);

                        }

                        return POIs;
                }
                catch(Exception e){

                    return POIs;
                }
            }
            else{
            
                return POIs;
            
            }
        
            
    }
        
    
     /**
    * Gets the building list inside inside the JSON array and returns the list as a ArrayList
    * @param filename the name of the JSON file
    * @return the building list in an ArrayList
    */
    private ArrayList<Building> getBuildings(JSONArray bldgList){
        
       ArrayList<Building> buildings = new ArrayList<>();
       
       JSONObject nextBldg;
       
       if(bldgList.length()>0){
            
           try{

                for (Object bldgObj: bldgList) {

                    nextBldg  = (JSONObject) bldgObj;

                    String bldgName = nextBldg.getString("BuildingName");

                    String bldgDesc = nextBldg.getString("Building Description");

                    JSONObject location = nextBldg.getJSONObject("Building Location");

                    Vec2 bldgLocation = new Vec2(location.getInt("x"),location.getInt("y"));

                    JSONArray floorList = nextBldg.getJSONArray("FloorList");

                    ArrayList<Floor> bldgFloors = getFloors(floorList);

                    Building newBuilding = new Building(bldgName,bldgLocation,bldgDesc,bldgFloors);
                    
                    buildings.add(newBuilding);


                }

               return buildings;
               
           }
            catch(Exception e){

                System.out.println("Error while reading floors from metadata file");
                return buildings;
            }
        }
        else{
           return buildings;
        }
         
}
    
     /**
    * Gets the floor list inside inside the JSON array and returns the list as a ArrayList
    * @param filename the name of the JSON file
    * @return the floor list in an ArrayList
    */
    private ArrayList<Floor> getFloors(JSONArray floorList){
    
        ArrayList<Floor> floors = new ArrayList<Floor>();
        
        JSONObject nextFloor;
        
            if(floorList.length()>0){
                try{

                     for (Object floorObj: floorList) {

                        nextFloor  = (JSONObject) floorObj;

                        String bldgName = nextFloor.getString("BuildingName");

                        String mapFile = nextFloor.getString("FloorMap");

                        int floornum = nextFloor.getInt("FloorNumber");

                        JSONArray POIList = nextFloor.getJSONArray("POIList");

                        ArrayList<POI> floorPOIs = getPOIList(POIList);

                        Floor newfloor = new Floor(bldgName,floornum,mapFile,floorPOIs);

                        floors.add(newfloor);
                     }
                        return floors;
                }
                catch(JSONException | NullPointerException e){
                    
                    System.out.println("Incorrect value for floor in metadata file");
                    return floors;
                
                }
                catch(Exception e){

                    System.out.println("Error while reading floors from metadata file");
                    return floors;
                }
            }
            else{
                return floors;
            
            }
    
    }
}

