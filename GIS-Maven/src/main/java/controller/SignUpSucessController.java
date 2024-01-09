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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class for the sign up success page
 * It contains the functions to handle all the events in the sign up sucess page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class SignUpSucessController implements Initializable {
  
    @FXML
    private Button confirmBtn;
    
    /** Non FXML variable */
    private Stage previousStage;

    
    /**
     * Provides actions when a user click the "confirm" button in the sign up 
     * success page
     * @param event the ActionEvent created when the user clicked on the button
     */
    @FXML
    void confirm(ActionEvent event) {
        
        try {
            previousStage.close();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
            
            Parent root = loader.load();
           
            Stage stage = (Stage) confirmBtn.getScene().getWindow();
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            
            Image appIcon = new Image("/images/Stacked.png");
            
            stage.getIcons().add(appIcon);
            
            stage.setTitle("GIS Login Page");
            
            stage.setResizable(false);
            
            stage.show();
        
        } catch (IOException ex) {
            
            Logger.getLogger(SignUpSucessController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
    * Initializes the controller page
    * @param url
    * @param rb 
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
    } 
    
    /**
     * Sets the variable previousStage to get the stage of the view calling the 
     * success page
     * @param stage the Stage object to set as previousStage
     */
    public void setStage(Stage stage){
    
        this.previousStage = stage;
    
    }
    
}
