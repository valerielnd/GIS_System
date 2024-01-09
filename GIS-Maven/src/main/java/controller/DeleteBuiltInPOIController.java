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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the delete built-in POI page
 * It contains the functions to handle all the events in the view page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class DeleteBuiltInPOIController implements Initializable{
    
    @FXML
    private Label POINameLabel;



    @FXML
    private Button cancelDelPOIBtn;

    @FXML
    private Button deletePOIBtn;
    
    /** Non FXML instance variables*/
    private MapData2 map2;
    
    private Stage previousStage;
    
    private POI POItoDel;
    
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
     * Cancel the action of deleting the built-in POI
     * @param event the event detected when the user selects cancel 
     */
    @FXML
    void cancel(ActionEvent event) {
        
        Stage stage = (Stage) cancelDelPOIBtn.getScene().getWindow();
      
        stage.close();

    }
    
     /**
     * Deletes the built-in POI
     * Confirms the deletion before deleting the POI
     * reloads the map once the POI is deleted
     * @param event the event detected when the user click on delete
     */
    @FXML
    void delete(ActionEvent event) {
        
        try{
        User currentUser = userInfo.getCurrentUser();
        
        boolean result;
        
        Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
        
        deleteAlert.setContentText("This will permantly delete the Built-IN POI");
        
        ButtonType cancelButton = ButtonType.CANCEL;
        
        ButtonType okButton = ButtonType.OK;
        
        deleteAlert.getButtonTypes().setAll(cancelButton,okButton);
        
        Optional<ButtonType> resultAlert = deleteAlert.showAndWait();
        
        if(resultAlert.get()!= null && resultAlert.get() == ButtonType.OK){
            
            if(POItoDel != null){
                
                String bldgName = map2.getMapName();
             
                int floorNum = map2.getFloorNum();
                
                if(bldgName.equals("Campus Map")){

                    result = map2.getFile().deleteCampusPOI(bldgName, floorNum, POItoDel);
                }
                else{
                    result = map2.getFile().deleteBuiltInPOI(bldgName, floorNum, POItoDel);
                }
              

                if(result){
                    
                    try {

                        previousStage.close();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DevPage.fxml"));

                        Parent root = loader.load();

                        ((DevPageController)loader.getController()).setUserData(userInfo);

                        Stage stage = (Stage) deletePOIBtn.getScene().getWindow();

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
        }
        else if(resultAlert.get()!= null && resultAlert.get() == ButtonType.CANCEL){

            Stage stage = (Stage) deletePOIBtn.getScene().getWindow();

            stage.close();
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
     * Used to receive the POI object to delete from the calling page
     * @param rPOI the POI to delete
     */
    public void setPOI(POI rPOI){
        
        this.POItoDel = rPOI;
        
        POINameLabel.setText(POItoDel.getPOIName());
        
    
    }
    
    /**
     * Sets the variable map2 to get the loaded map metadata info
     * @param mapInfo the MapData2 object to set as map2
     */
    public void setMapData2(MapData2 map){
    
       this.map2 = map;
       
       
      
    }
}
