/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the edit POI page
 * It contains the functions to handle all the events in the view page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class EditPOIController implements Initializable {
    
    @FXML
    private TextField POIDescText;

    @FXML
    private TextField POINameTxtField;

    @FXML
    private Label POINameLabel;

    @FXML
    private Button cancelEditPOIBtn;

    @FXML
    private CheckBox favCheckBox;

    @FXML
    private Button savePOIBtn;
    
    private Stage previousStage;
    
    private POI POItoEdit;
    
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
     * Cancel the action of editing a POI
     * @param event the event detected when the user click on add building
     */
    @FXML
    void cancel(ActionEvent event) {
        
        Stage stage = (Stage) cancelEditPOIBtn.getScene().getWindow();
      
        stage.close();
  
    }

    /**
     * Reads the information provided for the POI
     * check if values are provided
     * save the new POI information
     * reloads the map once the POI is updated
     * @param event the event detected when the user click on save button
     */
    @FXML
    void save(ActionEvent event) {
        
        // create an alert
        Alert saveAlert = new Alert(AlertType.NONE);
        
        String name = POINameTxtField.getText();
        
        String desc = POIDescText.getText();
        
        Boolean favValue = favCheckBox.isSelected();
        
        User currentUser = userInfo.getCurrentUser();
        
        try{   
            if(((!name.equals(POItoEdit.getPOIName())) && (!name.equals(""))) || ((!desc.equals(POItoEdit.getDesc())) && (!desc.equals(""))) || (!Objects.equals(favValue, POItoEdit.getFav()))){

                currentUser.getPOIs().remove(POItoEdit);

                if(!name.equals(POItoEdit.getPOIName()) && (!name.equals(""))){

                    POItoEdit.setPOIName(name);
                }

                if(!desc.equals(POItoEdit.getDesc()) && (!desc.equals(""))){

                   

                    POItoEdit.setDesc(desc);
                }

                if(!Objects.equals(favValue, POItoEdit.getFav())){

                    POItoEdit.setPOIFav(favValue);
                }

                currentUser.getPOIs().add(POItoEdit);

                Boolean result = userInfo.getUserFile().UpdateUser(currentUser, currentUser);


                if(result){
                    
                    saveAlert.setAlertType(AlertType.INFORMATION);

                    saveAlert.setTitle("Confirmation Edit POI");

                    saveAlert.setContentText("POI successfully edited");

                    Optional<ButtonType> resultAlert = saveAlert.showAndWait();

                    if(resultAlert.get() == ButtonType.OK){

                        if(!currentUser.isDeveloper()){

                        try {

                                previousStage.close();

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));

                                Parent root = loader.load();

                                ((MainPageController)loader.getController()).setUserData(userInfo);

                                Stage stage = (Stage) savePOIBtn.getScene().getWindow();

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
                }
            }
            else{

                saveAlert.setAlertType(AlertType.WARNING);

                saveAlert.setContentText("No new information to add");

             
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
        
        this.POItoEdit = rPOI;
        
        POINameLabel.setText(POItoEdit.getPOIName());
    
    }
}
