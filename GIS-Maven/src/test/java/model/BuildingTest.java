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
public class BuildingTest {
    
    public BuildingTest() {
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
     * Test of getName method, of class Building.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Vec2 location = new Vec2(1,1);
        Building instance = new Building("testname",location,"text");
        String expResult = "testname";
        String result = instance.getName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setBldgName method, of class Building.
     */
    @Test
    public void testSetBldgName() {
        System.out.println("setBldgName");
        String bldgName = "Building2";
        Building instance = new Building("Building", new Vec2(2,3), "New Building");
        instance.setBldgName(bldgName);
        assertEquals(bldgName, instance.getName());
    }

    /**
     * Test of getFloorNum method, of class Building.
     */
    @Test
    public void testGetFloorNum() {
        System.out.println("getFloorNum");
        Building instance = new Building("Building", new Vec2(2,3), "New Building");
        int expResult = 0;
        int result = instance.getFloorNum();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getFloors method, of class Building.
     */
    @Test
    public void testGetFloors() {
        System.out.println("getFloors");
        Building instance =new Building("Building", new Vec2(2,3), "New Building");
        ArrayList<Floor> expResult = new ArrayList<Floor>();
        ArrayList<Floor> result = instance.getFloors();
        assertEquals(expResult, result);
    }

    /**
     * Test of addFloor method, of class Building.
     */
    @Test
    public void testAddFloor() {
        System.out.println("addFloor");
        Floor newfloor = null;
        Building instance = new Building("Building", new Vec2(2,3), "New Building");
        boolean result = instance.addFloor(newfloor);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Test of removeFloor method, of class Building.
     */
    @Test
    public void testRemoveFloor() {
        System.out.println("removeFloor");
        Floor floorToRemove = null;
        Building instance = new Building("Building", new Vec2(2,3), "New Building");
        assertThrows(EmpytListException.class, ()->{instance.removeFloor(floorToRemove);});   
    }

    /**
     * Test of setBuildingDescription method, of class Building.
     */
    @Test
    public void testSetBuildingDescription() {
        System.out.println("setBuildingDescription");
        String buildingDescription = "New Description";
        Building instance = new Building("Building", new Vec2(2,3), "New Building");
        instance.setBuildingDescription(buildingDescription);
        assertEquals(buildingDescription, instance.getDescription());
    }

    /**
     * Test of getDescription method, of class Building.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Vec2 location = new Vec2(1,1);
        Building instance = new Building("text",location,"testDescription");
        String expResult = "testDescription";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setBuildingLocation method, of class Building.
     */
    @Test
    public void testSetBuildingLocation() {
       System.out.println("getLocation");
        Vec2 expResult = new Vec2(1,1);
        Building instance = new Building("text",expResult,"testDescription");
        Vec2 result = instance.getLocation();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLocation method, of class Building.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        Building instance = new Building("Building", new Vec2(2,3), "New Building");
        Vec2 secLocation = new Vec2(2,3);
        Vec2 result = instance.getLocation();
        boolean expectResult = true;
        assertEquals(expectResult,secLocation.isEqual(result));
        
    }

    /**
     * Test of isEqual method, of class Building.
     */
    @Test
    public void testIsEqual() {
        System.out.println("isEqual");
        Building bldg2 = new Building("Building", new Vec2(2,3), "New Building");
        Building instance = new Building("Building", new Vec2(2,3), "New Building");
        boolean expResult = true;
        boolean result = instance.isEqual(bldg2);
        assertEquals(expResult, result);
       
    }
    
}
