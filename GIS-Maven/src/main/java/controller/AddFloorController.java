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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the Add floor page
 * It contains the functions to handle all the events in the view page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 3.0
 */
public class AddFloorController implements Initializable{
    
    @FXML
    private Button cancelBtn;

   
    @FXML
    private TextField floorMap;

    @FXML
    private TextField floorNumber;

    @FXML
    private Label namErrorLbl;

    @FXML
    private Button saveBtn;

   
    @FXML
    private Label maperrorLbl;

  
    
    /** Non FXML variables */
    
    private MapData2 map2;
    
    private Stage previousStage;
    
    private Building floorBuilding;
    
    private UserData userInfo;
    
    private String filePath;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /** No initialization required*/
    } 

     /**
     * Cancel the action of adding a floor
     * @param event the event detected when the user click on add floor
     */
    @FXML
    void cancel(ActionEvent event) {
        
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
      
        stage.close();
    }

    /**
     * Reads the information provided for the new floor
     * check if values are provided
     * save the new floor information
     * reloads the map once the floor is added
     * @param event the event detected when the user click on add floor
     */
    @FXML
    void save(ActionEvent event) {
        
        boolean result = false;
        
        boolean exit = false;
        
        try{
            if(floorBuilding != null){

                if(!floorNumber.getText().equals("")){

                    namErrorLbl.setText("");

                    int floorNum = Integer.parseInt(floorNumber.getText());
                    
                    for(int k =0; k < floorBuilding.getFloors().size(); k++){
                    
                        if(floorBuilding.getFloors().get(k).getfloorNUm() == floorNum){
                        
                            exit = true;
                        
                        }
                    
                    }

                    maperrorLbl.setText("");

                    if(!exit){
                    if(!floorMap.getText().equals("")){

                        String mapFile = floorMap.getText();
                        
                        String mapPath = "/images/"+mapFile;
                        
                        Image defaultImage = new Image(getClass().getResourceAsStream(mapPath));
                        

                        Floor newFloor = new Floor(floorBuilding.getName(), floorNum, mapFile);

                        result = map2.getFile().addFloor(newFloor, floorBuilding.getName());
                        
                        if(result){
                        
                        
                            try {

                                previousStage.close();

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DevPage.fxml"));

                                Parent root = loader.load();

                                ((DevPageController)loader.getController()).setUserData(userInfo);

                                Stage stage = (Stage) saveBtn.getScene().getWindow();

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

                        maperrorLbl.setText("No map entered");

                    }
                    }
                    else{
                    
                        Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                        layerAlert.setTitle("Add Floor");

                        layerAlert.setContentText("The floor already exist");

                        layerAlert.show();
                    
                    
                    }

                }
                else{

                    namErrorLbl.setText("No floor number");

                }

            }

        }
        catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }
        catch(NullPointerException e){
        
            Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
            layerAlert.setTitle("Add Florr");

            layerAlert.setContentText("The map file does not exist");

            layerAlert.show();
        
        }

    }

     /**
     * Sets the variable previousStage to get the stage of the view calling the Add POI page 
     * @param stage the Stage object to set as previousStage
     */
    public void setStage(Stage stage){
    
        this.previousStage = stage;
    }
    
    /**
     * Sets the variable userInfo to get the current user data
     * @param  userInf the UserData object to set as userInfo
     */
    public void setUserData(UserData userInf){
        
        this.userInfo = userInf;
    
    } 
  
    /**
     * Sets the variable map2 to get the loaded map metadata info
     * @param mapInfo the MapData2 object to set as map2
     */
    public void setMapData2(MapData2 mapInfo){
    
       this.map2 = mapInfo;
       
       
       
    
    }
    
    /**
     * Sets the variable buildingToEdit to get the provided building
     * @param buildingToEdit the building to edit
     */
    public void setBuilding(Building buildingToEdit){
    
        this.floorBuilding = buildingToEdit;
        
    }
    
    /**
 * Creates the path to get the user and metadata JSON files
 * 
 */
   private void createPath(){
    
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

    
}
