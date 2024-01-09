package model;
import java.util.ArrayList;

/**
 * Floor entity for storing information about a floor. 
 * <br><br>
 * Entity can be initialized with Floor(Floor Number, List of POIs on floor, Name of building containing floor, File name of the floor) <br>
 * Get/Set functions can be used with floorNum(floor number), POI List (floor number), Name(POI name), Type(POI type), Desc(POI description), and Location(x,y coordinate @see Vec2). <br>
 * Favorite status can be checked with the {@link isFav} method and status can be alters with the {@link setFav} method. <br>
 * User created is a special type (POItype = 16) that can be check with the {@link isUserCreated} method.
 * <br><br>
 * 
 * <b>Example Use: </b>
 * <pre>
 * {@code
 * 		POI myNewPOI = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries", newVect2(1002, 325), false);
 * 		myNewPOI.setFav(true);
 * 		myPOI.setBldg("Alumni Hall");
 * 		System.out.println(myNewPOI.getDesc());
 * }
 * </pre>
 * 
 * <b>Example Output:</b>  <code>Restaurant in MC basement - good fries</code>
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson  
 */
public class Floor {
	
        /**
         * This represents the floors number 
         */
	private int floorNum; 
        
        /**
         * This is the list of POIs on the floor
         */
	private ArrayList<POI> POIList = new ArrayList();
        
        /**
         * Name of the building that holds this floor
         */
	private String blgdName; 
        
        /**
         * The image file for this floor
         */
	private String floorFileName;
	
        
	/**
	 * Constructor a floor which does not take any parameters
	 */
	public Floor(){

		this.blgdName = "";
		
		this.floorNum = 0;
		
		this.floorFileName = "";
		
		this.POIList = new ArrayList<POI>();
		
		
	}
	
        /**
	 * Constructor a floor
	 * 
	 * <h1> Example </h1>
	 * <code>
	 * 
	 * Floor floor = new Floor("Building 1", 2,"img.png");
	 * 
	 * </code>
	 * 
	 * @param floorNum The floor number
	 * @param bldgName The name of the building
	 * @param file The floors image file
	 */
	public Floor(String bldgName, int floorNum, String file){
    
            this.blgdName = bldgName;

            this.floorNum = floorNum;

            this.floorFileName = file;

            this.POIList = new ArrayList<POI>();
        }
        
	/**
	 * Constructor a floor
	 * 
	 * <h1> Example </h1>
	 * <code>
	 * 
	 * Floor floor = new Floor("Building 1",2, "img.png", new ArrayList&ltPOI&gt);
	 * 
	 * </code>
	 * 
	 * @param iniFloorNum The floor number
	 * @param iniPOIList The list of POIS
	 * @param iniBlgdName The name of the building
	 * @param iniFloorFileName The floors image file
	 */
	public Floor(String iniBlgdName, int iniFloorNum , String iniFloorFileName, ArrayList<POI> iniPOIList){
    
        this.blgdName = iniBlgdName;
        
        this.floorNum = iniFloorNum ;
        
        this.floorFileName = iniFloorFileName;
        
        this.POIList = iniPOIList;
    }
	
	
	/**
         * Returns the floor's number
         * @return An int represents the floors number
         */
	public int getfloorNUm() { return floorNum; }

	
	/**
	 * Sets the floor number of the floor to a given floor number. 
	 * @param	newFloorNum	Integer number that we want to set the floor too
	 */
	public void setFloorNum(int newFloorNum) { floorNum = newFloorNum; }

	/**
	 * Returns the pointer to the list of POIs on this floor
	 * @return	POIList	List of POIs
	 */
	public ArrayList<POI> getPOIs() { return POIList; }

	/**
	 * Get function for the file name of the floor map  the floor number of the floor to a given floor number. 
	 * @return	floorFileName	Returns the file name of the map of this floor. 
	 */
	public String getMapFile() { return floorFileName; }

	/**
	 * Sets a new map file for the floor by giving a new file name.
	 * @param	newMapFile	the name of the new file that we want to set
	 */
	public void setFloorFileMap(String newMapFile) { floorFileName=newMapFile; }
	
        /**
         * Returns the name of the building which holds this floor
         * @return A string which holds the buildings name
         */
	public String getBldgName() { return blgdName; }
	
	/**
	 * Assigns the floor to the given building.
	 * @param	newBlgdName	 new building name given
	 */
	public void setBlgdName(String newBlgdName) { blgdName=newBlgdName; }
	
	/**
	 * Sets the floor number of the floor to a given floor number. 
	 * @param	newPOI	new POI to add to the POI list
	 */
	public boolean addPOI(POI newPOI) {
		
            if(newPOI != null){
		POIList.add(newPOI);
                return true;
            }
		return false;
	}
	
        /**
         * Searches for a POI given a name
         * @param targetPOIName The name of the target POI
         * @return The POI or null if not found
         */
	public POI searchPOI(String targetPOIName) {
		//int searchList = POIList.size(); --> check if needed
		for (int i=0; i<POIList.size(); i++) {
			if (POIList.get(i).getPOIName().contains(targetPOIName)) {
				return POIList.get(i);
			}
		}
		return null;
	}
	 
}
