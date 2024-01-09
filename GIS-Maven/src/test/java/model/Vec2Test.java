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
public class Vec2Test {
    
    public Vec2Test() {
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
     * Test of getx method, of class Vec2.
     */
    @Test
    public void testGetx() {
        System.out.println("getx");
        Vec2 instance = new Vec2(0.0,3.0);
        double expResult = 0.0;
        double result = instance.getx();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of gety method, of class Vec2.
     */
    @Test
    public void testGety() {
        System.out.println("gety");
        Vec2 instance = new Vec2(3.0,0.0);
        double expResult = 0.0;
        double result = instance.gety();
        assertEquals(expResult, result, 0);
       
    }

    /**
     * Test of setx method, of class Vec2.
     */
    @Test
    public void testSetx() {
        System.out.println("setx");
        double xcoord = 2.0;
        Vec2 instance = new Vec2(0.0,3.0);
        instance.setx(xcoord);
        assertEquals(xcoord, instance.getx(), 0);
    }

    /**
     * Test of sety method, of class Vec2.
     */
    @Test
    public void testSety() {
        System.out.println("sety");
        double ycoord = 3.0;
        Vec2 instance = new Vec2(3.0,0.0);
        instance.sety(ycoord);
        assertEquals(ycoord, instance.gety(), 0);
    }

    /**
     * Test of isEqual method, of class Vec2.
     */
    @Test
    public void testIsEqualTrue() {
        System.out.println("isEqual");
        Vec2 otherLocation  = new Vec2(3.0,0.0);
        Vec2 instance = new Vec2(3.0,0.0);
        Boolean expResult = true;
        Boolean result = instance.isEqual(otherLocation);
        assertEquals(expResult, result);
        
    }
    
     /**
     * Test of isEqual method, of class Vec2.
     */
    @Test
    public void testIsEqualFalse() {
        System.out.println("isEqual");
        Vec2 otherLocation  = new Vec2(3.0,2.0);
        Vec2 instance = new Vec2(3.0,0.0);
        Boolean expResult = false;
        Boolean result = instance.isEqual(otherLocation);
        assertEquals(expResult, result);
        
    }
    
}
