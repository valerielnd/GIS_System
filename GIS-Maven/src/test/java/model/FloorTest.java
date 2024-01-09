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
public class FloorTest {
    
    public FloorTest() {
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
     * Test of getfloorNUm method, of class Floor.
     */
    @Test
    public void testGetfloorNUm() {
        System.out.println("getfloorNUm");
        Floor instance = new Floor("Building",100,  "img.png", new ArrayList());
        int expResult = 100;
        int result = instance.getfloorNUm();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setFloorNum method, of class Floor.
     */
    @Test
    public void testSetFloorNum() {
        System.out.println("setFloorNum");
        int newFloorNum = 2;
        Floor instance = new Floor("Building",100,  "img.png", new ArrayList());
        instance.setFloorNum(newFloorNum);
         assertEquals(instance.getfloorNUm(), newFloorNum);
    }

    /**
     * Test of getPOIs method, of class Floor.
     */
    @Test
    public void testGetPOIs() {
        System.out.println("getPOIs");
        Floor instance = new Floor();
        ArrayList<POI> expResult = new ArrayList<POI>();
        ArrayList<POI> result = instance.getPOIs();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getMapFile method, of class Floor.
     */
    @Test
    public void testGetMapFile() {
        System.out.println("getMapFile");
        Floor instance = new Floor("Building",100,  "img.png", new ArrayList());
        String expResult = "img.png";
        String result = instance.getMapFile();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setFloorFileMap method, of class Floor.
     */
    @Test
    public void testSetFloorFileMap() {
        System.out.println("setFloorFileMap");
        String newMapFile = "MC1.png";
        Floor instance = new Floor();
        instance.setFloorFileMap("MC1.png");
        assertEquals(newMapFile, instance.getMapFile());
    }

    /**
     * Test of getBldgName method, of class Floor.
     */
    @Test
    public void testGetBldgName() {
        System.out.println("getBldgName");
        Floor instance = new Floor("Building",100,  "img.png", new ArrayList());
        String expResult = "Building";
        String result = instance.getBldgName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setBlgdName method, of class Floor.
     */
    @Test
    public void testSetBlgdName() {
        System.out.println("setBlgdName");
        String newBlgdName = "Building2";
        Floor instance = new Floor();
        instance.setBlgdName(newBlgdName);
        assertEquals(newBlgdName, instance.getBldgName());
    }

    /**
     * Test of addPOI method, of class Floor.
     */
    @Test
    public void testAddPOI() {
        System.out.println("addPOI");
        POI newPOI = null;
        Floor instance = new Floor();
        boolean expResult = false;
        boolean result = instance.addPOI(newPOI);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of searchPOI method, of class Floor.
     */
    @Test
    public void testSearchPOI() {
        System.out.println("searchPOI");
        String targetPOIName = "";
        Floor instance = new Floor();
        POI expResult = null;
        POI result = instance.searchPOI(targetPOIName);
        assertEquals(expResult, result);
       
    }
    
}
