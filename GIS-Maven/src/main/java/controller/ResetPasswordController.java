/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the Reset Password page
 * It contains the functions to handle all the events in the Reset password page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class ResetPasswordController implements Initializable{
    
    @FXML
    private PasswordField SecAnswerTextField;

    @FXML
    private Label SecQuestionText;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private TextField emailTxtField;

    @FXML
    private Label secAnswerLbl;

    @FXML
    private Label secQuestionLBL;
    
    @FXML
    private Label errorLbl;
    
    /** Non FXML variable*/
    private String email;
   
    private User currentUser;
    
    private String secQuestion;
   
    private UserData userInfo;
    
    private String filePath;
    
    /**
    * Initializes the controller page
    * @param url
    * @param rb 
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        URL url2 = LoginPageController.class.getProtectionDomain().getCodeSource().getLocation();
                
        try {
           
            
            String str = Paths.get(url2.toURI()).toString();

            str = str.replace("\\", "/");

            String path2[] = str.split("/");

            String path3 = "";

            for(int i = 0; i < path2.length-1; i++){

                if(i == 0){

                    path3 = path3+path2[i];
                }
                else{

                    path3 = path3+"/"+path2[i];
                }

            }
           

            filePath = path3;

             } catch (URISyntaxException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    } 

    /**
     * Provides actions when a user click the "cancel" button in the Reset 
     * password page
     * @param event the ActionEvent created when the user clicked on the button
     */
    @FXML
    void cancel(ActionEvent event) {
        
        try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));

                    Parent root = loader.load();

                    Stage stage = (Stage) cancelBtn.getScene().getWindow();

                    Scene scene = new Scene(root);

                    stage.setScene(scene);

                    Image appIcon = new Image("/images/Stacked.png");

                    stage.getIcons().add(appIcon);
                    

                    stage.setTitle("GIS Login Page");

                    stage.show();
                
                } catch (IOException ex) {
                    
                    Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                }

    }

    /**
     * Provides actions when a user click the "confirm" button in the Reset 
     * password page
     * @param event the ActionEvent created when the user clicked on the button
     */
    @FXML
    void confirm(ActionEvent event) {
        
        if(SecQuestionText.getText().equals("")){
            email = emailTxtField.getText();
            
            if(!email.equals("")){
            
                String path = filePath+"/user3.json";
                            
                userInfo = new UserData(email,path);
                
                currentUser = userInfo.getCurrentUser();
                
                if(currentUser != null){
                    
                    errorLbl.setText("");
                
                    secQuestionLBL.setVisible(true);
                    
                    secQuestion = currentUser.getSecQuestion();
                    
                    SecQuestionText.setText(secQuestion);
                
                    secAnswerLbl.setVisible(true);
                    
                    SecAnswerTextField.setVisible(true);
                   
                }
                else{
                    errorLbl.setText("Invalid Email");
                }
            
            }
            else{
                
                errorLbl.setText("No Email entered");
            }
        
        }
        else{
        
            String correctAnswer = currentUser.getSecAnswer();
                    
            String inputAnswer = SecAnswerTextField.getText();
                    
            if(!inputAnswer.equals(correctAnswer)){
                    
             errorLbl.setText("Wrong security answer");
                    
            }
            else{
            
                try {
                    
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewPassword.fxml"));

                    Parent root = loader.load();

                 
                    ((NewPasswordController)loader.getController()).setUserData(userInfo);
                    
                    ((NewPasswordController)loader.getController()).setStage((Stage) confirmBtn.getScene().getWindow());

                    Stage stage = new Stage();

                    Scene scene = new Scene(root);

                    stage.setScene(scene);

                    Image appIcon = new Image("/images/Stacked.png");

                    stage.getIcons().add(appIcon);
                    
                    stage.setResizable(false);

                    stage.setTitle("GIS Enter New Password");

                    stage.show();
                
                } catch (IOException ex) {
                    
                    Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
            }
        
        }

    }

    
    
}
