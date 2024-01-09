package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author Tyler Johnson
 */
public class HelpController implements Initializable {
    
    /**
     * The help box located on the side of the screen
     */
    @FXML
    private DialogPane HelpTextBox;
    
    @FXML
    private TitledPane NavBuildings;
    
    @FXML
    private TitledPane Buildings;
    
    @FXML
    private TitledPane ChangeLayers;
    
    
    
    /**
     * Initializes the controller class.
     * @param url The FXML location
     * @param rb Resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /* Set up the on mouse clicked and unclicked for each box */
        
        
        
        
    }
    
    public void BuildingNavigation() {
    
        this.HelpTextBox.setContentText("On the sidebar, there is a group of buildings listed");
        
    
    }
    
    /**
     * Edits the help box when asked about changing layers
     */
    public void ChangingLayers() {
        
        this.HelpTextBox.setContentText("On the lower left hand side, there is a list of layers which you can select.");
        
    }
    
    /**
     * This will empty out the help box
     */
    public void EmptyHelpBox() {
        
        this.HelpTextBox.setContentText("");
        
    }
    
}
