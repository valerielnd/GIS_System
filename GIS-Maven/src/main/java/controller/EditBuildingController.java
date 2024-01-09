/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;



/**
 * FXML Controller class for the edit building  page
 * It contains the functions to handle all the events in the view page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class EditBuildingController implements Initializable{
  
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
    private Button deleteBtn;

    @FXML
    private Label firstPar;

    @FXML
    private Label mapNameLabel;

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
    private ComboBox<String> editFloorBldg;
    
   
    /** Non FXML instance variables*/
    private MapData2 map2;
    
    private Stage previousStage;
    
    private Building bldgToEdit;
    
    private Floor floorToEdit;
    
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
     * Cancels the action of editing a building
     * Loads the previous page
     * @param event the event detected when the user clicked on the cancel button
     */
    @FXML
    void cancel(ActionEvent event) {
        
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
      
        stage.close();

    }

    /**
     * Deletes the information of a building on the campus Map
     * Reloads the map once the building is deleted
     * @param event the event detected when the user clicked on the delete button
     */
    @FXML
    void delete(ActionEvent event) {
        
        try{
            boolean result;

            Alert deleteAlert = new Alert(Alert.AlertType.WARNING);

            deleteAlert.setContentText("This will permantly delete the Building");

            ButtonType cancelButton = ButtonType.CANCEL;

            ButtonType okButton = ButtonType.OK;

            deleteAlert.getButtonTypes().setAll(cancelButton,okButton);

            Optional<ButtonType> resultAlert = deleteAlert.showAndWait();

            if(resultAlert.get()!= null && resultAlert.get() == ButtonType.OK){

                if(bldgToEdit != null){

                    result = map2.getFile().deleteBuilding(bldgToEdit);

                    if(result){

                        try {

                            previousStage.close();

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DevPage.fxml"));

                            Parent root = loader.load();

                            ((DevPageController)loader.getController()).setUserData(userInfo);

                            Stage stage = (Stage) deleteBtn.getScene().getWindow();

                            Scene scene = new Scene(root);

                            stage.setScene(scene);

                            Image appIcon = new Image(getClass().getResourceAsStream("/images/Stacked.png"));

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
            }
            else if(resultAlert.get()!= null && resultAlert.get() == ButtonType.CANCEL){

                Stage stage = (Stage) deleteBtn.getScene().getWindow();

                stage.close();
            }

            }
            catch(MetadataFileException e){

                System.out.println(e.getMessage());
            }



    }

     /**
     * Used to edit a floor. Opens the edit floor page
     * @param event the event detected when the user clicked on the edit floors button
     */
    void editFloor() {
        
        /** Get the floor number selected*/
        String floorNum = editFloorBldg.getSelectionModel().getSelectedItem();
        
        int realFloorNum = Integer.parseInt(floorNum);
        
        for(int i = 0; i < bldgToEdit.getFloors().size(); i++){
        
            Floor bldgFloor = bldgToEdit.getFloors().get(i);
            
            if(bldgFloor.getfloorNUm() == realFloorNum){
            
                floorToEdit = bldgFloor;
            
            }
        
        
        }
        
        try {

            /** Load the delete POI view */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editFloor.fxml"));

            Parent root = loader.load();
            
            Stage thisStage = (Stage) cancelBtn.getScene().getWindow();
      
            thisStage .close();

            /** Transfer the current user data and the POI to edit to the page controller*/
            ((EditFloorController)loader.getController()).setUserData(userInfo);
            
            ((EditFloorController)loader.getController()).setMapData2(map2);

            //((EditFloorController)loader.getController()).setStage((Stage) saveBtn.getScene().getWindow());
            
            ((EditFloorController)loader.getController()).setStage(previousStage);
            
            if(floorToEdit!= null){
            
                ((EditFloorController)loader.getController()).setFloor(floorToEdit);
            
            }
     

             /**Create the new window for the new view with a new scene graph*/
            Stage stage = new Stage();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            Image appIcon = new Image(getClass().getResourceAsStream("/images/Stacked.png"));

            stage.getIcons().add(appIcon);

            stage.setTitle("GIS Edit Building");

            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        }
        catch (Exception e) {

            e.printStackTrace();
        }

    }

     /**
     * Save the information entered by the user when editing a Building 
     * Checks if the values provided by the user is correct
     * Updates the JSON metadata file trough the map file handler
     * @param event the event detected when the user clicked on the save button
     */
    @FXML
    void save(ActionEvent event) {
     
        boolean result = false;
	
	/** Get the Building x position */
        String xPos;
        
        if(Buildingpos != null){
        
            xPos = Double.toString(Buildingpos.getx());
        
        }
        else{
            
            xPos = BldgxPos.getText().trim();
        }
        
        
	xerrorLbl.setText("");
		
	double realX;
        
	
        /** If a x position was given or a position was selected on the map*/
        if(!xPos.equals("") || Buildingpos!= null){
            
            if(isNumeric(xPos)){
                
                realX = Double.parseDouble(xPos);
                
                
            }
            else{
                realX = -1;
                xerrorLbl.setText("x is not numeric");
                
            }
            
        }
        /** Else use the Building current x position */
	else{
            
            realX = bldgToEdit.getLocation().getx();
        }
		
       
		
        /** Get the Building y position */
		
	String yPos;
        
        if(Buildingpos != null){
        
            yPos = Double.toString(Buildingpos.gety());
        
        }
        else{
            
            yPos = bldgyPos.getText().trim();
        }
        
        
        yerrorLbl.setText("");
		
	double realY;
        
        /** If a y position was given or a position was selected on the map*/
        if((!yPos.equals("") || Buildingpos != null)){
            
            if(isNumeric(yPos)){
                 
                 realY = Double.parseDouble(yPos);

                
            }
            else{
                realY = -1;
                yerrorLbl.setText("y is not numeric");
             
             }
        }
        /** Else use the Building current y position */
        else{
		
	   realY = bldgToEdit.getLocation().gety();
	}
	
        /** Create the POI location on the map */
        Vec2 location = null;
        
        if(realX != -1 && realY != -1){
        
            location = new Vec2(realX, realY);
        
        }
        
        /** Get the Building type  */
	
        /** The type is 1 as it is a building */
		
	int realType = 1;
	
        
        
     	
        /** Get the Building name  */
        String name = bldgNameTxt.getText();
		
	String realName = "";
        
        String currentName = "";
	
        /** If the user enters a type */
        if(!name.equals("")){

            realName = name;

	}
        /** Else use the current building name */
        else{

            realName = bldgToEdit.getName();   
        }
		
		
		
        /** Get the Building desc  */
        String desc = bldgDesc.getText();

        String realDesc;

        /** If the user enters a POI description */
        if(!desc.equals("")){

            realDesc = desc;

        }
        /** Else use the building POI description */
        else{

            realDesc = bldgToEdit.getDescription();

        }
	
        if(!realName.equals("") && realType != 0 && location != null){
                   
            Building newBldg = new Building(realName, location, realDesc, bldgToEdit.getFloors());
   
            result = map2.getFile().editBuilding(bldgToEdit, newBldg);
        }
        
        /** If the building was edited successfully */
        if(result){
                    
            try {
                /** Load the previous page with the updated information */
                previousStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DevPage.fxml"));

                Parent root = loader.load();

                ((DevPageController)loader.getController()).setUserData(userInfo);
                
                if(map2 != null){
                
                   
                    
                    //System.out.println(map2.getMapName());
                    
                  
                }
                
                

                Stage stage = (Stage) saveBtn.getScene().getWindow();

                Scene scene = new Scene(root);

                stage.setScene(scene);

                Image appIcon = new Image(getClass().getResourceAsStream("/images/Stacked.png"));

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
    
    /**
     * Used to add a floor
     * Opens the add floor view page
     * @param event the event detected when the user clicked on the add button
     */
    @FXML
    void addFloor(ActionEvent event) {
        
        if(bldgToEdit != null){
        
            try {

            /** Load the delete POI view */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addFloor.fxml"));

            Parent root = loader.load();
            
            Stage thisStage = (Stage) cancelBtn.getScene().getWindow();
      
            thisStage .close();

            /** Transfer the current user data and the POI to edit to the page controller*/
            ((AddFloorController)loader.getController()).setUserData(userInfo);
            
            ((AddFloorController)loader.getController()).setMapData2(map2);

            //((AddFloorController)loader.getController()).setStage((Stage) saveBtn.getScene().getWindow());
            
            ((AddFloorController)loader.getController()).setStage((previousStage));
            
            if(bldgToEdit!= null){
            
                ((AddFloorController)loader.getController()).setBuilding(bldgToEdit);
            
            }
     

             /**Create the new window for the new view with a new scene graph*/
            Stage stage = new Stage();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            Image appIcon = new Image(getClass().getResourceAsStream("/images/Stacked.png"));

            stage.getIcons().add(appIcon);

            stage.setTitle("GIS add Floor");

            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        
        }

    }

    /**
     * Lets the user go back to the previous page to select the Building position 
     * on the Campus map
     * @param event the event detected when the user clicked on the button
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
     * Sets the variable buildingToEdit to get the provided building
     * @param buildingToEdit the building to edit
     */
    public void setBuilding(Building buildingToEdit){
    
        this.bldgToEdit = buildingToEdit;
        
        mapNameLabel.setText(buildingToEdit.getName());
        
         ObservableList<String> floorList = FXCollections.observableArrayList();
        
        for(int i = 0; i < buildingToEdit.getFloors().size(); i++){
            
            String floorNum = Integer.toString(buildingToEdit.getFloors().get(i).getfloorNUm());
            
            floorList.add(floorNum);
            
        }
        
        editFloorBldg.setItems(floorList);
        
         /** Add a listener to the floor combo box to detect event*/
         editFloorBldg.getSelectionModel().selectedItemProperty().addListener((options,oldValue,newValue)-> {
                   
                   
            if(newValue != null){

                editFloor();
            }
         });
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
