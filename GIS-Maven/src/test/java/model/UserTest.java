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
public class UserTest {
    
    public UserTest() {
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
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        User instance =  new User("someone@uwo.ca", "", "What is this", "a dog", 0);
        String expResult = "someone@uwo.ca";
        String result = instance.getEmail();
        
    }

    /**
     * Test of setEmail method, of class User.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "newemail@uwo.ca";
        User instance = new User("someone@uwo.ca", "", "What is this", "a dog", 0);
        instance.setEmail(email);
        assertEquals("newemail@uwo.ca", instance.getEmail());

    }

    /**
     * Test of getPassword method, of class User.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        String expResult = "password";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setPassword method, of class User.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "new password";
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        instance.setPassword(password);
        assertEquals("new password", instance.getPassword());
    }

    /**
     * Test of getSecQuestion method, of class User.
     */
    @Test
    public void testGetSecQuestion() {
        System.out.println("getSecQuestion");
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        String expResult = "What is this";
        String result = instance.getSecQuestion();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setSecQuestion method, of class User.
     */
    @Test
    public void testSetSecQuestion() {
        System.out.println("setSecQuestion");
        String secQuestion = "Who are you";
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        instance.setSecQuestion(secQuestion);
        assertEquals("Who are you", instance.getSecQuestion());
    }

    /**
     * Test of getSecAnswer method, of class User.
     */
    @Test
    public void testGetSecAnswer() {
        System.out.println("getSecAnswer");
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        String expResult = "a chair";
        String result = instance.getSecAnswer();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setSecAnswer method, of class User.
     */
    @Test
    public void testSetSecAnswer() {
        System.out.println("setSecAnswer");
        String secAnswer = "book";
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        instance.setSecAnswer(secAnswer);
        assertEquals("book", instance.getSecAnswer());
    }

    /**
     * Test of getUserType method, of class User.
     */
    @Test
    public void testGetUserType() {
        System.out.println("getUserType");
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        int expResult = 1;
        int result = instance.getUserType();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getPOIs method, of class User.
     */
    @Test
    public void testGetPOIs() {
        System.out.println("getPOIs");
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        ArrayList<POI> expResult = new ArrayList<>();
        ArrayList<POI> result = instance.getPOIs();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of addToList method, of class User.
     */
    @Test
    public void testAddToList() {
        System.out.println("addToList");
        POI poi = new POI("MC", 1, "newPOI", 9, "description", false, new Vec2(2,3));
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        boolean result = instance.addToList(poi);
        boolean expResult = true;
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setUserType method, of class User.
     */
    @Test
    public void testSetUserType() {
        System.out.println("setUserType");
        int userType = 0;
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        instance.setUserType(userType);
        
        /** check instance is a developer */
	assertFalse(instance.isDeveloper());
    }

   

    /**
     * Test of isEqual method, of class User.
     */
    @Test
    public void testIsEqual() {
        System.out.println("isEqual");
        User otherUser = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        Boolean expResult = true;
        Boolean result = instance.isEqual(otherUser);
        assertEquals(expResult, result);
       
    }

   

    /**
     * Test of isDeveloper method, of class User.
     */
    @Test
    public void testIsDeveloper() {
        System.out.println("isDeveloper");
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        Boolean expResult = true;
        Boolean result = instance.isDeveloper();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of removePOI method, of class User.
     */
    @Test
    public void testRemovePOI() {
        System.out.println("removePOI");
        POI poiToRemove = null;
        User instance = new User("someone@uwo.ca", "password", "What is this", "a chair", 1);
        assertThrows(EmpytListException.class, ()->{instance.removePOI(poiToRemove);;});
    }
    
}
