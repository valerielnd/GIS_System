/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * POI entity for storing information about a point of interest. 
 * <br><br>
 * Entity can be initialized with POI (building name, floor number, POI name, POI type, description, and favorite (T/F), location) <br>
 * Get/Set functions can be used with Bldg(building name), Floor(floor number), Name(POI name), Type(POI type), Desc(POI description), and Location(x,y coordinate @see Vec2). <br>
 * Favorite status can be checked with the {@link isFav} method and status can be alters with the {@link setFav} method. <br>
 * User created is a special type (POItype = 16) that can be check with the {@link isUserCreated} method.
 * <br><br>
 * 
 * <br>Example Use: <br>
 * <pre>
 * {@code
 * 		POI myNewPOI = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
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

public class POI {
	/**The name of the building containing POI**/
	private String POIBldgName; 
	/**The floor number containing the POI **/
	private int POIFloorNum;
	/**The name of the POI **/
	private String POIName;
	/**The type of the POI. Designated Types: <i>
	 * <br> 1: "Building",
	 * <br> 2: "Classroom", C 
	 * <br> 3: "Washroom", W
	 * <br> 4: "Lab", L
	 * <br> 5: "Collab room", CR
	 * <br> 6: "TA room", TA
	 * <br> 7: "Mech room", MR
	 * <br> 8: "Multiple use room", MUR
	 * <br> 9: "Room", R
	 * <br> 10: "Hallway", H
	 * <br> 11: "Elevator", E
	 * <br> 12: "Terrace", T
	 * <br> 13: "Stairs", S
	 * <br> 14: "Restaurant" RT
	 * <br> 15: "Exit", EX
	 * <br> 16: "User Created", U
	 * </i>
	 */
	private int POIType;
	/**Description of the POI**/
	private String POIDescription;
	/**Location of the POI on the map (as a vector - @see Vec2)**/
	private Vec2 POILocation; 
	/**Determines if POI has been favorited by the user (0=not favorite, 1=favorite) **/
	private Boolean POIFav;
	
	/**
     * POI constructor. Creates a new POI object. <br>
     * @param iniPOIBldgName The name of the building containing POI
     * @param iniPOIFloorNum The floor number containing the POI 
     * @param iniPOIName The name of the POI 
     * @param iniPOIType The type of the POI. See {@link POIType} for type designations. 
     * @param iniPOIDescription Location of the POI on the map (as a vector - @see Vec2)
     * @param iniPOIFav Description of the POI
     * @param iniPOILocation Determines if POI has been favorited by the user (0=not favorite, 1=favorite) 
     */
	public POI(String iniPOIBldgName, int iniPOIFloorNum, String iniPOIName, int iniPOIType, String iniPOIDescription, Boolean iniPOIFav, Vec2 iniPOILocation) {
		this.POIBldgName = iniPOIBldgName; 
		this.POIFloorNum = iniPOIFloorNum; 
		this.POIName = iniPOIName; 
		this.POIType = iniPOIType;
		this.POILocation = iniPOILocation;
		this.POIDescription = iniPOIDescription;
		this.POIFav = iniPOIFav; 		
	}
	
	/** @return POIBldgName name of the building that contains the POI**/
	public String getBldg() { return POIBldgName; }
	/** Reassigns the name of the building containing the POI. <br>
        * @param newPOIBldgName The name of the new building (string) containing the POI
        **/
        public void setBldg(String newPOIBldgName) { POIBldgName = newPOIBldgName; }
	
	/** @return POIFloorNum floor number that contains the POI**/
        public int getFloor() { return POIFloorNum; }
        /** Reassigns the floor number containing the POI. <br>
        * @param newPOIFloorNum The name of the new floor number
        **/
	public void setFloor(int newPOIFloorNum) { POIFloorNum = newPOIFloorNum; }
	
        /** @return POIName name of the POI**/
	public String getPOIName() { return POIName; }
        /** Reassigns the POI name. <br>
        * @param newPOIName The new name of the POI
        **/
	public void setPOIName(String newPOIName) { POIName = newPOIName; }
	
        /** @return POIType type of the POI**/
	public int getType() { return POIType; }
        /** Reassigns the type of the POI. <br>
        * @param newPOIType The new type of the POI
        **/
	public void setType(int newPOIType) { POIType = newPOIType; }
	
        /** @return POIDescription description of the POI **/
	public String getDesc() { return POIDescription; }
        
        /** Reassigns the description of the POI. <br>
        * @param newPOIDescription The new type of the POI
        **/
	public void setDesc(String newPOIDescription) { POIDescription = newPOIDescription; }
	
	/** @return POILocation location of the POI**/
        public Vec2 getLocation() { return POILocation; }
        
        /** Reassigns the location of the POI. <br>
        * @param newPOILocation The new location of the POI
        **/
	public void setLocation(Vec2 newPOILocation) { POILocation = newPOILocation; }
		
	/** @return POIFav Checks if the POI has been favorited by the user **/
	public Boolean getFav() { return POIFav; }
	
        /** @return true if the POI is user created **/
	public Boolean isUserCreated() { return POIType==16; }
        
        /** Changes favorite status
        * @param newPOIFav new favorite status**/
	public void setPOIFav(Boolean newPOIFav) { POIFav = newPOIFav; }
	
	/** Checks if two POIs have the same name.
	 * <br><br><b>Example Use: </b> {@code	System.out.print(myNewPOI.isEqual(otherPOI)); }
	 * @param POI2 The POI being compared to this one
	 * @return true if the two POI have the same name
	 **/
	public boolean isEqual(POI POI2) {
            return (POI2.getPOIName().equals(this.POIName))&& (POI2.POIBldgName.equals(this.POIBldgName)) && (POI2.POIFloorNum==this.POIFloorNum) &&(POI2.POILocation.isEqual(this.POILocation)); }
        
	
}
