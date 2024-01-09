/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the Add built-in POI page
 * It contains the functions to handle all the events in the view page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class AddBuiltInPOIController implements Initializable{
    
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
    private Label xerrorLbl;
    
    @FXML
    private Label yerrorLbl;
    
    @FXML
    private Label nameErrorLbl;
    
    @FXML
    private Label typeErrorLbl;
    
    @FXML
    private Label descErrorLbl;
    
    @FXML
    private Button selectOnMap;

    
    /** Non FXML instance variables*/
    private MapData2 map2;
    
    private Stage previousStage;
    
    
    private UserData userInfo;
    
    private Vec2 POIpos;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /** No initialization required*/
    } 
    
    /**
     * Cancel the action of adding a built-in POI
     * @param event the event detected when the user click on add POI
     */
    @FXML
    void cancel(ActionEvent event) {
        
        Stage stage = (Stage) cancelAddPOIBtn.getScene().getWindow();
      
        stage.close();


    }
    
    /**
     * Brings the user back to the main page to select the building location
     * @param event the event detected when the user clicks on select on map
     */
    @FXML
    void selectOnMap(ActionEvent event) {
        
       
        Stage stage = (Stage) selectOnMap.getScene().getWindow();
      
        stage.hide();

    }

    /**
     * Reads the information provided for the new POI
     * check if values are provided
     * save the new POI information
     * reloads the map once the POI is added
     * @param event the event detected when the user click on add POI
     */
    @FXML
    void save(ActionEvent event) {
        
        try{
        boolean result;
        
        String xPos;
        
        if(POIpos != null){
        
            xPos = Double.toString(POIpos.getx());
        
        }
        else{
            
            xPos = POIxPosTxt.getText().trim();
        }
        
        
        xerrorLbl.setText("");
        
        if(!xPos.equals("") || POIpos != null){
            
            if(isNumeric(xPos)){
        
                String yPos;
                
                if(POIpos != null){
        
                    yPos = Double.toString(POIpos.gety());
        
                }
                else{
            
                    yPos = POIyPosText.getText().trim();
                }
        
                yerrorLbl.setText("");
                
                if(!yPos.equals("")){
            
                    if(isNumeric(yPos)){
                        
                        String name = POINameText.getText().trim();
                        
                        nameErrorLbl.setText("");
                        
                        if(!name.equals("")){
                        
                            String bldgName = map2.getMapName();
                            
                            int floorNum = map2.getFloorNum();
                            
                            String type = POITypeText.getText().trim();
                            
                            typeErrorLbl.setText("");
                            
                            if(!type.equals("")){
                            
                                if(isNumeric(type)){
                                    
                                    String desc = POIDescText.getText().trim();
                                    
                                    descErrorLbl.setText("");
                                
                                   if(!desc.equals("")){
                                
                                       String bldgCode = getBldgCode(bldgName);
                                       
                                       String typeCode = getTypeCode(type);
                                       
                                       String currentName = bldgCode+"_"+String.valueOf(floorNum)+"_"+typeCode+"_"+name;
                                       
                                       boolean fav = favCheckBox.isSelected();
                                       
                                        if(!bldgCode.equals("") && !typeCode.equals("")){
                                       
                                            Vec2 location = new Vec2(Double.parseDouble(xPos),Double.parseDouble(yPos));

                                            POI POItoAdd = new POI(bldgName, floorNum, currentName, Integer.parseInt(type), desc, fav, location);

                                            if(map2.getMapName().equals("Campus Map")){
                                                
                                                POI campusPOI = new POI(bldgName, floorNum, name, Integer.parseInt(type), desc, fav, location);
                                            
                                                result = map2.getFile().addCampusPOI(bldgName, floorNum, campusPOI);
                                            }
                                            else{
                                                    result = map2.getFile().addBuiltInPOI(bldgName, floorNum, POItoAdd);
                                            }

                                            if(result){

                                                 try {

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
                                            else{
                                            
                                                Alert addAlert = new Alert(Alert.AlertType.ERROR);
        
                                                addAlert.setContentText("This POI exists already");
                                            
                                                addAlert.show();
                                            }
                                        }
                                        else{
                                        
                                            typeErrorLbl.setText("Invalid type");
                                        }
                                
                                    }
                                   else{
                                   
                                        descErrorLbl.setText("No description");
                                   
                                   }
                                
                                }
                                else{
                                
                                    typeErrorLbl.setText("Type not numeric");
                                
                                }
                            
                            
                            }
                            else{
                            
                                typeErrorLbl.setText("No type entered");
                            
                            }
                        
                        }
                        else{
                    
                            nameErrorLbl.setText("No name");
                        }
                    
                    
                    }
                    else{
                        
                        yerrorLbl.setText("y is not numeric");
                    
                    }
                }
                else{
                    yerrorLbl.setText("No y entered");
                }
            
            }
            else{
                xerrorLbl.setText("x is not numeric");
            
            }
        
            
        
        }
        else{
        
            xerrorLbl.setText("No x entered");
        
        }
        }
        catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Sets the variable previousStage to get the stage of the view calling the 
     * Add POI page
     * @param stage the Stage object to set as previousStage
     */
    public void setStage(Stage stage){
    
        this.previousStage = stage;
    }
    
    /**
     * Set the UserData object to retrieve the current suer data
     * @param userInfo the UserData containing the current user data
     */
    public void setUserData(UserData userInfo){
        
        this.userInfo = userInfo;
    
    } 
    
    
     /**
     * Sets the variable map2 to get the loaded map metadata info
     * @param map the MapData2 object to set as map2
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
     * Verify if a value is numeric
     * @param str the string containing the numeric value
     * @return true if the value is numeric or false otherwise
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
     * Returns the code associated with a building name
     * @param name the name of the building
     * @return the code of the building
     */
    private String getBldgCode(String name){
        
        String code = "";
        
        for (String word : name.split(" ")) {
                
                code = code + word.charAt(0);
            
        }
        
       return code;
    
    }
    
     /** 
     * Returns the code associated with a POI type
     * @param name the type of the POI
     * @return the code of the type
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
