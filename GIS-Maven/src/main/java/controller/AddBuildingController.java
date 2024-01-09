/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the Add Building page
 * It contains the functions to handle all the events in the view page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class AddBuildingController implements Initializable{
    
    @FXML
    private TextField BldgxPos;

    @FXML
    private TextField bldgDesc;

    @FXML
    private TextField bldgNameTxt;

    @FXML
    private TextField bldgyPos;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label comma;

    @FXML
    private Label descErrorLbl;

    @FXML
    private Label firstPar;

    @FXML
    private Button saveBtn;

    @FXML
    private Label secondPar;

    @FXML
    private Button selectOnMap;

    @FXML
    private Label xValue;

    @FXML
    private Label xerrorLbl;

    @FXML
    private Label yValue;

   @FXML
    private Label yerrorLbl;

    @FXML
    private Label namErrorLbl;
    
    
    /** Non FXML instance variables*/
    private MapData2 map2;
    
    private Stage previousStage;
    
    private UserData userInfo;
    
    private Vec2 Buildingpos;
    
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
     * Cancel the action of adding a building
     * @param event the event detected when the user click on add building
     */
    @FXML
    void cancel(ActionEvent event) {
        
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
      
        stage.close();

    }

     /**
     * Reads the information provided for the new building
     * check if values are provided
     * save the new building information
     * reloads the map once the building is added
     * @param event the event detected when the user click on add building
     */
    @FXML
    void save(ActionEvent event) {
        
        boolean result = false;
        
        boolean exist = false;
        
        String xPos;
        
        try{
        
        if(Buildingpos != null){
        
            xPos = Double.toString(Buildingpos.getx());
        
        }
        else{
           
            xPos =  BldgxPos.getText().trim();
        }
        
        
        xerrorLbl.setText("");
        
        if(!xPos.equals("") || Buildingpos!= null){
            
            if(isNumeric(xPos)){
        
                String yPos;
                
                if(Buildingpos != null){
        
                    yPos = Double.toString(Buildingpos.gety());
        
                }
                else{
                     
                    yPos =  bldgyPos.getText().trim();
                }
        
                yerrorLbl.setText("");
                
                if(!yPos.equals("")){
            
                    if(isNumeric(yPos)){
                        
                        String name = bldgNameTxt.getText().trim();
                        
                        namErrorLbl.setText("");
                        
                        if(!exist){
                        if(!name.equals("")){
                            
                                    ArrayList<Building> campBuildings = new ArrayList();
                                    
                                    for(int k =0; k < campBuildings.size() ; k++){
                                    
                                        if(campBuildings.get(k).getName().equals(name)){
                                        
                                            exist = true;
                                        
                                        }
                                    }
                        
                                    String desc = bldgDesc.getText().trim();
                                    
                                    descErrorLbl.setText("");
                                
                                   if(!desc.equals("")){
                                
                                     
                                            Vec2 location = new Vec2(Double.parseDouble(xPos),Double.parseDouble(yPos));

                                            Building bldgtoAdd = new Building(name, location, desc);
                                            
                                            result = map2.getFile().addBuilding(bldgtoAdd);

                                            if(result){

                                                 try {

                                                     previousStage.close();

                                                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DevPage.fxml"));

                                                     Parent root = loader.load();

                                                     ((DevPageController)loader.getController()).setUserData(userInfo);

                                                     Stage stage = (Stage) saveBtn.getScene().getWindow();

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
        
                                                addAlert.setContentText("This Building exists already");
                                            
                                                addAlert.show();
                                            }
                                        
                                
                                    }
                                   else{
                                   
                                         Alert addAlert = new Alert(Alert.AlertType.ERROR);
        
                                         addAlert.setContentText("No description");
                                            
                                          addAlert.show();
                                   
                                   }
                            }
                        else{
                        
                                Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                                layerAlert.setTitle("Add Building");

                                layerAlert.setContentText("No name entered");
                                
                                layerAlert.show();
                        
                        }
                        }
                        else{
                    
                            Alert addAlert = new Alert(Alert.AlertType.ERROR);
        
                            addAlert.setContentText("No Building name");

                            addAlert.show();
                        }
                    
                    
                    }
                    else{
                        
                        Alert addAlert = new Alert(Alert.AlertType.ERROR);
        
                            addAlert.setContentText("Y is not numberic");

                             addAlert.show();
                    
                    }
                }
                else{
                    
                    
                    Alert addAlert = new Alert(Alert.AlertType.ERROR);
        
                    addAlert.setContentText("No y entered");

                     addAlert.show();
                    
                }
            
            }
            else{
               
                    Alert addAlert = new Alert(Alert.AlertType.ERROR);
        
                    addAlert.setContentText("x is not numeric");

                     addAlert.show();
            
            }
        
            
        
        }
        else{
       
            
            Alert addAlert = new Alert(Alert.AlertType.ERROR);
        
            addAlert.setContentText("No x entered");

             addAlert.show();
            
        
        }
        }
        catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }

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
    
     /** Non FXML methods */
    
     /**
     * Sets the variable previousStage to get the stage of the view calling the Add POI page 
     * @param stage the Stage object to set as previousStage
     */
    public void setStage(Stage stage){
    
        this.previousStage = stage;
    }
    
    /**
     * Sets the variable userInfo to get the current user data
     * @param  userInf the UserData object to set as userInfo
     */
    public void setUserData(UserData userInf){
        
        this.userInfo = userInf;
    
    } 
  
    /**
     * Sets the variable map2 to get the loaded map metadata info
     * @param mapInfo the MapData2 object to set as map2
     */
    public void setMapData2(MapData2 mapInfo){
    
       this.map2 = mapInfo;
       
      
      
       
    
    }
    
    /**
     * Sets the variable Buildingpos to get the Position of Building chosen by the user on the map
     * @param pos the position of the Building
     */
    public void setBldgpos(Vec2 pos){
        
       
    
        this.Buildingpos = pos;
        
        if( Buildingpos != null){
        
            firstPar.setText("(");
        
            xValue.setText(Double.toString(Math.round(Buildingpos.getx())));
        
            yValue.setText(Double.toString(Math.round(Buildingpos.gety())));
            
            comma.setText(",");
            
            secondPar.setText(")");
        }
        
        
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
