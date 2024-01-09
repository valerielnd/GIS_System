/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * This class acts as a model to load the data from the metadata JSON file
 * Constructs the current User object with the data loaded from the file 
 * It also updates the data in the map JSON file when a user modifies a 
 * its password. It will be used by the controllers to handle users 
 * interactions with the views.
* <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class UserData {
    
    /** User object to contain the current user data */
    private User currentUser;
    
    /** An instance of fileHandler */
    private FileHandler userFile;
    
     /** 
     * Constructor of an instance of UserData
     * @param email the email of the current user
     * @param filename the name of the metadata JSON file
     */
    public UserData(String email, String filename){
        
        try{
        
            userFile = new FileHandler(filename);

            this.currentUser = userFile.getUserData(email);
        }
        catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }
        
    }
    
    /** 
     * Sets a MapData object using the provided FileHandler and User
     * @param file the FileHandler object of UserData
     * @param user the User object of UserData
     */
    public void setUserData(FileHandler file, User user){
        
        this.currentUser = user;
        
        this.userFile = file;
    
    }
    
    /** 
     * Returns the User object of UserData
     * @return the User Object
    */
    public User getCurrentUser(){
     
        return this.currentUser;
    }
    
     /** 
     * Returns the FileHandler object of UserData
     * @return FileHandler the FileHandler object
    */
    public FileHandler getUserFile(){
        return this.userFile;
    }
    
}

