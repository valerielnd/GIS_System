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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the New Password page
 * It contains the functions to handle all the events in the New Password page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class NewPasswordController implements Initializable{

    @FXML
    private Button cancelBtn;

    @FXML
    private PasswordField cfPasswordTextField;

    @FXML
    private Button confirmBtn;

    @FXML
    private Hyperlink errorLbl;

    @FXML
    private PasswordField passwordTxtField;
    
   @FXML
    private Label checkPassword;
    
    private User currentUser;
    
    private String password;
    
    private String cfPassword;
    
    private UserData userInfo;
    
    private Stage previousStage;
    
  
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
     * Provides actions when a user click the "Cancel" button in the New 
     * Password page
     * @param event the ActionEvent created when the user clicked on the button
     */
    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Provides actions when a user click the "Confirm" button in the New 
     * Password page
     * @param event the ActionEvent created when the user clicked on the button
     */
    @FXML
    void confirm(ActionEvent event) {
       
        try{
        currentUser = userInfo.getCurrentUser();
       
        password = passwordTxtField.getText();
        
        if(!password.equals("")){
        
            cfPassword = cfPasswordTextField.getText();
            
            if(cfPassword.equals(password)){
            
                checkPassword.setText("v");
                
                User updateUser = new User(currentUser.getEmail(),password,currentUser.getSecQuestion(),currentUser.getSecAnswer(), 0);
                
                for(int i = 0; i < currentUser.getPOIs().size();i++){
                
                    updateUser.addToList(currentUser.getPOIs().get(i));
                }
                
                Boolean result = userInfo.getUserFile().UpdateUser(currentUser, updateUser);
                
                if(result){
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
                        
                        Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                
                    System.out.print("Not done");
                }
            }
            else{
            
                errorLbl.setText("Passwords do not match");
            
            }
        
        }
        else{
            errorLbl.setText("No password entered");
        
        }
        }
        catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }

    }
    
    
    /**
     * Sets the variable userInfo to the provided UserData
     * Permits to transfer the current user data to this page
     * @param data the UserData object to set as userInfo
     */
    public void setUserData(UserData data){
    
        this.userInfo = data;
    
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

