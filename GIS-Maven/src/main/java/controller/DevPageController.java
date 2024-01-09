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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class for the Developer page
 * It contains the functions to handle all the events in the developer page
 * <br><br>
 * @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
 * @version 1.0
 */
public class DevPageController implements Initializable{
    
    @FXML
    private ListView<String> POIListview;

    @FXML
    private Button addPOIBtn;


    @FXML
    private ComboBox<String> bldgCombo;

    @FXML
    private MenuButton customBtn;


    @FXML
    private ListView<String> customPOIList;


    @FXML
    private ListView<String> editBuildingListview;

    @FXML
    private MenuButton favBtn;


    @FXML
    private ListView<String> favPOIList;

    @FXML
    private ComboBox<String> floorCombo;

    @FXML
    private ListView<String> layerList;


    @FXML
    private Slider mainPageSlider;

    @FXML
    private Pane mapPane;

    @FXML
    private ScrollPane mapScrollPane;


    @FXML
    private MenuButton editDelBtn;
  
    @FXML
    private HBox editBuildingBox;
    
    

    
    /** Non FXML instance variables */
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
    
    
    private User currentUser;
    
    private String selectedCustomPOI;
    
    private String selectFloorPOI;
    
    private String selectedCampusPOI;
    
    private String selectedBldg;
    
    private String selectedFavPOI;
    
    private ObservableList<String> appLayer;
    
    
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
        /** Set the default map name to Campus Map*/
        currentMap = "Campus Map"; 
        
        
        /** Set the dimension of the pin used to show location on the map */
        int pinWidth = 116;
        
        int pinHeight = 120;
        
        
        /** Create an instance of MapData2 to load the app metadata */
        String metadataPath = filePath+"/map6.json";
        
        map2 = new MapData2(metadataPath, currentMap);
        
        /** Get the Campus Map object from the MapData2 object*/
        campMap = map2.getMap();
        
        mapFile = campMap.getMapFile();
        
        
        /** Display the default map when the app launches */
        
        String mapPath = "/images/"+mapFile;
        
        mapPane.getChildren().removeAll(mapPane.getChildren());
        
        Image defaultImage = new Image(getClass().getResourceAsStream(mapPath));
        
        Image pinImage = new Image(getClass().getResourceAsStream("/images/Generic_Location.png"));
      
        mapNewImage = new ImageView();
        
        mapNewImage.setImage(defaultImage);
        
        pin = new ImageView();
        
        pin.setFitWidth(pinWidth);
        
        pin.setFitHeight(pinHeight);
        
        pin.setImage(pinImage);
        
        pin.setVisible(false);
        
        mapNewImage.setPreserveRatio(true);
        
        pin.setPreserveRatio(true);
        
        editBuildingBox.setPrefSize(140, 32);
        
        mapPane.getChildren().addAll(mapNewImage,pin);
        
        
        /** Add an event listener to the displayed map */
        mapNewImage.setOnMouseClicked((MouseEvent event) -> {
           
         
        
           getPixel(event);
        
       });
        
        
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
        
        /** Populate the list view under the Edit Building Button */
        
        ObservableList<String> bldgListEdit = FXCollections.observableArrayList();
        
        for(int i = 0; i < campMap.getBuilding().size(); i++){
            
           bldgListEdit.add(campMap.getBuilding().get(i).getName());
            
        }
        
        bldgListEdit.add("+Add Building");
        
        editBuildingListview.setItems(bldgListEdit);
        
        
        /** Add a listener to the list view of building */
        editBuildingListview.setOnMouseClicked((MouseEvent event) -> {

                /** On left click show the POI position on the map*/
               if(event.getButton()== MouseButton.PRIMARY){

                    selectedBldg = editBuildingListview.getSelectionModel().getSelectedItem();

                    ArrayList<Building> buildings = map2.getMap().getBuilding();
                    
                    if( selectedBldg.equals("+Add Building")){
                    
                        OpenAddBldgPage();
                    }
                    else{
                        for(int i =0; i< buildings.size(); i++){

                        Building currentBldg = buildings.get(i);

                            if(currentBldg.getName().equals(selectedBldg)){

                               OpenEditBldgPage(currentBldg);

                            }

                        }
                }

               }
               /** On right click, give the user the option to edit or delete the POI*/
               else if (event.getButton() == MouseButton.SECONDARY){
                   
                   selectedBldg = POIListview.getSelectionModel().getSelectedItem();

                   ArrayList<Building> buildings = map2.getMap().getBuilding();

                    for(int i =0; i< buildings.size(); i++){

                    Building currentBldg = buildings.get(i);

                        if(currentBldg.getName().equals(selectedBldg)){

                           OpenEditBldgPage(currentBldg);

                        }

                    }

               }

            });
        
        /** Add the campus POIs */
        
        ArrayList<POI> campusPOIs = campMap.getPOIs();
        
        if(!campusPOIs.isEmpty()){
            
            ObservableList<String> campPOIList = FXCollections.observableArrayList();
        
            for (POI campusPOI : campusPOIs) {
                
                campPOIList.add(campusPOI.getPOIName());
            }
                
            POIListview.setItems(campPOIList);
            
            POIListview.setOnMouseClicked((MouseEvent event) -> {

                /** On left click show the POI position on the map*/
               if(event.getButton()== MouseButton.PRIMARY){

                    editDelBtn.setVisible(false);

                    selectedCampusPOI = POIListview.getSelectionModel().getSelectedItem();

                    showPOI(selectedCampusPOI, campusPOIs);

               }
               /** On right click, give the user the option to edit or delete the POI*/
               else if (event.getButton() == MouseButton.SECONDARY){
                   

                   selectedCampusPOI = POIListview.getSelectionModel().getSelectedItem();

                   editDelBtn.setVisible(true);

               }

            });

             
            
        }
        
        
        /** Populate the list views of the current user favorite and custom POIS*/
        
        ObservableList<String> favPOIs = FXCollections.observableArrayList();
        
        ObservableList<String> customPOIs = FXCollections.observableArrayList();
        
        /** Get the information of the currently logged user from the userInfo variable*/
        userInfo = LoginPageController.getCurrentUser();
        
        currentUser = userInfo.getCurrentUser();
        
        /** Get the current user list of POIs */
        
        ArrayList<POI> POIList = currentUser.getPOIs();
        
        ArrayList<POI> currentUserPOIs = currentUser.getPOIs();
        
        if(POIList.size() > 0){
        
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
           
                /** On left click show the POI position on the map*/
               if(event.getButton()== MouseButton.PRIMARY){
                   
                    customBtn.setVisible(false);
               
                    
                    String selectedCustom = customPOIList.getSelectionModel().getSelectedItem();
                    
                    showPOI(selectedCustom,POIList);
              
               }
               /** On right click, give the user the option to edit or delete the POI*/
               else if (event.getButton() == MouseButton.SECONDARY){
                   
                   selectedCustomPOI = customPOIList.getSelectionModel().getSelectedItem();
                   
                   customBtn.setVisible(true);
               
               }
        
            });
            
            /** Adding Listener for the favorite POIs */
             favPOIList.setOnMouseClicked((MouseEvent event) -> {
           
               /** On left click show the POI position on the map*/
               if(event.getButton()== MouseButton.PRIMARY){
               
                    
                    favBtn.setVisible(false);
                    
                    String selectedFav = favPOIList.getSelectionModel().getSelectedItem();
                    
                   
                    
                    showPOI(selectedFav,POIList);
              
               }
               /** On right click, give the user the option to edit or delete the POI*/
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
             
             
                /** On left click show the layer on the map */
               if(event.getButton()== MouseButton.PRIMARY){
               
                    /** Showing Campus POI Layer */
                    String selectedLayer = layerList.getSelectionModel().getSelectedItem();
                    
                    if(selectedLayer.equals(campPOILayer)){
                    
                        showLayer(campusPOIs, "campus");
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
                   
               
               }
        
            });
        
        
        /** Set the zoom so that entire map appears when the user first load the app*/
        zoom(0.5);
    } 
    
    /** FXML Methods */
    
    /**
     * Gets the x position and y position when a user clicked on the map
     * @param event the mouse event that was detected on the map
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
                
                bldg = null;
                
                floor = null;
                
                map2.setFloorNum(0);
                
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
                
                	POIListview.setItems(campPOIList);
                        
                        POIListview.setOnMouseClicked((MouseEvent mevent) -> {

                            /** On left click show the POI position on the map*/
                           if(mevent.getButton()== MouseButton.PRIMARY){

                                editDelBtn.setVisible(false);

                                String selectedPOI = POIListview.getSelectionModel().getSelectedItem();

                                showPOI(selectedPOI, campusPOIs);

                           }
                            /** On right click, give the user the option to edit or delete the POI*/
                            else if (mevent.getButton() == MouseButton.SECONDARY){
                                
                                selectedCampusPOI = POIListview.getSelectionModel().getSelectedItem();
                                
                               
                                editDelBtn.setVisible(true);

                            }

                        });
                        
                
                 }


                

    }
    
   
    
    /**
     * Adds a user created POI when a user clicks on the addPOIBtn
     * Opens the add POI page trough the addPOI FXML page
     * @param event the mouse even that was detected on the button
     */
    @FXML
    void addPOI(ActionEvent event) {
      try {
           
                /** Load the AddPOI view */
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addPOI.fxml"));

                Parent root = loader.load();

                /** Transfer the map Data and user info to the controller of the  new view */
                ((AddPOIController)loader.getController()).setMapData2(map2);
                
                ((AddPOIController)loader.getController()).setUserData(userInfo);

                ((AddPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                /** If the POI Position was already chosen on the map*/
                if(POIpos != null){
                    
                    ((AddPOIController)loader.getController()).setPOIpos(POIpos);
                }

                /** Create the new window for the new view with a new scene graph */
                Stage stage = new Stage();

                Scene scene = new Scene(root);

                stage.setScene(scene);

                Image appIcon = new Image("/images/Stacked.png");

                stage.getIcons().add(appIcon);

                stage.setTitle("GIS - Add POI");
                
                stage.setResizable(false);

                /** Make sure that the parent page cannot be closed before the child page */
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.show();
           
            } catch (Exception e) {

                e.printStackTrace();
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
     * Adds a POI in the current floor POI list
     * Gets the name of the POI selected
     * Opens the Add built-in POI page to enter the POI details
     * @param event the even detected when the user clicked on the add button
     */
    @FXML
    void addBuiltIn(ActionEvent event) {
        
        if(currentMap.equals("Campus Map")){
            
             /** Set the value of the map name since a new map will be displayed*/
             map2.setMapName("Campus Map");
        
              /** Set the value of the floor number*/
              map2.setFloorNum(0);
            
            try {
           
                    /** Load the delete built-in POI view */
                    FXMLLoader loader = new FXMLLoader(DevPageController.class.getResource("/view/addBuiltin.fxml"));

                    Parent root = loader.load();

                    /** Transfer the map Data and user info to the controller of the  new view */
                    ((AddBuiltInPOIController)loader.getController()).setMapData2(map2);

                    ((AddBuiltInPOIController)loader.getController()).setUserData(userInfo);

                    ((AddBuiltInPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                     /** If the POI Position was already chosen on the map*/
                    if(POIpos != null){
                    
                        ((AddBuiltInPOIController)loader.getController()).setPOIpos(POIpos);
                    }
                    /** Create the new window for the new view with a new scene graph */
                    Stage stage = new Stage();

                    Scene scene = new Scene(root);

                    stage.setScene(scene);

                    Image appIcon = new Image("/images/Stacked.png");

                    stage.getIcons().add(appIcon);

                    stage.setTitle("GIS Add POI");

                    stage.setResizable(false);

                    /** Make sure that the parent page cannot be closed before the child page */
                    stage.initModality(Modality.APPLICATION_MODAL);

                    stage.show();

            } catch (IOException e) {
            }
        
        }
        
        else if(bldg != null && floor != null ){
        
            ArrayList<POI> POIs = floor.getPOIs();
        
             try {
           
                    /** Load the delete built-in POI view */
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addBuiltin.fxml"));

                    Parent root = loader.load();

                    /** Transfer the map Data and user info to the controller of the  new view */
                    ((AddBuiltInPOIController)loader.getController()).setMapData2(map2);

                    ((AddBuiltInPOIController)loader.getController()).setUserData(userInfo);

                    ((AddBuiltInPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                     /** If the POI Position was already chosen on the map*/
                    if(POIpos != null){
                    
                        ((AddBuiltInPOIController)loader.getController()).setPOIpos(POIpos);
                    }
                    /** Create the new window for the new view with a new scene graph */
                    Stage stage = new Stage();

                    Scene scene = new Scene(root);

                    stage.setScene(scene);

                    Image appIcon = new Image("/images/Stacked.png");

                    stage.getIcons().add(appIcon);

                    stage.setTitle("GIS Add POI");

                    stage.setResizable(false);

                    /** Make sure that the parent page cannot be closed before the child page */
                    stage.initModality(Modality.APPLICATION_MODAL);

                    stage.show();

            } catch (IOException e) {
            }
        
        }

    }
    
    
    /**
     * Deletes a POI in the current floor POI list
     * Gets the name of the POI selected
     * Opens the delete built-in POI page to confirm the deletion
     * @param event the even detected when the user clicked on the delete button
     */
    @FXML
    void deletePOI(ActionEvent event) {
       
        if(selectedCampusPOI != null && !selectedCampusPOI.equals("") && currentMap.equals("Campus Map")){
            
            ArrayList<POI> campusPOIs = campMap.getPOIs();
            
            for (POI campusPOI : campusPOIs) {
                
                if(campusPOI.getPOIName().equals(selectedCampusPOI)){
                
                    try {
           
                            /** Load the delete built-in POI view */
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteBuiltin.fxml"));

                            Parent root = loader.load();

                            /** Transfer the map Data and user info to the controller of the  new view */
                            ((DeleteBuiltInPOIController)loader.getController()).setMapData2(map2);

                            ((DeleteBuiltInPOIController)loader.getController()).setUserData(userInfo);

                            ((DeleteBuiltInPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                   
                            ((DeleteBuiltInPOIController)loader.getController()).setPOI(campusPOI);
                            
                             
                            /** Create the new window for the new view with a new scene graph */
                            Stage stage = new Stage();

                            Scene scene = new Scene(root);

                            stage.setScene(scene);

                            Image appIcon = new Image("/images/Stacked.png");

                            stage.getIcons().add(appIcon);

                            stage.setTitle("GIS Delete POI");

                            stage.setResizable(false);

                            /** Make sure that the parent page cannot be closed before the child page */
                            stage.initModality(Modality.APPLICATION_MODAL);

                            stage.show();

                        } catch (IOException e) {
                        }
                }
            
            
            }
        
        
        }
        
        else if(selectFloorPOI != null && !selectFloorPOI.equals("")){
        
            if(bldg != null && floor != null ){
            
                ArrayList<POI> POIs = floor.getPOIs();
                
                for (POI currentPOI : POIs) {
                    
                    String currentPOIName = currentPOI.getPOIName().split("_")[0] + currentPOI.getPOIName().split("_")[3];
                    
                    if(currentPOIName.equals(selectFloorPOI)){
   
                        try {
           
                            /** Load the delete built-in POI view */
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteBuiltin.fxml"));

                            Parent root = loader.load();

                            /** Transfer the map Data and user info to the controller of the  new view */
                            ((DeleteBuiltInPOIController)loader.getController()).setMapData2(map2);

                            ((DeleteBuiltInPOIController)loader.getController()).setUserData(userInfo);

                            ((DeleteBuiltInPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                      
                            ((DeleteBuiltInPOIController)loader.getController()).setPOI(currentPOI);
                            

                            /** Create the new window for the new view with a new scene graph */
                            Stage stage = new Stage();

                            Scene scene = new Scene(root);

                            stage.setScene(scene);

                            Image appIcon = new Image("/images/Stacked.png");

                            stage.getIcons().add(appIcon);

                            stage.setTitle("GIS Delete POI");

                            stage.setResizable(false);

                            /** Make sure that the parent page cannot be closed before the child page */
                            stage.initModality(Modality.APPLICATION_MODAL);

                            stage.show();

                        } catch (IOException e) {
                        }
                    }
                }
            
            }
        
        }

    }
    
     /**
     * Edits a POI in the current floor POI list
     * Gets the name of the POI selected
     * Opens the delete built-in POI page to confirm the deletion
     * @param event the even detected when the user clicked on the delete button
     */
    @FXML
    void editPOI(ActionEvent event) {
        
        if(selectedCampusPOI != null && !selectedCampusPOI.equals("") && currentMap.equals("Campus Map")){
        
    
            
            ArrayList<POI> campusPOIs = campMap.getPOIs();
            
            for (POI campusPOI : campusPOIs) {
                
                if(campusPOI.getPOIName().equals(selectedCampusPOI)){
                
                    try {
           
                            /** Load the delete built-in POI view */
                            FXMLLoader loader = new FXMLLoader(DevPageController.class.getResource("/view/editBuiltPOI.fxml"));
                            
                            

                            Parent root = loader.load();

                            /** Transfer the map Data and user info to the controller of the  new view */
                            ((EditBuiltInPOIController)loader.getController()).setMapData2(map2);

                            ((EditBuiltInPOIController)loader.getController()).setUserData(userInfo);

                            ((EditBuiltInPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                      
                            ((EditBuiltInPOIController)loader.getController()).setPOI(campusPOI);
                            
                            /** If the POI Position was already chosen on the map*/
                             if(POIpos != null){
                    
                                ((EditBuiltInPOIController)loader.getController()).setPOIpos(POIpos);
                             }

                             
                            /** Create the new window for the new view with a new scene graph */
                            Stage stage = new Stage();

                            Scene scene = new Scene(root);

                            stage.setScene(scene);

                            Image appIcon = new Image("/images/Stacked.png");

                            stage.getIcons().add(appIcon);

                            stage.setTitle("GIS Delete POI");

                            stage.setResizable(false);

                            /** Make sure that the parent page cannot be closed before the child page */
                            stage.initModality(Modality.APPLICATION_MODAL);

                            stage.show();

                        } catch (IOException e) {
                        }
                }
            
            
            }
        
        
        }
        
        else if(selectFloorPOI != null && !selectFloorPOI.equals("")){
        
            if(bldg != null && floor != null ){
            
                ArrayList<POI> POIs = floor.getPOIs();
                
                for (POI currentPOI : POIs) {
                    
                    String currentPOIName = currentPOI.getPOIName().split("_")[0] + currentPOI.getPOIName().split("_")[3];
                    
                    if(currentPOIName.equals(selectFloorPOI)){
   
                        try {
           
                            /** Load the delete built-in POI view */
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editBuiltPOI.fxml"));

                            Parent root = loader.load();

                            /** Transfer the map Data and user info to the controller of the  new view */
                            ((EditBuiltInPOIController)loader.getController()).setMapData2(map2);

                            ((EditBuiltInPOIController)loader.getController()).setUserData(userInfo);

                            ((EditBuiltInPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                      
                            ((EditBuiltInPOIController)loader.getController()).setPOI(currentPOI);
                            
                            /** If the POI Position was already chosen on the map*/
                             if(POIpos != null){
                    
                                ((EditBuiltInPOIController)loader.getController()).setPOIpos(POIpos);
                             }
                            /** Create the new window for the new view with a new scene graph */
                            Stage stage = new Stage();

                            Scene scene = new Scene(root);

                            stage.setScene(scene);

                            Image appIcon = new Image("/images/Stacked.png");

                            stage.getIcons().add(appIcon);

                            stage.setTitle("GIS Delete POI");

                            stage.setResizable(false);

                            /** Make sure that the parent page cannot be closed before the child page */
                            stage.initModality(Modality.APPLICATION_MODAL);

                            stage.show();

                        } catch (IOException e) {
                        }
                    }
                }
            
            }
        
        }

    }
    
    /**
     * To zoom in when browsing the current map
     * Called when the user click on the zoom "+" button
     * @param event detected when the user clicked on the button
     */
    @FXML
    void zoomminus(ActionEvent event) {
       
       double sliderVal = mainPageSlider.getValue();
       
       mainPageSlider.setValue(sliderVal + -0.1);
    }

    /**
     * To zoom in when browsing the current map
     * Called when the user click on the zoom "-" button
     * @param event detected when the user clicked on the button
     */
    @FXML
    void zoomplus(ActionEvent event) {
        double sliderVal = mainPageSlider.getValue();
        mainPageSlider.setValue(sliderVal += 0.1);
    }
    
    /**
     * Edits the selected POI in the favorite list view
     * Opens the edit POI page
     * Called when the user clicked on the edit button
     * @param event detected when the user clicked on the button
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

                        /** Transfer the current user data and the POI to edit to the page controller*/
                  
                        ((EditPOIController)loader.getController()).setUserData(userInfo);
                        
                        ((EditPOIController)loader.getController()).setPOI(newPOI);

                        ((EditPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                        /**Create the new window for the new view with a new scene graph*/
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
     * Edits the selected POI in the custom list view
     * Opens the edit POI page
     * Called when the user clicked on the edit button
     * @param event detected when the user clicked on the button
     */
    @FXML
    void editCustom(ActionEvent event) {
        
        if(!selectedCustomPOI.equals("")){
            
            ArrayList<POI> POIList = currentUser.getPOIs();
            
            for(int i = 0; i < POIList.size(); i++){
                
                POI newPOI = POIList.get(i);
                
                if(selectedCustomPOI.equals(newPOI.getPOIName())){
                    
                   
                    try {
                       
                        /** Load the edit POI view */
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editPOI.fxml"));

                        Parent root = loader.load();

                         /** Transfer the current user data and the POI to edit to the page controller*/
                        ((EditPOIController)loader.getController()).setUserData(userInfo);
                        
                        ((EditPOIController)loader.getController()).setPOI(newPOI);

                        ((EditPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                        /**Create the new window for the new view with a new scene graph*/
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
     * Deletes the selected POI from the custom list view
     * Opens the delete POI page
     * Called when the user clicked on the delete button
     * @param event detected when the user clicked on the button
     */
    @FXML
    void deleteCustom(ActionEvent event) {

        if(!selectedCustomPOI.equals("")){
            
            ArrayList<POI> POIList = currentUser.getPOIs();
            
            for(int i = 0; i < POIList.size(); i++){
                
                POI newPOI = POIList.get(i);
                
                if(selectedCustomPOI.equals(newPOI.getPOIName())){
                    
                    
                    try {
                       
                        /** Load the delete POI view */
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deletePOI.fxml"));

                        Parent root = loader.load();

                        /** Transfer the current user data and the POI to edit to the page controller*/
                        ((DeletePOIController)loader.getController()).setUserData(userInfo);
                        
                        ((DeletePOIController)loader.getController()).setPOI(newPOI);

                        ((DeletePOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                         /**Create the new window for the new view with a new scene graph*/
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
     * Deletes the selected POI from the Favorite list view
     * Opens the delete POI page
     * Called when the user clicked on the delete button
     * @param event detected when the user clicked on the button
     */
    @FXML
    void deleteFav(ActionEvent event) {
        
        if(!selectedFavPOI.equals("")){
            
            ArrayList<POI> POIList = currentUser.getPOIs();
            
            for(int i = 0; i < POIList.size(); i++){
                
                POI newPOI = POIList.get(i);
                
                if(selectedFavPOI.equals(newPOI.getPOIName())){
                    
                    
                    try {
                       
                        /** Load the delete POI view */
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteFavPOI.fxml"));

                        Parent root = loader.load();

                        /** Transfer the current user data and the POI to edit to the page controller*/
                        ((DeleteFavPOIController)loader.getController()).setUserData(userInfo);
                        
                        ((DeleteFavPOIController)loader.getController()).setPOI(newPOI);

                        ((DeleteFavPOIController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());

                         /**Create the new window for the new view with a new scene graph*/
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

     /**
     * Opens the usual manual local web page GIS.html
     * Gets triggered when the user clicked on the help button and 
     * clicked on the hyperlink
     * @param event detected when the user clicked on the button
     */
    @FXML
    void openlink(ActionEvent event) throws MalformedURLException {
        
       /** Load the web page file and open it in a browser */
        String pagePath = filePath+"/GIS.html";
         
       try {
           File urlFile = new File(pagePath);
           Desktop.getDesktop().open(urlFile);
       } catch (IOException ex) {
           Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }

    
    /** Non FXML Methods*/
    
    
    /**
     * Used to zoom in and out the current displayed map
     * by using the ScrollPane Hvalue and Vvalue
     * @param scaleValue the scale numerical value to zoom the map
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
    * Gets the value of the POI selected 
    * Calls the function ShowPOIOnCurrentMapto show it on the current Map
    * @param POIName the name of the POI to show on the map
    * @param list the list where the POI is a member of
    */
    public void showPOI(String POIName, ArrayList<POI> list){
        
        /** Set the dimension of the PIN used to show a position on the map*/
        int pinWidth = 116;
        
        int pinHeight = 120;
        
        String mapPath ="";
        
        
        /** Get the name of the building on which the POIs are */
        for(int i = 0; i < list.size(); i++){
                
                POI newPOI = list.get(i);
                
                if(POIName.equals(newPOI.getPOIName())){
                    
                    pin.setVisible(true);
                    
                    String bldgName = newPOI.getBldg();
                    
                    int floorNum = newPOI.getFloor();
                    
                    ArrayList<Building> buildings = campMap.getBuilding();
                    
                    /** If the POIs to show are on the campus map*/
                    if(bldgName.equals("Campus Map")){
                    
                        mapPath = "/images/"+campMap.getMapFile();
                        
                        
                        currentMap = "Campus Map";
                
                        bldg = null;

                        floor = null;

                        bldgCombo.getSelectionModel().clearSelection();

                        floorCombo.getSelectionModel().clearSelection();
                        
                        /** Set the value of the map name since a new map will be displayed*/
                        map2.setMapName("Campus Map");
        
                        /** Set the value of the floor number*/
                        map2.setFloorNum(0);
                   
                    }
                    else {
                    
                        /** If it is one of the 3 other buildings*/
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
                    
                    /** If a map file for the building where the POIs are is found*/
                    
                    /** Show in the map the position of the POI using the pin Image*/
                    /** Show the POI description using the bubble image and the text area in new pane */
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
     * Show POI layers using Pins for different layer 
     * Gets the name of the layer and the POI List and drop pins on them
     * @param POIList the list of POI in a specific layer
     * @param pinType the type of layer to show
     */
    public void showLayer(ArrayList<POI> POIList, String pinType){
    
        /** Variable to hold the map file */
        String mapPath ="";
        
        /** Set the dimension of the PIN used to show a position on the map*/
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
        
       /** If the list of POI to show is not empty*/
        if(!POIList.isEmpty()){
            
            /** Getting the building name and the map file for the layer to show*/
        
            POI samplePOI = POIList.get(0);
            
            pin.setVisible(true);

            String bldgName = samplePOI.getBldg();

            int floorNum = samplePOI.getFloor();

            ArrayList<Building> buildings = campMap.getBuilding();

            /** If the layer to show is on the campus map*/
            if(bldgName.equals("Campus Map")){

                mapPath = "/images/"+campMap.getMapFile();
                
                currentMap = "Campus Map";
                
                bldg = null;
                
                floor = null;
                
                bldgCombo.getSelectionModel().clearSelection();
                
                floorCombo.getSelectionModel().clearSelection();
                
                
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
     * Detects events on the floor combo box in the main page
     * Gets the number of the floor selected
     * Populates the POI combo box with the selected floor POIs
     */
    void floorComboAction() {
        
        /** Get the floor number selected*/
        String floorNum = floorCombo.getSelectionModel().getSelectedItem();
        
        /** Set the dimension of the PIN used to show a position on the map*/
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
        if(floorPOIList != null){
            /** Populate the POI combo box with the current floor POIs*/
            POIListview.setItems(floorPOIList);

            ArrayList<POI> floorPOIs = floor.getPOIs();
            
            POIListview.setOnMouseClicked((MouseEvent event) -> {

                    /** On left click show the POI position on the map*/
                   if(event.getButton()== MouseButton.PRIMARY){

                        editDelBtn.setVisible(false);

                        String selectedPOI = POIListview.getSelectionModel().getSelectedItem();

                        POIListAction(selectedPOI);

                   }
                   /** On right click, give the user the option to edit or delete the POI*/
                   else if (event.getButton() == MouseButton.SECONDARY){
                       

                       selectFloorPOI = POIListview.getSelectionModel().getSelectedItem();

                       editDelBtn.setVisible(true);

                   }

                });
           
        }    
    }
    
    /**
     * Gets the value of the POI selected 
     * Calls the function ShowPOI to show it on the current Map
     * @param POIvalue the name of the POI selected
     */
    public void POIListAction(String POIvalue){
        
       
        /** Get the current floor POIs */ 
        ArrayList<POI> currentPOIs = floor.getPOIs();
        
        
        for (POI currentPOI : currentPOIs) {

            String currentPOIName = currentPOI.getPOIName().split("_")[0] + currentPOI.getPOIName().split("_")[3];
            
            
            if(POIvalue.equals(currentPOIName)){
                
         
               showPOI(currentPOI.getPOIName(), currentPOIs);
                
                
            
            }
        }
    
    
    
    }
    
    /**
     * Used to set the current user data
     * @param userInfo an instance of UserData which contains the user data
     */
    public void setUserData(UserData userInfo){
        
        this.userInfo = userInfo;
    
    }
    
    /**
     * Sets the map to the configuration it had before opening a new page
     * @param mapInfoController the MapData object that contains all the information needed
     */
    public void setBuilgindNFloor(MapData2 mapInfoController){
     
        String bldgName = mapInfoController.getMapName();
                            
        int floorNum = mapInfoController.getFloorNum();
        
        
        map2.setMapName(bldgName);
        
        currentMap = bldgName;
        
       

        ArrayList<Building> buildings = map2.getMap().getBuilding();

        if(currentMap.equals("Campus Map")){
        
           bldgCombo.getSelectionModel().clearSelection();

           floorCombo.getSelectionModel().clearSelection();
           
           /** Set the value of the map name since a new map will be displayed*/
           map2.setMapName("Campus Map");
        
           /** Set the value of the floor number*/
           map2.setFloorNum(0);
    
        }
        else{
            for(int i =0; i < buildings.size(); i++){

                   Building currentBldg = buildings.get(i);

                   if(currentBldg.getName().equals(currentMap)){

                           bldg = currentBldg;

                   }

            }
            map2.setMapName(bldg.getName());
            
            bldgCombo.setValue(bldg.getName());

            ArrayList<Floor> floors = bldg.getFloors();

            for(int i =0; i< floors.size(); i++){

                   Floor currentFloor = floors.get(i);

                   if(currentFloor.getfloorNUm() == floorNum){

                           floor = currentFloor;

                   }

            }
            map2.setFloorNum(floor.getfloorNUm());
            
            floorCombo.setValue(Integer.toString(floor.getfloorNUm()));
        }
    }
   
    private void OpenEditBldgPage(Building bldgToEdit){
    
         try {

            /** Load the delete POI view */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editBuilding.fxml"));

            Parent root = loader.load();

            /** Transfer the current user data and the POI to edit to the page controller*/
            ((EditBuildingController)loader.getController()).setUserData(userInfo);
            
            ((EditBuildingController)loader.getController()).setMapData2(map2);

            ((EditBuildingController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());
            
            if(bldgToEdit!= null){
            
                ((EditBuildingController)loader.getController()).setBuilding(bldgToEdit);
            
            }
            /** If the POI Position was already chosen on the map*/
            if(POIpos != null){

               
               ((EditBuildingController)loader.getController()).setBldgpos(POIpos);
            }

             /**Create the new window for the new view with a new scene graph*/
            Stage stage = new Stage();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            Image appIcon = new Image("/images/Stacked.png");

            stage.getIcons().add(appIcon);

            stage.setTitle("GIS Edit Building");

            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    private void OpenAddBldgPage(){
    
         try {

            /** Load the delete POI view */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddBuilding.fxml"));

            Parent root = loader.load();

            /** Transfer the current user data and the POI to edit to the page controller*/
            ((AddBuildingController)loader.getController()).setUserData(userInfo);
            
            ((AddBuildingController)loader.getController()).setMapData2(map2);

            ((AddBuildingController)loader.getController()).setStage((Stage) addPOIBtn.getScene().getWindow());
            

            /** If the POI Position was already chosen on the map*/
            if(POIpos != null){

               ((AddBuildingController)loader.getController()).setBldgpos(POIpos);
            }

             /**Create the new window for the new view with a new scene graph*/
            Stage stage = new Stage();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            Image appIcon = new Image("/images/Stacked.png");

            stage.getIcons().add(appIcon);

            stage.setTitle("GIS Edit Building");

            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        }
        catch (Exception e) {

            e.printStackTrace();
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
}
