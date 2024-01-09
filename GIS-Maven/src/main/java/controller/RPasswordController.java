/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.*;

/**
 *
 * @author valer
 */
public class RPasswordController implements Initializable{
    
    @FXML
    private PasswordField SecAnswerTextField;

    @FXML
    private Label SecQuestionText;

    @FXML
    private ImageView appLogo;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private Label emailLbl;

    @FXML
    private TextField emailTxtField;

    @FXML
    private Label erroLbl;

    @FXML
    private VBox loginVbox;

    @FXML
    private Label secAnswerLbl;

    @FXML
    private Label secQuestionLBL;
    
    @FXML
    private Label errorLbl;
    
    private String email;
    
    private FileHandler userFile;
    
    private User currentUser;
    
    private String password;
    
    private String cfPassword;
    
    private String secQuestion;
    
    private String secAnswer;
    
    private UserData userInfo;

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void confirm(ActionEvent event) {
        
        if(SecQuestionText.getText().equals("")){
            
          
            
            email = emailTxtField.getText();
            
            if(!email.equals("")){
            
                userInfo = new UserData(email,"user.json");
                
                //userInfo = new UserData(email,"C:\\Users\\valer\\OneDrive - The University of Western Ontario\\Val\\CS2212\\Project_CS2212\\src\\user.json");

                currentUser = userInfo.getCurrentUser();
                
                if(currentUser != null){
                
                    secQuestionLBL.setVisible(true);
                    
                    secQuestion = currentUser.getSecQuestion();
                    
                    SecQuestionText.setText(secQuestion);
                
                    secAnswerLbl.setVisible(true);
                    
                    SecAnswerTextField.setVisible(true);
                }
                else{
                    errorLbl.setText("Invalid User");
                }
            
            }
            else{
                
                errorLbl.setText("No email entered");
            }
        
        }
        else{
        
        
        
        }

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    
    } 
    
}

