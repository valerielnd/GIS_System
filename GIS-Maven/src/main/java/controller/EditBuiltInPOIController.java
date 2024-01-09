/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the edit Built-in POI page
 * It contains the functions to handle all the events in the view page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class EditBuiltInPOIController implements Initializable{
    

    @FXML
    private TextField POIDescText;


    @FXML
    private TextField POINameText;

    @FXML
    private TextField POITypeText;

    @FXML
    private TextField POIxPosTxt;

    @FXML
    private TextField POIyPosText;

    @FXML
    private Button cancelAddPOIBtn;

    @FXML
    private CheckBox favCheckBox;

    @FXML
    private Label mapNameLabel;


    @FXML
    private Button savePOIBtn;

    @FXML
    private Button selectOnMap;

    @FXML
    private Label typeErrorLbl;

    @FXML
    private Label xerrorLbl;

    @FXML
    private Label yerrorLbl;

    
    /** Non FXML instance variables*/
    private MapData2 map2;
    
    private Stage previousStage;
    
    private POI POItoEdit;
    
    private UserData userInfo;
    
    private Vec2 POIpos;
    
    
    /**
    * Initializes the controller page
    * @param url
    * @param rb 
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /** No initialization required*/
    }
    
    /**
     * Cancels the action of editing a POI
     * Loads the previous page
     * @param event the event detected when the user clicked on the save button
     */
    @FXML
    void cancel(ActionEvent event) {
        
        Stage stage = (Stage) cancelAddPOIBtn.getScene().getWindow();
      
        stage.close();

    }

    /**
     * Save the information entered by the user when editing a POI 
     * Checks if the value provided by the user is correct
     * Updates the JSON metadata file trough the map file handler
     * @param event the event detected when the user clicked on the save button
     */
    @FXML
    void save(ActionEvent event) {
        
        
        try{
        
        boolean result = false;
		
	String bldgName = map2.getMapName();
                            
        int floorNum = map2.getFloorNum();	
        
	/** Get the POI x position */
        String xPos;
        
        if(POIpos != null){
        
            xPos = Double.toString(POIpos.getx());
        
        }
        else{
            
            xPos = POIxPosTxt.getText().trim();
        }
        
        
	xerrorLbl.setText("");
		
	double realX;
        
	
        /** If a x position was given or a position was selected on the map*/
        if(!xPos.equals("") || POIpos != null){
            
            if(isNumeric(xPos)){
                
                realX = Double.parseDouble(xPos);
                
                
            }
            else{
                realX = -1;
                xerrorLbl.setText("x is not numeric");
                
            }
            
        }
        /** Else use the POI current x position */
	else{
            
            realX = POItoEdit.getLocation().getx();
        }
		
       
		
        /** Get the POI y position */
		
	String yPos;
        
        if(POIpos != null){
        
            yPos = Double.toString(POIpos.gety());
        
        }
        else{
            
            yPos = POIyPosText.getText().trim();
        }
        
        
        yerrorLbl.setText("");
		
	double realY;
        
        /** If a y position was given or a position was selected on the map*/
        if((!yPos.equals("") || POIpos != null)){
            
            if(isNumeric(yPos)){
                 
                 realY = Double.parseDouble(yPos);

                
            }
            else{
                realY = -1;
                yerrorLbl.setText("y is not numeric");
             
             }
        }
        /** Else use the POI current y position */
        else{
		
	   realY = POItoEdit.getLocation().gety();
	}
	
        /** Create the POI location on the map */
        Vec2 location = null;
        
        if(realX != -1 && realY != -1){
        
            location = new Vec2(realX, realY);
        
        }
        
        /** Get the POI type  */
	String type = POITypeText.getText();
		
	int realType = 0;
	
        /** If a type was entered by the user */
	if(!type.equals("")){
            
            if(isNumeric(type)){
		
                realType = Integer.parseInt(type);
            }
            else{
            
                 typeErrorLbl.setText("Type not numeric");
            }
		
	}
         /** Else use the POI current type */
	else{
		
            realType = POItoEdit.getType();
		
	}
        
     	
        /** Get the POI name  */
        String name = POINameText.getText();
		
	String realName = "";
        
        String currentName = "";
	
        /** If the user enters a type */
        if(!name.equals("")){

            /** Find the building and type code */
            String bldgCode = getBldgCode(bldgName);

            String typeCode = getTypeCode(Integer.toString(realType));
            
            /** If a valid building and type codes was found */
            if(!bldgCode.equals("") && !typeCode.equals("")){

                        realName = name;

                        currentName  = bldgCode+"_"+String.valueOf(floorNum)+"_"+typeCode+"_"+name;
            }
            else{
                
               
                typeErrorLbl.setText("Invalid type");
                

            }
		
		
	}
        /** Else use the current POI name */
        else{

                realName = POItoEdit.getPOIName();
                currentName = POItoEdit.getPOIName();
        }
		
		
		
        /** Get the POI desc  */
        String desc = POIDescText.getText();

        String realDesc;

        /** If the user enters a POI description */
        if(!desc.equals("")){

            realDesc = desc;

        }
        /** Else use the current POI description */
        else{

            realDesc = POItoEdit.getDesc();

        }
	
        /** Get the POI favorite value  */
        boolean fav = favCheckBox.isSelected();
        
        /** If the POI is on the campus Map */
        if(map2.getMapName().equals("Campus Map")){

                if(!realName.equals("") && realType != 0 && location != null){
                   

                    POI POIUpdated = new POI(bldgName, floorNum, realName, realType, realDesc, fav, location);

                    result = map2.getFile().updateCampusPOI(bldgName, floorNum, POItoEdit, POIUpdated);
                }
        }
        /** If the POI is on the other buildings on the map */
        else{
                
                if(!currentName.equals("") && realType != 0 && location != null){
                    
                    POI POIUpdated = new POI(bldgName, floorNum, currentName, realType, realDesc, fav, location);
                    
                   
                    result = map2.getFile().updateBuiltInPOI(bldgName, floorNum, POItoEdit,POIUpdated);
                }
        }
        
        /** If the POI was edited successfully */
        if(result){
                    
            try {
                /** Load the previous page with the updated information */
                previousStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DevPage.fxml"));

                Parent root = loader.load();

                ((DevPageController)loader.getController()).setUserData(userInfo);

                Stage stage = (Stage) savePOIBtn.getScene().getWindow();

                Scene scene = new Scene(root);

                stage.setScene(scene);

                Image appIcon = new Image("/images/Stacked.png");

                stage.getIcons().add(appIcon);

                stage.setTitle("GIS Developer Page");

                stage.setResizable(true);

                stage.setMaximized(true);

                stage.show();

            } catch (IOException ex) {

                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
        catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }

    }

    /**
     * Lets the user go back to the previous page to select the POI position 
     * on the map
     * @param event the event detected when the user clicked on the button
     */
    @FXML
    void selectOnMap(ActionEvent event) {
        
        Stage stage = (Stage) selectOnMap.getScene().getWindow();
      
        stage.hide();

    }
    
    /**
     * Sets the variable previousStage to get the stage of the view calling the 
     * "addPOI" page
     * @param stage the Stage object to set as previousStage
     */
    public void setStage(Stage stage){
    
        this.previousStage = stage;
    }
    
    /**
     * Sets the variable userInfo to get the data about the current user 
     * of the app
     * @param userInfo the UserData object to set as userInfo
     */
    public void setUserData(UserData userInfo){
        
        this.userInfo = userInfo;
    
    } 
    
     /**
     * Sets the variable map to get the map metadata of the app
     * @param map the MapData2object to set as map
     */
    public void setMapData2(MapData2 map){
    
       this.map2 = map;
       
       
       
       mapNameLabel.setText(map2.getMapName());
       
    
    }
    
    /**
     * Sets the variable POIpos to get the Position of POI chosen by the user
     * on the map
     * @param pos the position of the POI
     */
    public void setPOIpos(Vec2 pos){
    
        this.POIpos = pos;
    }
    
    /**
     * Sets the variable rPoi to get the POI details of the POI to edit
     * @param rPOI the POI to edit
     */
    public void setPOI(POI rPOI){
        
        this.POItoEdit = rPOI;
        
    
    }
    
    /**
     * Checks if the value of String variable is numeric
     * Used to verify that the user provides numeric variables
     * @param str the string to check
     * @return true if the variable value is numeric or false otherwise
     */
    private Boolean isNumeric(String str) { 
        try {  
            double parseDouble = Double.parseDouble(str);  
            return true;
        } 
        catch(NumberFormatException e){  
          return false;  
        }  
    }
    
     /**
     * Gets the code associated to a building name
     * The code is used when creating the name of the POI to edit
     * @param str the name of the building where the POI is located
     * @return the code associated with the building
     */
    private String getBldgCode(String name){
        
        String code = "";
        
        for (String word : name.split(" ")) {
                
                code = code + word.charAt(0);
            
        }
        
       return code;
    
    }
    
    /**
     * Gets the code associated to a POI type
     * The code is used when creating the name of the POI to edit
     * @param str the name of the type of the POI
     * @return the code associated with the type
     */
    private String getTypeCode(String type){
        
        String code;
    
        switch (type) {
            
           case "1" -> code = "B"; 
           
           case "2" -> code = "C";
           
           case "3" -> code = "W";
           
           case "4" -> code = "L";
           
           case "5" -> code = "CR";
           
           case "6" -> code = "TA";
           
           case "7" -> code = "MR";
           
           case "8" -> code = "MUR";
           
           case "9" -> code = "R";
           
           case "10" -> code = "H";
           
           case "11" -> code = "E";
           
           case "12" -> code = "T";
           
           case "13" -> code = "S";
           
           case "14" -> code = "RT";
         
           case "15" -> code = "Ex";
           
           default -> { code = "";
           }
       }
    
       return code;
    
    }

}
