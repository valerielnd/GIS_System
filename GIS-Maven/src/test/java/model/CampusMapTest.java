/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package model;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author valer
 */
public class CampusMapTest {
    
    public CampusMapTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addBuilding method, of class CampusMap.
     */
    @Test
    public void testAddBuilding() {
        System.out.println("addBuilding");
        Building building = null;
        CampusMap instance = new CampusMap("map.png");
        boolean result = instance.addBuilding(building);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Test of getBuilding method, of class CampusMap.
     */
    @Test
    public void testGetBuilding_0args() {
        System.out.println("getBuilding");
        CampusMap instance =new CampusMap("map.png");
        ArrayList<Building> expResult = new ArrayList<Building>();
        ArrayList<Building> result = instance.getBuilding();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getPOIs method, of class CampusMap.
     */
    @Test
    public void testGetPOIs() {
        System.out.println("getPOIs");
        CampusMap instance = new CampusMap("map.png");
        ArrayList<POI> expResult = new ArrayList();
        ArrayList<POI> result = instance.getPOIs();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getMapFile method, of class CampusMap.
     */
    @Test
    public void testGetMapFile() {
        System.out.println("getMapFile");
        CampusMap instance = new CampusMap("map.png");
        String expResult = "map.png";
        String result = instance.getMapFile();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getBuilding method, of class CampusMap.
     */
    @Test
    public void testGetBuilding_int() {
        System.out.println("getBuilding");
        int index = 0;
        CampusMap instance = new CampusMap("map.png");
        Building expResult = null;
        assertThrows(EmpytListException.class, ()->{instance.getBuilding(index);});   
    }
    
}
