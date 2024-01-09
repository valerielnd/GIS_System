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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Floor;
import model.MapData2;
import model.POI;
import model.UserData;


/**
 * FXML Controller class for the edit floor page
 * It contains the functions to handle all the events in the view page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class EditFloorController implements Initializable{
    

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deleteBtn;


    @FXML
    private TextField floorNumber;

    @FXML
    private TextField mapFile;

    @FXML
    private Button saveBtn;


    
     /** Non FXML instance variables*/
    private MapData2 map2;
    
    private Stage previousStage;
    
    private Floor floorToEdit;
    
    private UserData userInfo;
    
    
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
     * Cancels the action of editing a floor
     * Loads the previous page
     * @param event the event detected when the user clicked on the cancel button
     */
    @FXML
    void cancel(ActionEvent event) {
        
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
      
        stage.close();

    }

    /**
     * Save the information entered by the user when editing a floor
     * Checks if the values provided by the user is correct
     * Updates the JSON metadata file trough the map file handler
     * @param event the event detected when the user clicked on the save button
     */
    @FXML
    void save(ActionEvent event) {
        
        boolean result = false;
        
        
        /** Gets the floor number */
        int floorNum;
        
        if(!floorNumber.getText().equals("")){
        
        
            floorNum = Integer.parseInt(floorNumber.getText());
        
        }
        else{
        
        
            floorNum = floorToEdit.getfloorNUm();
        
        
        }
        
       
       String file;
       
       if(!mapFile.getText().equals("")){
           
           file = mapFile.getText();
           
       }
       else{
       
           file = floorToEdit.getMapFile();
       
       }
       
       
       Floor newFloor = new Floor(map2.getMapName(), floorNum, file, floorToEdit.getPOIs());
       
       result = map2.getFile().editFloor(floorToEdit, newFloor, map2.getMapName());
       
       /** If the floor was edited successfully */
        if(result){
                    
            try {
                /** Load the previous page with the updated information */
                previousStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DevPage.fxml"));

                Parent root = loader.load();

                ((DevPageController)loader.getController()).setUserData(userInfo);
                
               
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
     * Deletes the information of a floor on the campus Map
     * @param event the event detected when the user clicked on the delete button
     */
    @FXML
    void delete(ActionEvent event) {
        
        boolean result;
        
        Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
        
        deleteAlert.setContentText("This will permantly delete the floor");
        
        ButtonType cancelButton = ButtonType.CANCEL;
        
        ButtonType okButton = ButtonType.OK;
        
        deleteAlert.getButtonTypes().setAll(cancelButton,okButton);
        
        Optional<ButtonType> resultAlert = deleteAlert.showAndWait();
        
        if(resultAlert.get()!= null && resultAlert.get() == ButtonType.OK){
            
            if(floorToEdit != null){
                
              
                
                result = map2.getFile().deleteFloor(floorToEdit, floorToEdit.getBldgName());
                
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
     * Sets the variable floorToEdit to get the provided floor
     * @param floor the floor to edit
     */
    public void setFloor(Floor floor){
    
        this.floorToEdit = floor;
        
       
    }
    
}
