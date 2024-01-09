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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the Add POI page
 * It contains the functions to handle all the events in the MainAdd POI
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 4.0
 */
public class AddPOIController implements Initializable {

    
    @FXML
    private GridPane addPOIPane;
    
    @FXML
    private Label mapNameLabel;
    
    @FXML
    private Button selectOnMapBtn;
    
    @FXML
    private TextField POINameText;
    
    @FXML
    private CheckBox favCheckBox;
    
    @FXML
    private TextField POIDescText;
   
    private MapData2 map2;
    
    private Stage previousStage;
    
    private Vec2 POIpos;
    
    private UserData userInfo;
    
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /** No initialization required*/
    } 
    
    /**
     * Provides actions when a user click the "Select on Map" button in the 
     * add POI page
     * @param event the ActionEvent created when the user clicked on the button
     */
    @FXML
    void SelectPOI(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));
            
        Parent root = loader.load();
            
        ((MainPageController)loader.getController()).setAdd(true);
        
        Stage stage = (Stage) selectOnMapBtn.getScene().getWindow();
      
        stage.hide();
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
     * Sets the variable previousStage to get the stage of the view calling the 
     * Add POI page
     * @param stage the Stage object to set as previousStage
     */
    public void setStage(Stage stage){
    
        this.previousStage = stage;
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
     * Cancel the action of adding a built-in POI
     * @param event the event detected when the user click on add POI
     */
    @FXML
    void cancel(ActionEvent event){
        
        Stage stage = (Stage) selectOnMapBtn.getScene().getWindow();
      
        stage.close();
    
    
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
        
        if(POIpos != null){
   
             String bldgName = map2.getMapName();
             
             int floorNum = map2.getFloorNum();
             
             String name = POINameText.getText();
             
             if(!name.equals("")){
             
                 String desc = POIDescText.getText();
                 
                 if(!desc.equals("")){
                     
                     int type = 15;
                     
                     boolean fav;
                     
                     if(favCheckBox.isSelected()){
                      
                         fav = true;
                     
                     }
                     else{
                     
                         fav = false;
                     
                     }
                 
                     POI newPOI = new POI(bldgName,floorNum,name,type,desc,fav,POIpos);
                 
                     if(userInfo != null){
                         
                        User currentUser = userInfo.getCurrentUser();

                        userInfo.getCurrentUser().addToList(newPOI);

                    

                        User updateUser = new User(currentUser.getEmail(),currentUser.getPassword(),currentUser.getSecQuestion(),currentUser.getSecAnswer(), 0);

                        updateUser.setPOIList(currentUser.getPOIs());

                        Boolean result = userInfo.getUserFile().UpdateUser(currentUser, updateUser);
                         
                        if(result){
                             
                            if(!currentUser.isDeveloper()){
                                
                                try {

                                        previousStage.close();

                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));

                                        Parent root = loader.load();

                                        ((MainPageController)loader.getController()).setUserData(userInfo);

                                        Stage stage = (Stage) addPOIPane.getScene().getWindow();

                                        Scene scene = new Scene(root);

                                        stage.setScene(scene);

                                        Image appIcon = new Image("/images/Stacked.png");

                                        stage.getIcons().add(appIcon);

                                        stage.setTitle("GIS Main Page");

                                        stage.setResizable(true);

                                        stage.setMaximized(true);

                                        stage.show();

                                   } catch (IOException ex) {

                                       Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                            }
                            else{
                            
                                try {

                                        previousStage.close();

                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DevPage.fxml"));

                                        Parent root = loader.load();

                                        ((DevPageController)loader.getController()).setUserData(userInfo);

                                        Stage stage = (Stage) addPOIPane.getScene().getWindow();

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
                        else{

                            Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                            layerAlert.setTitle("Add POI");

                            layerAlert.setContentText("POI not saved");

                            layerAlert.show();
                        }
                     }
                 }
                 else{
                 
                    Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                    layerAlert.setTitle("Add POI");

                    layerAlert.setContentText("No Description provided");

                    layerAlert.show();
                 
                 
                 }
             
             }
             else{
             
                 Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                 layerAlert.setTitle("Add POI");

                 layerAlert.setContentText("No POI name provided");

                 layerAlert.show();
             
             
             }
             
        }
        }catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }
        
    }

/**
* Set the UserData object to retrieve the current suer data
* @param userInfo the UserData containing the current user data
*/ 
   public void setUserData(UserData userInfo){
        
        this.userInfo = userInfo;
    
    } 
}

