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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;


/**
 * FXML Controller class for the sign up page
 * It contains the functions to handle all the events in the sign up page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class SignUpController implements Initializable {
    
    @FXML
    private Button CancelBtn;

    @FXML
    private Button RegisterBtn;

    @FXML
    private PasswordField cfPasswordTxtField;


    @FXML
    private TextField emailTxtField;

    @FXML
    private Label erroLbl;


    @FXML
    private PasswordField passwordTxtField;


    @FXML
    private PasswordField secAnswerTxtField;

    @FXML
    private ComboBox<String> secQuestionCombo;

    
    @FXML
    private Label checkEmail;

    
    /** Non FXML variable */
    private String email;
    
    private User currentUser;
    
    private String password;
    
    private String cfPassword;
    
    private String secQuestion;
    
    private String secAnswer;
    
    private UserData userInfo;
    
    private String filePath;
    
    
   /**
    * Initializes the controller page
    * @param url
    * @param rb 
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
 
        ObservableList<String> questionList = FXCollections.observableArrayList();

        questionList.add("What is your favorite country?");

        questionList.add("Where were you born?");

        questionList.add("What is your favorite hobby?");

        secQuestionCombo.setItems(questionList);
        
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
     * Provides actions when a user click the "cancel" button in the sign up
     * page
     * @param event the ActionEvent created when the user clicked on the button
     */
    @FXML
    void cancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
            
            Parent root = loader.load();
            
            Stage stage = (Stage) CancelBtn.getScene().getWindow();
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            
            Image appIcon = new Image("/images/Stacked.png");
            
            stage.getIcons().add(appIcon);
            
            stage.setResizable(false);
            
            stage.setTitle("GIS Login Page");
            
            stage.show();
        
        } catch (IOException ex) {
            
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Provides actions when a user click the "Register" button in the sign up
     * page
     * @param event the ActionEvent created when the user clicked on the button
     */
    @FXML
    void register(ActionEvent event) {
        
        erroLbl.setText("");
        
        email = emailTxtField.getText();
     
        try{
        
        if(!email.equals("")){
            
            password = passwordTxtField.getText();
            
            cfPassword = cfPasswordTxtField.getText();
            
            if(!password.equals("") && (password.equals(cfPassword))){
                
                
                secQuestion = secQuestionCombo.getValue();

                if(secQuestion != null){
                    
                    secAnswer = secAnswerTxtField.getText();
                    
                    if(!secAnswer.equals("")){
                        
                        String path = filePath+"/user3.json";
                           
                        userInfo = new UserData(email,path);
                        
                        currentUser = userInfo.getCurrentUser();

                        if(currentUser == null){
                           
                            User createdUser = new User(email,password,secQuestion,secAnswer,0);
                            
                            Boolean add = userInfo.getUserFile().AddUserTofile(createdUser);
                            
                            if(add){
                            
                                try {
         
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpSucess.fxml"));

                                    Parent root = loader.load();
                                    
                               
                                    ((SignUpSucessController)loader.getController()).setStage((Stage) RegisterBtn.getScene().getWindow());;
                                    
                              
                                    Stage stage = new Stage();
                                    
                                    Scene scene = new Scene(root);

                                    stage.setScene(scene);

                                    Image appIcon = new Image("/images/Stacked.png");
                                    
                                    stage.getIcons().add(appIcon);

                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    
                                    stage.setResizable(false);
                                    
                                    stage.setTitle("GIS Sign Up Sucess");
                                    
                                    stage.show();
                                } catch (Exception e) {
                                    
                                    e.printStackTrace();
                                }
                            
                            }
                            
                        }
                        else{
                            
                            erroLbl.setText("Email already in use");
                        }
                    }
                    else{
                    
                        erroLbl.setText("No security Answer");
                    }
                }
                else{
                    erroLbl.setText("No security Question");
                
                }
            }
            else if(password.equals("")){
                
                erroLbl.setText("No password entered");
            }
            else{
            
                
                erroLbl.setText("Passwords do not match");
            }
        }
        else{
            
            erroLbl.setText("Invalid email");
        }
        }
        catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }
    }

  
}
