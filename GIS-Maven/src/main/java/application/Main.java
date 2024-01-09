/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is the application main page with the main method to launch it
 * It opens the application login page trough the Login page view file
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 1.0
 */
public class Main extends Application {
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * To Launch the login page of the application
     * @param stage the first window that will appear
     */
    @Override
    public void start(Stage stage) { 
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            
            Image appIcon = new Image("/images/Stacked.png");
            
            stage.getIcons().add(appIcon);

            stage.setTitle("GIS Login Page");
          
            
            stage.setResizable(false);
            
            stage.show();
        
        } catch (IOException e) {
           
            e.printStackTrace();
        
        }
            
       
    }
    
}
