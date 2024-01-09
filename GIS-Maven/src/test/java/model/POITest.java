/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package model;

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
public class POITest {
    
    public POITest() {
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
     * Test of getBldg method, of class POI.
     */
    @Test
    public void testGetBldg() {
        System.out.println("getBldg");
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        String expResult = "Middlesex College";
        String result = instance.getBldg();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBldg method, of class POI.
     */
    @Test
    public void testSetBldg() {
        System.out.println("setBldg");
        String newPOIBldgName = "Alumni Hall";
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        instance.setBldg(newPOIBldgName);
        assertEquals("Alumni Hall", instance.getBldg());
    }

    /**
     * Test of getFloor method, of class POI.
     */
    @Test
    public void testGetFloor() {
        System.out.println("getFloor");
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        int expResult = 1;
        int result = instance.getFloor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFloor method, of class POI.
     */
    @Test
    public void testSetFloor() {
        System.out.println("setFloor");
        int newPOIFloorNum = 3;
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        instance.setFloor(newPOIFloorNum);
        assertEquals(3, instance.getFloor());
    }

    /**
     * Test of getPOIName method, of class POI.
     */
    @Test
    public void testGetPOIName() {
        System.out.println("getPOIName");
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        String expResult = "Grad Club";
        String result = instance.getPOIName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPOIName method, of class POI.
     */
    @Test
    public void testSetPOIName() {
        System.out.println("setPOIName");
        String newPOIName = "Collab Room";
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        instance.setPOIName(newPOIName);
        assertEquals("Collab Room", instance.getPOIName());
    }

    /**
     * Test of getType method, of class POI.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        int expResult = 14;
        int result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of setType method, of class POI.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        int newPOIType = 5;
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        instance.setType(newPOIType);
        assertEquals(5, instance.getType());
    }

    /**
     * Test of getDesc method, of class POI.
     */
    @Test
    public void testGetDesc() {
        System.out.println("getDesc");
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        String expResult = "Restaurant in MC basement - good fries";
        String result = instance.getDesc();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDesc method, of class POI.
     */
    @Test
    public void testSetDesc() {
        System.out.println("setDesc");
        String newPOIDescription = "Restaurant in MC basement - bad fries";
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        instance.setDesc(newPOIDescription);
        assertEquals("Restaurant in MC basement - bad fries", instance.getDesc());
    }

    /**
     * Test of getLocation method, of class POI.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        Vec2 testVec = new Vec2 (1002, 325);
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, testVec);
        Vec2 expResult = testVec;
        Vec2 result = instance.getLocation();
        System.out.println("Vector pointer");
        assertEquals(expResult, result);
        System.out.println("Vector content");
        assertEquals(1002, instance.getLocation().getx());
        assertEquals(325, instance.getLocation().gety());
    }

    /**
     * Test of setLocation method, of class POI.
     */
    @Test
    public void testSetLocation() {
        System.out.println("setLocation");
        Vec2 newPOILocation = new Vec2 (500, 600);
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        instance.setLocation(newPOILocation);
        assertEquals(500, instance.getLocation().getx());
        assertEquals(600, instance.getLocation().gety());
    }

    /**
     * Test of getFav method, of class POI.
     */
    @Test
    public void testGetFav() {
        System.out.println("getFav");
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        Boolean expResult = false;
        Boolean result = instance.getFav();
        assertEquals(expResult, result);
    }

    /**
     * Test of isUserCreated method, of class POI.
     */
    @Test
    public void testIsUserCreated() {
        System.out.println("isUserCreated");
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        Boolean expResult = false;
        Boolean result = instance.isUserCreated();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPOIFav method, of class POI.
     */
    @Test
    public void testSetPOIFav() {
        System.out.println("setPOIFav");
        Boolean newPOIFav = true;
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        instance.setPOIFav(newPOIFav);
        assertEquals(true, instance.getFav());
    }

     /**
     * Test of isEqual method, of class POI- True
     */
    @Test
    public void testIsEqualTrue() {
        System.out.println("isEqual");
        POI POI2 = new POI("Middlesex College", 2, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        POI instance = new POI("Middlesex College", 2, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        boolean expResult = true;
        boolean result = instance.isEqual(POI2);
        assertEquals(expResult, result);
    }
    
     /**
     * Test of isEqual method, of class POI- False
     */
    @Test
    public void testIsEqualFalse() {
        System.out.println("isEqual");
        POI POI2 = new POI("Alumni Hall", 2, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        POI instance = new POI("Middlesex College", 1, "Grad Club", 14, "Restaurant in MC basement - good fries",  false, new Vec2(1002, 325));
        boolean expResult = false;
        boolean result = instance.isEqual(POI2);
        assertEquals(expResult, result);
    }    
}
