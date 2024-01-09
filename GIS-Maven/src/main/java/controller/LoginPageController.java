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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the login page
 * It contains the functions to handle all the events in the login page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class LoginPageController implements Initializable {


    /** FXML variables */
    
    /** The login button*/
    @FXML
    private Button LoginBtn;

    /** The textField to enter the email*/
    @FXML
    private TextField emailTxtField;

    /** The link to click when an email is forgotten*/
    @FXML
    private Hyperlink forgetPassLink;

    /** The textField to enter the password*/
    @FXML
    private PasswordField passwordTxtField;

    /** The sign up button*/
    @FXML
    private Button signUpBtn;
    
    /** The label to display error*/
    @FXML
    private Label erroLbl;
    
    
    /** Non FXML instance variables */
    
    /** Current user email */
    private String email;
    
    /** User object for current user*/
    private User currentUser;
    
    /** Current user password*/
    private String password;
    
    /** UserData object for current user*/
    private static UserData userInfo;
    
    /** File path for user and metadata files */
    private String filePath;
    
    
    /**
     * Initializes the controller class.
     * Creates the path for the program to find the JSON files
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
     * Provides actions when a user click the "Reset Password link" in the main page
     * @param event the ActionEvent created when the user clicked on the link
     */
    @FXML
    void forgetPassword(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ResetPassword.fxml"));
            
            Parent root = loader.load();
            
            Stage stage = (Stage) forgetPassLink.getScene().getWindow();
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            
              Image appIcon = new Image(getClass().getResourceAsStream("/images/Stacked.png"));
            
            stage.getIcons().add(appIcon);
            
            stage.setResizable(false);
            
            stage.setTitle("GIS Reset Password");
            
            stage.show();
            
        } catch (IOException ex) {
            
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Provides actions when a user click the "Login" button in the main
     * page
     * @param event the ActionEvent created when the user clicked on the button
     */
   @FXML
    void login(ActionEvent event) throws IOException {
        
        email = emailTxtField.getText();
        
        password = passwordTxtField.getText();
       
        String path = filePath+"/user3.json";
        
        
        
        userInfo = new UserData(email,path);
       
        currentUser = userInfo.getCurrentUser();
        
        if(currentUser != null){
             
            if(!password.equals("")){
        
                if(currentUser.getPassword().equals(password)){
                    
                    if(!currentUser.isDeveloper()){

                        try {   
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));

                            Parent root = loader.load();

                            
                            ((MainPageController)loader.getController()).setUserData(userInfo);

                            Stage stage = (Stage) LoginBtn.getScene().getWindow();

                            Scene scene = new Scene(root);

                            stage.setScene(scene);

                            Image appIcon = new Image(getClass().getResourceAsStream("/images/Stacked.png"));

                            stage.getIcons().add(appIcon);

                            stage.setResizable(true);

                            stage.setMaximized(true);

                            stage.setTitle("GIS Main Page");

                            stage.show();
                        } 
                        catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                    else{
                    
                          try {   // Load the main page view
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DevPage.fxml"));

                            Parent root = loader.load();

                            // TRansfer the user Data to the new view
                            ((DevPageController)loader.getController()).setUserData(userInfo);

                            Stage stage = (Stage) LoginBtn.getScene().getWindow();

                            Scene scene = new Scene(root);

                            stage.setScene(scene);

                              Image appIcon = new Image(getClass().getResourceAsStream("/images/Stacked.png"));

                            stage.getIcons().add(appIcon);

                            stage.setResizable(true);

                            stage.setMaximized(true);

                            stage.setTitle("GIS Developer Page");

                            stage.show();
                        } 
                        catch (IOException e) {

                            e.printStackTrace();
                        }
                    
                    }
                }
                else{

                 

                    erroLbl.setText("Invalid Password");
                }
            }
            else{
                erroLbl.setText("No password entered");
            }
        }
        else{
        
            erroLbl.setText("Invalid user");

        }
        passwordTxtField.clear();
    }
    
    /**
     * Provides actions when a user click the "Sign Up" button in the main
     * page
     * @param event the ActionEvent created when the user clicked on the button
     */
    @FXML
    void signup(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
            
            Parent root = loader.load();
            
            Stage stage = (Stage) signUpBtn.getScene().getWindow();
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            
              Image appIcon = new Image(getClass().getResourceAsStream("/images/Stacked.png"));
            
            stage.getIcons().add(appIcon);
       
            stage.setResizable(false);
            
            stage.setTitle("GIS Sign Up Page");
            
            stage.show();
        
        } catch (IOException ex) {
            
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static UserData getCurrentUser(){
    
       return userInfo;
    
    }
}