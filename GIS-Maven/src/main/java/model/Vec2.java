/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Defines the coordinates location of a POI or a Building on a map
 * Contains two coordinates x and y
 * <br><br>
 * 
 * <br>Example Use: <br>
 * <pre>
 * {@code
 * 		Vec2 POILocation = new Vec2(2,3)
 *              System.out.println(POILocation.getx())
 * }
 * </pre>
 * 
 * <b>Example Output:</b>  <code>2</code>
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson  
 */
public class Vec2 {
    
    /** The x coordinates of the object */
    private double x;
    
    /** The y coordinates of the object */
    private double y;
    
    /** Constructor of an object of type Vec2 
     * @param xcoord the int x coordinates
     * @param ycoord the int y coordinates
     */
    public Vec2(int xcoord, int ycoord){
        this.x = xcoord;
        this.y = ycoord;
    }
    
     /** Constructor of an object of type Vec2 
     * @param xcoord the double x coordinates
     * @param ycoord the double y coordinates
     */
    public Vec2(double xcoord, double ycoord){
        this.x = xcoord;
        this.y = ycoord;
    }
    
    
    /**
     * Gets the object x coordinates
     * @return the x coordinates
     */
    public double getx(){
        return this.x;
    }
    
     /**
     * Gets the object y coordinates
     * @return the y coordinates
     */
    public double gety(){
        return this.y;
    }
    
    
    /**
    * Gets the object x coordinates
    * @param xcoord the x coordinates
    */
    public void setx(double xcoord){
        this.x = xcoord;
    }
    
    /**
    * Gets the object y coordinates
    * @param ycoord the x coordinates
    */
    public void sety(double ycoord){
        this.y = ycoord;
    }
    
    /** 
     * Verifies if two locations are equals
     * @param otherLocation the other location to compare with this one
     * @return true if they are, otherwise false
     */
    public Boolean isEqual(Vec2 otherLocation){
        if(otherLocation.x == this.x){
            if(otherLocation.y == this.y){
            return true;
            }
        }
        return false;
    }
    
}

