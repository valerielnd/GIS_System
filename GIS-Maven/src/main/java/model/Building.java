package model;

import java.util.ArrayList;

/**
 * @author John Curran Krupa
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard
 * @author Thai An Luong
 * @author Tyler James Johnson
 *
 * This is the Building Class, it is a representation of a building
 * and exists with a location on the campus map. The building contains an
 * array as a representation of its Floors.
 */
public class Building {

    /**
     * The name of the building
     */
    private String bldgName;

    /**
     * The list of floor inside this building
     */
    private ArrayList<Floor> floorList;

    /**
     * The buildings location on the campus map (as x,y coordinates)
     */
    private Vec2 buildingLocation;

    /**
     * The description of the building
     */
    private String buildingDescription;

    /**
     * Constructor of a building object without parameters
     */
    public void Building(){

        this.bldgName = "";
        this.buildingDescription = "";
        Vec2 location = new Vec2(0,0);
        this.buildingLocation = location;
        this.floorList = new ArrayList<Floor>();

    }

   
    /**
     * Constructor of a building object with 3 parameters
     * @param name the building name
     * @param location the building Vec2 location
     * @param desc the building description
     */
    public void Building(String name, Vec2 location, String desc){

        this.bldgName = name;

        this.buildingLocation = location;

        this.buildingDescription = desc;

        this.floorList = new ArrayList<Floor>();
    }
    
    /**
     * This is a constructor for building objects only the first three parameters
     * @param name The name of the building
     * @param location The location of the building's x and y coordinates on the campus map
     * @param description The description of the building
     */
    public Building(String name, Vec2 location, String description){
    
        this.bldgName = name;
        
        this.buildingLocation = location;
        
        this.buildingDescription = description;
        
        this.floorList = new ArrayList<Floor>();
    }
            
    /**
     * This is a complete constructor for building objects it takes all possible params
     * @param bldgName The name of the building
     * @param buildingLocation The location of the building's x and y coordinates on the campus map
     * @param floorList The ArrayList of floors,
     * @param buildingDescription The description of the building
     */
    public Building(String bldgName, Vec2 buildingLocation, String buildingDescription,ArrayList<Floor> floorList){
        this.bldgName = bldgName;
        this.buildingLocation = buildingLocation;
        this.buildingDescription = buildingDescription;
        this.floorList = floorList;
    }

    /**
     * This is a getter for the building name
     * @return The name of the Building
     */
    public String getName() {
        return bldgName;
    }

    /**
     * This is a setter for the name of the building
     * @param bldgName The new name of the building
     */
    public void setBldgName(String bldgName) {
        this.bldgName = bldgName;
    }

    /**
     * This is a getter for the number of floors the building has
     * @return The int number of floors the building has
     */
    public int getFloorNum(){
        return floorList.size();
    }

    /**
     * This is a getter for the floor list
     * @return The floor list, stored as an ArrayList of Floor objects
     */
    public ArrayList<Floor> getFloors(){
        return floorList;
    }

    /**
     * This is function to add a new floor to building object.
     * @param newfloor The floor to be added to the building
     * @return true if the floor is added, false otherwise
     */
    public Boolean addFloor(Floor newfloor) {
        if(newfloor != null){
            
            floorList.add(newfloor);
            
            return true;
        }
        return false;
    }

    /**
     * This is a function to remove a floor from a building object
     * @param floorToRemove The floor to be removed from the building.
     * @throws EmpytListException if the list is empty
     */
    public void removeFloor(Floor floorToRemove) throws EmpytListException{
        if(!floorList.isEmpty()){
            floorList.remove(floorToRemove);
        }
        else{
            throw new EmpytListException("List of floor is empty");
        }
    }

    /**
     * This is a setter for the description of the building
     * @param buildingDescription The new description for the building
     */
    public void setBuildingDescription(String buildingDescription){
        this.buildingDescription = buildingDescription;
    }

    /**
     * This is a getter for the description of the building
     * @return The building's description as a String
     */
    public String getDescription(){
        return this.buildingDescription;
    }

    /**
     * This is a setter for the Vec2 location of the building
     * @param buildingLocation the new Vec2 location of the building
     */
    public void setBuildingLocation(Vec2 buildingLocation){
        this.buildingLocation = buildingLocation;
    }

    /**
     * This is a getter for the Vec2 location of the building
     * @return the building location in Vec2
     */
    public Vec2 getLocation(){
        return buildingLocation;
    }

    /**
     * This is a boolean comparison function.
     * @param bldg2 This is the building to compare against
     * @return the true or false
     */
    public boolean isEqual(model.Building bldg2){
        if(this.bldgName.equals(bldg2.getName())  && this.getDescription().equals(bldg2.getDescription()) && this.getLocation().isEqual(bldg2.getLocation())){
            return true;
        }

        return false;
    }
}

