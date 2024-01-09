/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;


/**
 * FXML Controller class for the Main page
 * It contains the functions to handle all the events in the Main page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 5.0
 */
public class MainPageController implements Initializable {
    
   @FXML
    private ComboBox<String> POICombo;


    @FXML
    private Button addPOIBtn;


    @FXML
    private ComboBox<String> bldgCombo;

    @FXML
    private ListView<String> customPOIList;

    @FXML
    private ListView<String> favPOIList;

    @FXML
    private ComboBox<String> floorCombo;

    @FXML
    private ListView<String> layerList;

    @FXML
    private ImageView mapImage;

    @FXML
    private ScrollPane mapScrollPane;
    
    @FXML
    private Pane mapPane;

    @FXML
    private MenuButton pinButton;

    @FXML
    private Slider mainPageSlider;

    
    @FXML
    private MenuButton favBtn;
    
    @FXML
    private MenuButton customBtn;
    
     @FXML
    private CheckBox makeFav;
    



    
    
    /** Non FXML variables */
    private String filePath;
    
    private Pane paneDesc;

    private Group zoomGroup;
    
    private String currentMap;
    
    private Building bldg;
    
    private Floor floor;
    
    private CampusMap campMap;
    
    private UserData userInfo;
    
    private MapData2 map2;
    
    private String mapFile;
    
    private ImageView mapNewImage;
    
    private ImageView pin;
    
    private Vec2 POIpos;
    
    private Boolean add;
    
    private User currentUser;
    
    private String selectedCustomPOI;
    
    private String selectedFavPOI;
    
    private ObservableList<String> appLayer;
    
    private String  SelectedBuilt;
    
    

    /**
     * Initializes the controller class for the Main page
     * Sets the default image to the campus map
     * Sets up the slider and the zoom properties of the page
     * Populate the building combo box for the user to browse the maps
     * Populate the layer combo to see the POI on the campus Map
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /** Create the path for the metadata file */
        
        createPath();
        
        
        /** Display the campus map as the default map */
        
        currentMap = "Campus Map"; 
        
        int pinWidth = 116;
        
        int pinHeight = 120;
        
        String metadataPath = filePath+"/map6.json";
        
        map2 = new MapData2(metadataPath, currentMap);
        
        campMap = map2.getMap();
        
        mapFile = campMap.getMapFile();
        
        String mapPath = "/images/"+mapFile;
        
        mapPane.getChildren().removeAll(mapImage,pinButton);
        
        Image defaultImage = new Image(getClass().getResourceAsStream(mapPath));
        
        Image pinImage = new Image(getClass().getResourceAsStream("/images/Generic_Location.png"));
      
        mapNewImage = new ImageView();
        
        mapNewImage.setImage(defaultImage);
        
        
        /** Add an event listener to the displayed map */
        
        mapNewImage.setOnMouseClicked((MouseEvent event) -> {
           
           getPixel(event);
        
       });
        
        /** Create the pin for showing POI */
        pin = new ImageView();
        
        pin.setFitWidth(pinWidth);
        
        pin.setFitHeight(pinHeight);
        
        pin.setImage(pinImage);
        
        pin.setVisible(false);
        
        mapNewImage.setPreserveRatio(true);
        
        pin.setPreserveRatio(true);
        
        mapPane.getChildren().addAll(mapNewImage,pin);
        
        
        /** Prepare the slider for zooming */
       
        mainPageSlider.setMax(1.5);
        
        mainPageSlider.setMin(0.5);
        
        mainPageSlider.setValue(1.0);
        
        mainPageSlider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        
        /** Wrap scroll content in a Group so ScrollPane re-computes scroll bars */
        Group contentGroup = new Group();
        
        zoomGroup = new Group();
        
        contentGroup.getChildren().add(zoomGroup);
        
        zoomGroup.getChildren().add(mapScrollPane.getContent());
       
        mapScrollPane.setContent(contentGroup);
        
       
        /** Get the application metadata trough an instance of MapData */
        
        /** Populate the building combo box in the main page */
       
        ObservableList<String> bldgList = FXCollections.observableArrayList();
        
        for(int i = 0; i < campMap.getBuilding().size(); i++){
            
            bldgList.add(campMap.getBuilding().get(i).getName());
            
        }
        bldgCombo.setItems(bldgList);
        
         /** Add the campus POIs to the POI combo box*/
        
        ArrayList<POI> campusPOIs = campMap.getPOIs();
        
        if(!campusPOIs.isEmpty()){
            
            ObservableList<String> campPOIList = FXCollections.observableArrayList();
        
            for (POI campusPOI : campusPOIs) {
                
                campPOIList.add(campusPOI.getPOIName());
            }
                
            POICombo.setItems(campPOIList);
            
             /** Add a listener to the POI combo box to detect event*/
            POICombo.getSelectionModel().selectedItemProperty().addListener((options,oldValue,newValue)-> {
           
                if(newValue != null){

                   showPOI(newValue,campusPOIs);
                   
                   SelectedBuilt = newValue;
                   
                   makeFav.setVisible(true);

                }
            
            });

             
            
        }
        
        
        
        /** Populate the list views of the current user favorite and custom POIS*/
        
        ObservableList<String> favPOIs = FXCollections.observableArrayList();
        
        ObservableList<String> customPOIs = FXCollections.observableArrayList();
        
        userInfo = LoginPageController.getCurrentUser();
        
        currentUser = userInfo.getCurrentUser();
        
        ArrayList<POI> POIList = currentUser.getPOIs();
        
        ArrayList<POI> currentUserPOIs = currentUser.getPOIs();
        
        if(!POIList.isEmpty()){
        
            for(int i = 0; i < POIList.size(); i++){
                
                POI newPOI = POIList.get(i);
                
                customPOIs.add(newPOI.getPOIName());
                
                if(newPOI.getFav() == true){
                
                    favPOIs.add(newPOI.getPOIName());
                }
            
            }
            favPOIList.setItems(favPOIs);
            
            customPOIList.setItems(customPOIs);
            
            
            /** Add listener to detect actions on the list of favorite and custom POIs */
            
            /** Two kinds of action, right click and left click */
            
            
            /** Adding listener for Custom POIs */
            
            customPOIList.setOnMouseClicked((MouseEvent event) -> {
           
               if(event.getButton()== MouseButton.PRIMARY){
                   
                    customBtn.setVisible(false);
               
                    selectedCustomPOI = customPOIList.getSelectionModel().getSelectedItem();
                    
                    showPOI(selectedCustomPOI,POIList);
              
               }
               else if (event.getButton() == MouseButton.SECONDARY){
                  
                   selectedCustomPOI = customPOIList.getSelectionModel().getSelectedItem();
                   
                   customBtn.setVisible(true);
               
               }
        
            });
            
            /** Adding Listener for the favorite POIs */
             favPOIList.setOnMouseClicked((MouseEvent event) -> {
           
               if(event.getButton()== MouseButton.PRIMARY){
               
                   
                    favBtn.setVisible(false);
                    
                    String selectedFav = favPOIList.getSelectionModel().getSelectedItem();
                    
                   
                    
                    showPOI(selectedFav,POIList);
              
               }
               else if (event.getButton() == MouseButton.SECONDARY){
                                      selectedFavPOI = favPOIList.getSelectionModel().getSelectedItem();
                   
                   favBtn.setVisible(true);
               
               }
        
            });
            
            
        }
        
        
        /** Populate the layer view list*/
        
        appLayer = FXCollections.observableArrayList();
        
        
        /** Creating the campus POIs layer */
       
        String campPOILayer = "Campus POI Layer";
        
        appLayer.add(campPOILayer);
        
        ArrayList<POI> campusPOIS = campMap.getPOIs();
        
        
        /** Creating user custom POI layer*/
        
        String customPOILayer = "Custom POI Layer";
        
        appLayer.add(customPOILayer);
        
        
        /** Creating user classroom POI layer*/
        
        String classroomPOILayer = "Classroom POI Layer";
        
        appLayer.add(classroomPOILayer);
        
        
        
        /** Creating user Lab POI layer*/
        
        String labPOILayer = "Lab POI Layer";
        
        appLayer.add(labPOILayer);
        
        
        
        /** Creating user washroom POI layer*/
        
        String washroomPOILayer = "Washroom POI Layer";
        
        appLayer.add(washroomPOILayer);
        
        
        
        /** Creating user elevator POI layer*/
        
        String elevatorPOILayer = "Elevator POI Layer";
        
        appLayer.add(elevatorPOILayer );
        
        
        /** Creating user Exit POI layer*/
        
        String exitPOILayer = "Exit POI Layer";
        
        appLayer.add(exitPOILayer);
        
       
        /** Creating user stairs POI layer*/
        
        String stairsPOILayer = "Stairs POI Layer";
        
        appLayer.add(stairsPOILayer );
        
        
        /** Creating restaurant POI layer*/
        
        String restaurantPOILayer = "Restaurant POI Layer";
        
        appLayer.add(restaurantPOILayer);
        
        layerList.setItems(appLayer);
        
        
        /** Adding a listener for layer list view*/
        
         layerList.setOnMouseClicked((MouseEvent event) -> {
           
               if(event.getButton()== MouseButton.PRIMARY){
               
                   
                    
                    /** Showing Campus POI Layer */
                    String selectedLayer = layerList.getSelectionModel().getSelectedItem();
                    
                    if(selectedLayer.equals(campPOILayer)){
                    
                        showLayer(campusPOIS, "campus");
                    }
                    
                    /** Showing Custom POI Layer */
                    else if(selectedLayer.equals(customPOILayer)){
                        
                        ArrayList<POI> userPOIShow = new ArrayList<>();
                        
                        for (POI currentUserPOI : currentUserPOIs) {
                           
                       
                            if(currentUserPOI.getBldg().equals(currentMap) && (currentMap.equals("Campus Map"))){
                                
                            
                                userPOIShow.add(currentUserPOI);
                            }
                            else if(bldg != null && floor == null && currentUserPOI.getBldg().equals(currentMap)){
                            
                                userPOIShow.add(currentUserPOI);
                            
                            }
                            else if(bldg != null && floor != null && currentUserPOI.getBldg().equals(bldg.getName())){
                                
                                
                                if((currentUserPOI.getFloor()== floor.getfloorNUm())){
                                
                                    userPOIShow.add(currentUserPOI);
                                }
                              
                            }
                        }
                        if(!userPOIShow.isEmpty()){
                        
                            showLayer(userPOIShow, "custom");
                        
                        }
                        else{
                        
                            Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                            layerAlert.setTitle("Show Custom POI Layer");

                            layerAlert.setContentText("No Custom POI on this Map");
                        
                            layerAlert.show();
                        }
                        
                    
                    }
                    
                    /** Showing Classroom POI Layer */
                    else if(selectedLayer.equals(classroomPOILayer)){
                        
                        if(floor != null){
                            
                            
                            ArrayList<POI> classroomPOI = new ArrayList<>();
                        
                            ArrayList<POI> currentFloorPOIs = floor.getPOIs();
                            
                            for (POI currentFloorPOI : currentFloorPOIs) {
                                
                                if(currentFloorPOI.getType()==2){
                                    
                                
                                    classroomPOI.add(currentFloorPOI);
                                }
                                
                            }
                            if(!classroomPOI.isEmpty()){
                            
                                showLayer(classroomPOI, "classroom");
                            }
                            else{
                            
                                Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                                layerAlert.setTitle("Show classroom POI Layer");

                                layerAlert.setContentText("No classroom POI on this Map");
                                
                                layerAlert.show();
                            
                            }
                        
                        }
                        else{

                           Alert layerAlert = new Alert(Alert.AlertType.WARNING);

                           layerAlert.setTitle("Show classroom POI Layer");

                           layerAlert.setContentText("No classroom POI on this Map");

                           layerAlert.show();   

                        }
                    
                        
                    }
                    
                    /** Showing Lab POI Layer */
                    else if(selectedLayer.equals(labPOILayer)){
                        
                        if(floor != null){
                         
                            ArrayList<POI> labPOI = new ArrayList<>();
                        
                            ArrayList<POI> currentFloorPOIs = floor.getPOIs();
                            
                            for (POI currentFloorPOI : currentFloorPOIs) {
                                
                                if(currentFloorPOI.getType()==4){
                           
                                    labPOI.add(currentFloorPOI);
                                }
                                
                            }
                            if(!labPOI.isEmpty()){
                            
                                showLayer(labPOI, "lab");
                            }
                            else{
                            
                                Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                                layerAlert.setTitle("Show lab POI Layer");

                                layerAlert.setContentText("No lab POI on this Map");
                                
                                layerAlert.show();
                            
                            }
                        
                        }
                    else{

                       Alert layerAlert = new Alert(Alert.AlertType.WARNING);

                       layerAlert.setTitle("Show Lab POI Layer");

                       layerAlert.setContentText("No Lab POI on this Map");

                       layerAlert.show();   

                    }
                    
                        
                    }
                    
                    /** Showing washroom POI Layer */
                    else if(selectedLayer.equals(washroomPOILayer)){
                        
                        if(floor != null){
                          
                            ArrayList<POI> washroomPOI = new ArrayList<>();
                        
                            ArrayList<POI> currentFloorPOIs = floor.getPOIs();
                            
                            for (POI currentFloorPOI : currentFloorPOIs) {
                                
                                if(currentFloorPOI.getType()==3){
                                   
                                    washroomPOI.add(currentFloorPOI);
                                }
                                
                            }
                            if(!washroomPOI.isEmpty()){
                            
                                showLayer(washroomPOI, "washroom");
                            }
                            else{
                            
                                Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                                layerAlert.setTitle("Show Washroom POI Layer");

                                layerAlert.setContentText("No Washroom POI on this Map");
                                
                                layerAlert.show();
                            
                            }
                        
                        }
                    else{

                       Alert layerAlert = new Alert(Alert.AlertType.WARNING);

                       layerAlert.setTitle("Show Washroom POI Layer");

                       layerAlert.setContentText("No Washroom  POI on this Map");

                       layerAlert.show();   

                    }
                    
                        
                    }
                    /** Showing elevator POI Layer */
                    else if(selectedLayer.equals(elevatorPOILayer)){
                        
                        if(floor != null){
                          
                            ArrayList<POI> elevPOI = new ArrayList<>();
                        
                            ArrayList<POI> currentFloorPOIs = floor.getPOIs();
                            
                            for (POI currentFloorPOI : currentFloorPOIs) {
                                
                                if(currentFloorPOI.getType() == 11 ){
                                   
                                    elevPOI.add(currentFloorPOI);
                                }
                                
                            }
                            if(!elevPOI.isEmpty()){
                            
                                showLayer(elevPOI, "elevator");
                            }
                            else{
                            
                                Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                                layerAlert.setTitle("Show Elevator POI Layer");

                                layerAlert.setContentText("No Elevator POI on this Map");
                                
                                layerAlert.show();
                            
                            }
                        
                        }
                    else{

                       Alert layerAlert = new Alert(Alert.AlertType.WARNING);

                       layerAlert.setTitle("Show Elevator POI Layer");

                       layerAlert.setContentText("No Elevator POI on this Map");

                       layerAlert.show();   

                    }
                    
                        
                    }
                    /** Showing Stairs POI Layer */
                    else if(selectedLayer.equals(stairsPOILayer)){
                        
                        if(floor != null){
                          
                            ArrayList<POI> stairsPOI = new ArrayList<>();
                        
                            ArrayList<POI> currentFloorPOIs = floor.getPOIs();
                            
                            for (POI currentFloorPOI : currentFloorPOIs) {
                                
                                if(currentFloorPOI.getType() == 13 ){
                                   
                                    stairsPOI.add(currentFloorPOI);
                                }
                                
                            }
                            if(!stairsPOI.isEmpty()){
                            
                                showLayer(stairsPOI, "stairs");
                            }
                            else{
                            
                                Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                                layerAlert.setTitle("Show Stairs POI Layer");

                                layerAlert.setContentText("No Stairs POI on this Map");
                                
                                layerAlert.show();
                            
                            }
                        
                        }
                    else{

                       Alert layerAlert = new Alert(Alert.AlertType.WARNING);

                       layerAlert.setTitle("Show Stairs POI Layer");

                       layerAlert.setContentText("No Stairs POI on this Map");

                       layerAlert.show();   

                    }
                    
                        
                    }
                    /** Showing exit POI Layer */
                    else if(selectedLayer.equals(exitPOILayer)){
                        
                        if(floor != null){
                          
                            ArrayList<POI> exitPOI = new ArrayList<>();
                        
                            ArrayList<POI> currentFloorPOIs = floor.getPOIs();
                            
                            for (POI currentFloorPOI : currentFloorPOIs) {
                                
                                if(currentFloorPOI.getType() == 15 ){
                                   
                                    exitPOI.add(currentFloorPOI);
                                }
                                
                            }
                            if(!exitPOI.isEmpty()){
                            
                                showLayer(exitPOI, "exit");
                            }
                            else{
                            
                                Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                                layerAlert.setTitle("Show Exit POI Layer");

                                layerAlert.setContentText("No Exit POI on this Map");
                                
                                layerAlert.show();
                            
                            }
                        
                        }
                    else{

                       Alert layerAlert = new Alert(Alert.AlertType.WARNING);

                       layerAlert.setTitle("Show Exit POI Layer");

                       layerAlert.setContentText("No Exit POI on this Map");

                       layerAlert.show();   

                    }
                    
                        
                    }
                    /** Showing restaurant POI Layer */
                    else if(selectedLayer.equals(restaurantPOILayer)){
                        
                        if(floor != null){
                          
                            ArrayList<POI> restPOI = new ArrayList<>();
                        
                            ArrayList<POI> currentFloorPOIs = floor.getPOIs();
                            
                            for (POI currentFloorPOI : currentFloorPOIs) {
                                
                                if(currentFloorPOI.getType() == 14 ){
                                   
                                    restPOI.add(currentFloorPOI);
                                }
                                
                            }
                            if(!restPOI.isEmpty()){
                            
                                showLayer(restPOI, "restaurant");
                            }
                            else{
                            
                                Alert layerAlert = new Alert(Alert.AlertType.WARNING);
                            
                                layerAlert.setTitle("Show Restaurant POI Layer");

                                layerAlert.setContentText("No Restaurant POI on this Map");
                                
                                layerAlert.show();
                            
                            }
                        
                        }
                    else{

                       Alert layerAlert = new Alert(Alert.AlertType.WARNING);

                       layerAlert.setTitle("Show Restaurant  Layer");

                       layerAlert.setContentText("No Restaurant  POI on this Map");

                       layerAlert.show();   

                    }
                    
                        
                    }
              
               }
               else if (event.getButton() == MouseButton.SECONDARY){
                  
                   selectedCustomPOI = customPOIList.getSelectionModel().getSelectedItem();
                   
                   customBtn.setVisible(true);
               
               }
        
            });
        
        
        /** Set the zoom so that entire map appears when the user first load the app*/
        zoom(0.5);
    } 
    
    /**
     * Adds a user custom or favorite POI
     * Open the view page add POI
     * @param event the event detected when the user click on the add button
     */  
    @FXML
    void addPOI(ActionEvent event) {
      try {
            if(add == null){
                /** Load the add POi page */
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addPOI.fxml"));

                Parent root = loader.load();

                ((AddPOIController)loader.getController()).setMapData2(map2);
                
                ((AddPOIController)loader.getController()).setUserData(userInfo);

                ((AddPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                if(POIpos != null){
                    

                    ((AddPOIController)loader.getController()).setPOIpos(POIpos);
                }

                /** Create the new window for the new view with a new scene graph */
                Stage stage = new Stage();

                Scene scene = new Scene(root);

                stage.setScene(scene);

                Image appIcon = new Image("/images/Stacked.png");

                stage.getIcons().add(appIcon);

                stage.setTitle("GIS Add POI");
                
                stage.setResizable(false);

                stage.initModality(Modality.APPLICATION_MODAL);

                stage.show();
            }
            
            } catch (Exception e) {

                e.printStackTrace();
            }
           
    }
    
     /**
     * Reloads the campus map
     * @param event the mouse event that was detected on the home button
     */
    @FXML
    void goHome(MouseEvent event) {
        
        
        /** Set the dimension of the PIN used to show a position on the map*/
        int pinWidth = 116;

        int pinHeight = 120;

        String mapPath = "/images/"+campMap.getMapFile();

        currentMap = "Campus Map";
        
        map2.setMapName("currentMap");
        
        map2.setFloorNum(0);

        bldg = null;

        floor = null;

        bldgCombo.getSelectionModel().clearSelection();

        floorCombo.getSelectionModel().clearSelection();

        /** Add the campus POIs */

        ArrayList<POI> campusPOIs = campMap.getPOIs();

        mapPane.getChildren().removeAll(mapPane.getChildren());

        Image pinImage = new Image(getClass().getResourceAsStream("/images/Generic_Location.png"));

        Image newImage = new Image(getClass().getResourceAsStream(mapPath));

        mapNewImage.setImage(newImage);
       
        pin = new ImageView();

        pin.setFitWidth(pinWidth);

        pin.setFitHeight(pinHeight);

        pin.setImage(pinImage);

        pin.setVisible(false);

        mapPane.getChildren().addAll(mapNewImage,pin);


         if(!campusPOIs.isEmpty()){

                ObservableList<String> campPOIList = FXCollections.observableArrayList();

                for (POI campusPOI : campusPOIs) {

                campPOIList.add(campusPOI.getPOIName());
                }

                POICombo.setItems(campPOIList);



         }

    }
    
    /**
     * Detects events on the building combo box in the main page
     * Gets the name of the building selected
     * Populates the floor combo box with the selected building floor numbers
     * @param event the ActionEvent created when the user clicked on combo box
     */
    @FXML
    void bldgComboAction(ActionEvent event) {
        
        
        /** Get the building name selected*/
        String answer = bldgCombo.getValue();
        
        
        /** Find the building object associated with the name selected */
        
        /** Once found, get the floor list of the building and populate the floor combo box*/
        for(int i = 0; i < campMap.getBuilding().size(); i++){
           
           if(campMap.getBuilding().get(i).getName().equals(answer)){
           
               bldg = campMap.getBuilding().get(i);
               
               int size = bldg.getFloors().size();
               
               ObservableList<String> floorList = FXCollections.observableArrayList();
               
               for(int j = 0; j < size ;j++){
                   
                   floorList.add(String.valueOf(bldg.getFloors().get(j).getfloorNUm()));
               
               }
               
               floorCombo.setItems(floorList);
               
               /** Add a listener to the floor combo box to detect event*/
               floorCombo.getSelectionModel().selectedItemProperty().addListener((options,oldValue,newValue)-> {
                   
                  
                   if(newValue != null){
                   
                       floorComboAction();
                   }
               });
               
               
           }
       
       }
    }

    
    /**
     * Detects events on the floor combo box in the main page
     * Gets the number of the floor selected
     * Populates the POI combo box with the selected floor POIs
     */
    @FXML
    void floorComboAction() {
        
        /** Get the floor number selected*/
        String floorNum = floorCombo.getSelectionModel().getSelectedItem();
        
        int pinWidth = 116;
        
        int pinHeight = 120;
        
       
        /** Set the value of the map name since a new map will be displayed*/
        map2.setMapName(bldg.getName());
        
        currentMap = bldg.getName();
        
        /** Set the value of the floor number*/
        map2.setFloorNum(Integer.parseInt(floorNum));
        
        int realFloorNum = Integer.parseInt(floorNum);
        
        int size = bldg.getFloors().size();
        
        ObservableList<String> floorPOIList = FXCollections.observableArrayList();

        /** Find the map of the selected floor */
        for(int j = 0; j < size ;j++){
                   
            if(bldg.getFloors().get(j).getfloorNUm() == realFloorNum){

                floor = bldg.getFloors().get(j);
                
                String mapPath = "/images/"+floor.getMapFile();

                mapPane.getChildren().removeAll(mapPane.getChildren());
                
                Image pinImage = new Image(getClass().getResourceAsStream("/images/Generic_Location.png"));
                
                Image newImage = new Image(getClass().getResourceAsStream(mapPath));

               
                mapNewImage.setImage(newImage);
                
                pin = new ImageView();
        
                pin.setFitWidth(pinWidth);
        
                pin.setFitHeight(pinHeight);
        
                pin.setImage(pinImage);
                
                pin.setVisible(false);
                
                mapPane.getChildren().addAll(mapNewImage,pin);
                
                int sizePOI = floor.getPOIs().size();
                
                
		for(int i = 0; i < sizePOI; i++){
                    
                   
                    String realPOIName = floor.getPOIs().get(i).getPOIName().split("_")[0] + floor.getPOIs().get(i).getPOIName().split("_")[3];
			
                    floorPOIList.add(realPOIName);

		}
                
                
            }

        }
        /** Populate the POI combo box with the current floor POIs*/
        POICombo.setItems(floorPOIList);
        
        /** Add a listener to the POI combo box to detect event*/
        POICombo.getSelectionModel().selectedItemProperty().addListener((options,oldValue,newValue)-> {
                   
            if(newValue != null){

                POIComboAction(newValue);
               
            }
        });
    }
    
    /**
     * Gets the value of the POI selected 
     * Calls the function ShowPOI to show it on the current Map
     * @param POIvalue the name of the POI selected
     */
    public void POIComboAction(String POIvalue){
        
        if(currentMap.equals("Campus Map")){
        
            ArrayList<POI> campusPOIs = map2.getMap().getPOIs();
            
            for (POI campusPOI : campusPOIs) {
            
                if(campusPOI.getPOIName().equals(POIvalue)){
                
                    showPOI(campusPOI.getPOIName(), campusPOIs);
                
                }
            
            }
            
            
        
        }
        else{
            ArrayList<POI> currentPOIs = floor.getPOIs();

            for (POI currentPOI : currentPOIs) {

                String currentPOIName = currentPOI.getPOIName().split("_")[0] + currentPOI.getPOIName().split("_")[3];


                if(POIvalue.equals(currentPOIName)){


                   showPOI(currentPOI.getPOIName(), currentPOIs);



                }
            }
    
        }
    
    }
    
    /**
     * Detects when a user clicks on the map image
     * Sets the x and y position where the mouse is positioned
     * @param event 
     */
    @FXML
    void getPixel(MouseEvent event) {
        /** Get the x and y position selected on the map*/
        Color color = mapNewImage.getImage().getPixelReader().getColor((int) event.getX(), (int) event.getY());
        
        
        /** Show the POI description on the map */
        if(paneDesc != null){
            paneDesc.setVisible(false);
        }
        
        /** Add the pin image add the position selected on the map */
        POIpos = new Vec2(event.getX(),event.getY());
        
        mapPane.getChildren().removeAll(mapPane.getChildren());
        
        pin.setVisible(true);
        
        pin.setX(event.getX()-(116/2));
        
        pin.setY(event.getY()-(120));
        
        mapPane.getChildren().addAll(mapNewImage,pin);
        
    }
   
    
    /**
     * Gets the value of the POI selected 
     * Calls the function ShowPOIOnCurrentMapto show it on the current Map
     * @param POIName the name of the POI to show on the map
     * @param list the list where the POI is a member of
     */
    public void showPOI(String POIName, ArrayList<POI> list){
        
        int pinWidth = 116;
        
        int pinHeight = 120;
        
        String mapPath ="";
        
        for(int i = 0; i < list.size(); i++){
                
                POI newPOI = list.get(i);
                
                if(POIName.equals(newPOI.getPOIName())){
                    
                
                    pin.setVisible(true);
                    
                    String bldgName = newPOI.getBldg();
                    
                    int floorNum = newPOI.getFloor();
                    
                    ArrayList<Building> buildings = campMap.getBuilding();
                    
                    if(bldgName.equals("Campus Map")){
                    
                        mapPath = "/images/"+campMap.getMapFile();
                        
                        currentMap = "Campus Map";
                
                        bldg = null;

                        floor = null;

                        if(!bldgCombo.getItems().isEmpty()){
                        
                        
                            bldgCombo.getSelectionModel().clearSelection();
                        }
                        
                        if(!floorCombo.getItems().isEmpty()){
                             floorCombo.getSelectionModel().clearSelection();
                        }

                        
                        //POICombo.getSelectionModel().clearSelection();
                        
                        /** Set the value of the map name since a new map will be displayed*/
                        map2.setMapName("Campus Map");
        
                        /** Set the value of the floor number*/
                        map2.setFloorNum(0);
                   
                    }
                    else {
                    
                        for(int j = 0; j < buildings.size(); j++){

                            if(bldgName.equals(buildings.get(j).getName())){
                            
                                Building newBuilding = buildings.get(j);
                                
                                bldg = newBuilding;
                                
                                bldgCombo.setValue(bldg.getName());
                                
                                /** Set the value of the map name since a new map will be displayed*/
                                map2.setMapName(bldg.getName());

                                ArrayList<Floor> floors = newBuilding.getFloors();

                                for (Floor floor1 : floors) {

                                    if(floor1.getfloorNUm() == floorNum){
                                       
                                        mapPath = "/images/"+floor1.getMapFile(); 
                                        
                                        floor = floor1;
                                        
                                        floorCombo.setValue(Integer.toString(floor.getfloorNUm()));
                                        
                                        /** Set the value of the floor number*/
                                        map2.setFloorNum(floor.getfloorNUm());
                                    }
                                }
                            }
                        
                        }
                    
                    }
                    
                    if(!mapPath.equals("")){

                        mapPane.getChildren().removeAll(mapPane.getChildren());

                        Image pinImage = new Image(getClass().getResourceAsStream("/images/Generic_Location.png"));

                        Image newImage = new Image(getClass().getResourceAsStream(mapPath));

                        mapNewImage.setImage(newImage);

                        pin = new ImageView();

                        pin.setFitWidth(pinWidth);

                        pin.setFitHeight(pinHeight);

                        pin.setImage(pinImage);
                        
                       
                        paneDesc = new Pane();
                        
                        TextArea descText = new TextArea();
                        
                        Text descPOIText = new Text();
                        
                        descPOIText.setFont(Font.font(null, FontWeight.BOLD, 25));
                        
                        descPOIText.setText(newPOI.getDesc());
                        
                        ImageView descImage = new ImageView();
                        
                        Image descBubble = new Image(getClass().getResourceAsStream("/images/Bubble.png"));
                        
                        descImage.setImage(descBubble);
                       
                        descImage.setFitHeight(350);
                        
                        descImage.setFitWidth(550);
                        
                        paneDesc.setPrefSize(550, 350);
                        
                        paneDesc.setOnMouseClicked((MouseEvent event) -> {
                            
                            paneDesc.setVisible(false);
                        });
                        
                        
                        descText.setText(newPOI.getDesc());
                      
                        descText.setFont(Font.font(null, FontWeight.BOLD, 30));
                      
                        descText.setPrefSize(450,200);
                       
                        descText.setStyle("-fx-focus-color:transparent; -fx-text-border:transparent;");
                        
                        descText.setWrapText(true);
                        
                        descText.setLayoutX(275-230);
                        
                        descText.setLayoutY(175-150);
                        
                        descText.setOnMouseClicked((MouseEvent event) -> {
                            
                            descText.setVisible(false);
                            
                            paneDesc.setVisible(false);
                        });
                        paneDesc.getChildren().addAll(descImage,descText);
                        
                        mapPane.getChildren().addAll(mapNewImage,pin,paneDesc);
                        
                        paneDesc.setLayoutX(newPOI.getLocation().getx()-(550/2));
                        
                        paneDesc.setLayoutY(newPOI.getLocation().gety()-(350*1.5));
                        
                        pin.setX(newPOI.getLocation().getx()-(pinWidth/2));

                        pin.setY(newPOI.getLocation().gety()-(pinHeight));

                        zoom(0.5);
                    }
                }
            
            }
    
    }
    
     /**
     * Gets the value of the POI selected 
     * Calls the function ShowPOIOnCurrentMapto show it on the current Map
     * @param POIToshow the POI to show on the map
     */
    public void showPOIOnCurrentMap(POI POIToshow){
        
        int pinWidth = 116;
        
        int pinHeight = 120;
    
        if((bldg != null) && (floor != null)){
            
            if((POIToshow.getBldg().equals(bldg.getName())) && (POIToshow.getFloor()== floor.getfloorNUm())){
    
                String mapPath = "/images/"+floor.getMapFile();

                if(!mapPath.equals("")){

                            mapPane.getChildren().removeAll(mapNewImage,pin);

                            Image pinImage = new Image(getClass().getResourceAsStream("/images/Generic_Location.png"));

                            Image newImage = new Image(getClass().getResourceAsStream(mapPath));

                            mapNewImage.setImage(newImage);

                            pin = new ImageView();

                            pin.setFitWidth(pinWidth);

                            pin.setFitHeight(pinHeight);

                            pin.setImage(pinImage);

                            mapPane.getChildren().addAll(mapNewImage,pin);

                            pin.setX(POIToshow.getLocation().getx()-(pinWidth/2));

                            pin.setY(POIToshow.getLocation().gety()-(pinHeight));

                            zoom(0.5);
                        }
            }
        }
    
    
    }
    /**
     * Show POI layers using Pins for different layer 
     * Gets the name of the layer and the POI List and drop pins on them
     * @param POIList the list of POI in a specific layer
     * @param pinType the type of layer to show
     */
    public void showLayer(ArrayList<POI> POIList, String pinType){
    
        String mapPath ="";
        
        int pinWidth = 116;
        
        int pinHeight = 120;
        
        /** Setting the generic pin image */
        Image pinImage = new Image("/images/Generic_Location.png");
        
        /* Sets the pin image depending of the layer to show*/
       switch (pinType) {
           
           case "campus" -> pinImage = new Image(getClass().getResourceAsStream("/images/campus_Locator.png"));
           
           case "custom" -> pinImage = new Image(getClass().getResourceAsStream("/images/User_Created.png"));
           
           case "classroom" -> pinImage = new Image(getClass().getResourceAsStream("/images/Classroom_Locator.png"));
           
           case "lab" -> pinImage = new Image(getClass().getResourceAsStream("/images/Classroom_Locator.png"));
           
           case "washroom" -> pinImage = new Image(getClass().getResourceAsStream("/images/Washroom.png"));
           
           case "elevator" -> pinImage = new Image(getClass().getResourceAsStream("/images/elevator.png"));
           
           case "exit" -> pinImage = new Image(getClass().getResourceAsStream("/images/exit.png"));
           
           case "stairs" -> pinImage = new Image(getClass().getResourceAsStream("/images/Stairs.png"));
           
           case "restaurant" -> pinImage = new Image(getClass().getResourceAsStream("/images/Food_Locator.png"));
           
           default -> {
           }
       }
        
        if(!POIList.isEmpty()){
            
            /** Getting the building name and the map file for the layer to show*/
        
            POI samplePOI = POIList.get(0);
            
            pin.setVisible(true);

            String bldgName = samplePOI.getBldg();

            int floorNum = samplePOI.getFloor();

            ArrayList<Building> buildings = campMap.getBuilding();

            if(bldgName.equals("Campus Map")){

                mapPath = "/images/"+campMap.getMapFile();
                
                currentMap = "Campus Map";
                
                bldg = null;
                
                floor = null;
                
                bldgCombo.getSelectionModel().clearSelection();
                
                floorCombo.getSelectionModel().clearSelection();
                
                POICombo.getSelectionModel().clearSelection();
                
                /** Set the value of the map name since a new map will be displayed*/
                map2.setMapName("Campus Map");
        
                /** Set the value of the floor number*/
                map2.setFloorNum(0);

            }
            else {

                for(int j = 0; j < buildings.size(); j++){

                    if(bldgName.equals(buildings.get(j).getName())){

                        Building newBuilding = buildings.get(j);
                        

                        ArrayList<Floor> floors = newBuilding.getFloors();

                        for (Floor floor1 : floors) {

                            if(floor1.getfloorNUm() == floorNum){

                                mapPath = "/images/"+floor1.getMapFile(); 
                            }
                        }
                    }

                }

            }

            /** If a map file is found */

            if(!mapPath.equals("")){

                mapPane.getChildren().removeAll(mapPane.getChildren());

                Image newImage = new Image(getClass().getResourceAsStream(mapPath));

                mapNewImage.setImage(newImage);
                
                
                int pinsNumber = POIList.size();
                
                ArrayList<ImageView> pins = new ArrayList<>();
                  
                for(int l = 0; l < pinsNumber; l++){
                    
                    pins.add(new ImageView());
                    
                    pins.get(l).setImage(pinImage);

                    pins.get(l).setFitWidth(pinWidth);

                    pins.get(l).setFitHeight(pinHeight);
                    
                    pins.get(l).setX(POIList.get(l).getLocation().getx()-(pinWidth/2));
                    
                    pins.get(l).setY(POIList.get(l).getLocation().gety()-pinHeight);


                }

                /** Ad the pins to the map */
                for(int n = 0; n < pinsNumber; n++){
                    
                    if(n==0){
                        
                        mapPane.getChildren().addAll(mapNewImage,pins.get(0));
                    
                    }
                    else{
                    
                         mapPane.getChildren().addAll(pins.get(n));
                    
                    }
                }

                zoom(0.4);
            }
                
        }
    
    
    
    }
    
    /**
     * Sets the variable userInfo to get the current user data
     * @param  userInfo the UserData object to set as userInfo
     */
    public void setUserData(UserData userInfo){
        
        this.userInfo = userInfo;
    
    }
    
    /**
     * Controls the zoom property of the map
     * @param scaleValue the value of the scale to zoom in or out
     */
    private void zoom(double scaleValue) {
        double scrollH = mapScrollPane.getHvalue();
        double scrollV = mapScrollPane.getVvalue();
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        mapScrollPane.setHvalue(scrollH);
        mapScrollPane.setVvalue(scrollV);
   }
    
    /**
     * Informs the controller the user is getting a location to add a POI
     * @param value Boolean true if the user is adding a POI or false
     */
    public void setAdd(Boolean value){
    
        this.add = value;
    }
    
    /**
     * Used to zoom in the map displayed
     * @param event the event detected when the user uses the slider
     */
    @FXML
    void zoomminus(ActionEvent event) {
       double sliderVal = mainPageSlider.getValue();
       mainPageSlider.setValue(sliderVal + -0.1);
    }

    /**
     * Used to zoom out the map displayed
     * @param event the event detected when the user uses the slider
     */
    @FXML
    void zoomplus(ActionEvent event) {
        double sliderVal = mainPageSlider.getValue();
        mainPageSlider.setValue(sliderVal += 0.1);
    }
    
    /**
     * Edits a user favorite POI
     * Opens the edit POI page
     * @param event the vent detected when the user select edit POI
     */
    @FXML
    void editFav(ActionEvent event) {
        
        if(!selectedFavPOI.equals("")){
            
            ArrayList<POI> POIList = currentUser.getPOIs();
            
            for(int i = 0; i < POIList.size(); i++){
                
                POI newPOI = POIList.get(i);
                
                if(selectedFavPOI.equals(newPOI.getPOIName())){
                    
                  
                   
                    try {
                       
                        /** Load the edit POI view */
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editPOI.fxml"));

                        Parent root = loader.load();

                       
                        ((EditPOIController)loader.getController()).setUserData(userInfo);
                        
                        ((EditPOIController)loader.getController()).setPOI(newPOI);

                        ((EditPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                        /** Create the new window for the new view with a new scene graph*/
                        Stage stage = new Stage();

                        Scene scene = new Scene(root);

                        stage.setScene(scene);

                        Image appIcon = new Image("/images/Stacked.png");

                        stage.getIcons().add(appIcon);

                        stage.setTitle("GIS Edit POI");
                        
                        stage.setResizable(false);

                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.show();
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
        }

    }

    }
    
    /**
     * Edits a user custom POI
     * Opens the edit POI page
     * @param event the vent detected when the user select edit POI
     */
    @FXML
    void editCustom(ActionEvent event) {
        
       
        
        if(!selectedCustomPOI.equals("")){
            
            ArrayList<POI> POIList = currentUser.getPOIs();
            
            for(int i = 0; i < POIList.size(); i++){
                
                POI newPOI = POIList.get(i);
                
                if(selectedCustomPOI.equals(newPOI.getPOIName())){
                 try {
                       
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editPOI.fxml"));

                        Parent root = loader.load();

                        ((EditPOIController)loader.getController()).setUserData(userInfo);
                        
                        ((EditPOIController)loader.getController()).setPOI(newPOI);

                        ((EditPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

     
                        Stage stage = new Stage();

                        Scene scene = new Scene(root);

                        stage.setScene(scene);

                        Image appIcon = new Image("/images/Stacked.png");

                        stage.getIcons().add(appIcon);

                        stage.setTitle("GIS - Edit POI");
                        
                        stage.setResizable(false);

                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.show();
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
        }

    }
    
    }
    
    /**
     * Deletes a user custom POI
     * Opens the delete POI page
     * @param event the vent detected when the user select edit POI
     */
    @FXML
    void deleteCustom(ActionEvent event) {

        if(!selectedCustomPOI.equals("")){
            
            ArrayList<POI> POIList = currentUser.getPOIs();
            
            for(int i = 0; i < POIList.size(); i++){
                
                POI newPOI = POIList.get(i);
                
                if(selectedCustomPOI.equals(newPOI.getPOIName())){
                  
                    try {
                       
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deletePOI.fxml"));

                        Parent root = loader.load();

                        ((DeletePOIController)loader.getController()).setUserData(userInfo);
                        
                        ((DeletePOIController)loader.getController()).setPOI(newPOI);

                        ((DeletePOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

        
                        Stage stage = new Stage();

                        Scene scene = new Scene(root);

                        stage.setScene(scene);

                        Image appIcon = new Image("/images/Stacked.png");

                        stage.getIcons().add(appIcon);

                       
                        stage.setTitle("GIS - Delete POI");
                        
                        stage.setResizable(false);

                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.show();
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

        }
    
    }
    
    /**
     * Deletes a user favorite POI
     * Opens the delete favorite POI page
     * @param event the vent detected when the user select edit POI
     */
    @FXML
    void deleteFav(ActionEvent event) {
        
        
        
        
        if(!selectedFavPOI.equals("")){
            
            ArrayList<POI> POIList = currentUser.getPOIs();
            
            for(int i = 0; i < POIList.size(); i++){
                
                POI newPOI = POIList.get(i);
                
                if(selectedFavPOI.equals(newPOI.getPOIName())){
                    
                    try {
                       
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteFavPOI.fxml"));

                        Parent root = loader.load();

                        ((DeleteFavPOIController)loader.getController()).setUserData(userInfo);
                        
                        ((DeleteFavPOIController)loader.getController()).setPOI(newPOI);

                        ((DeleteFavPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

      
                        Stage stage = new Stage();

                        Scene scene = new Scene(root);

                        stage.setScene(scene);

                        Image appIcon = new Image("/images/Stacked.png");

                        stage.getIcons().add(appIcon);

                
                        stage.setTitle("GIS - Delete Favorite POI");
                        
                        stage.setResizable(false);

                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.show();
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

        }
    

    }
    
    @FXML
    void makeFav(ActionEvent event) {
        
        try{
        if(makeFav.isSelected()){
            
          if(currentMap.equals("Campus Map")){
                
                ArrayList<POI> campPOIs = new ArrayList<POI>();
                
                campPOIs = map2.getMap().getPOIs();
                
                for(int i =0; i < campPOIs.size(); i++){
                
                    if(SelectedBuilt.equals(campPOIs.get(i).getPOIName())){
                       
                    
                        POI newPOI = campPOIs.get(i);
                        
                        newPOI.setPOIFav(true);
                        
                        currentUser.getPOIs().add(newPOI);
                        
                        Boolean result = userInfo.getUserFile().UpdateUser(currentUser, currentUser);
                        
                        try {   
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));

                            Parent root = loader.load();

                            
                            ((MainPageController)loader.getController()).setUserData(userInfo);

                            Stage stage = (Stage) addPOIBtn.getScene().getWindow();

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
                
                
                }
            
            
            }
            else{
            
                if(floor!= null && bldg != null){
                
                    ArrayList<POI> floorPOIs = new ArrayList<>();
                    
                    floorPOIs = floor.getPOIs();
                    
                
                    for(int i = 0; i < floorPOIs.size(); i++){
                        
                        POI POItoFind = floorPOIs.get(i);
                        
                        String realPOIName =POItoFind.getPOIName().split("_")[0] + floor.getPOIs().get(i).getPOIName().split("_")[3];

                    
                        if(realPOIName.equals(SelectedBuilt)){
                        
                            POI POIfloor = floorPOIs.get(i);
                          
                             POIfloor.setPOIFav(true);
                        
                            currentUser.getPOIs().add(POIfloor);
                            
                            User updateUser = new User(currentUser.getEmail(),currentUser.getPassword(),currentUser.getSecQuestion(),currentUser.getSecAnswer(), 0);
                
                        
                
                             Boolean result = userInfo.getUserFile().UpdateUser(currentUser, currentUser);
                        
                         try {   
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));

                            Parent root = loader.load();

                            
                            ((MainPageController)loader.getController()).setUserData(userInfo);

                            Stage stage = (Stage) addPOIBtn.getScene().getWindow();

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
                    
                    
                    }
                
                }
            
            
            }
        
        }
        }
        catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }

    }

     /**
     * Opens the usual manual local web page GIS.html
     * Gets triggered when the user clicked on the help button and 
     * clicked on the hyperlink
     * @param event detected when the user clicked on the button
     */
    @FXML
    void openlink(ActionEvent event) throws MalformedURLException {
        
        String pagePath = filePath+"/GIS.html";
         
       try {
           File urlFile = new File(pagePath);
           Desktop.getDesktop().open(urlFile);
       } catch (IOException ex) {
           Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
       }
        
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
    
     /** 
     * Returns the code associated with a building name
     * @param name the name of the building
     * @return the code of the building
     */
    private String getBldgCode(String name){
        
        String code = "";
        
        for (String word : name.split(" ")) {
                
                code = code + word.charAt(0);
            
        }
        
       return code;
    
    }
    
     /** 
     * Returns the code associated with a POI type
     * @param name the type of the POI
     * @return the code of the type
     */
    private String getTypeCode(String type){
        
        String code;
    
        switch (type) {
            
           case "1" -> code = "B"; 
           
           case "2" -> code = "C";
           
           case "3" -> code = "W";
           
           case "4" -> code = "L";
           
           case "5" -> code = "CR";
           
           case "6" -> code = "TA";
           
           case "7" -> code = "MR";
           
           case "8" -> code = "MUR";
           
           case "9" -> code = "R";
           
           case "10" -> code = "H";
           
           case "11" -> code = "E";
           
           case "12" -> code = "T";
           
           case "13" -> code = "S";
           
           case "14" -> code = "RT";
         
           case "15" -> code = "Ex";
           
           default -> { code = "";
           }
       }
    
       return code;
    
    }
}

