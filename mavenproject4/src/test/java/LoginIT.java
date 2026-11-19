/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginIT {
    
    public LoginIT() {
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
     * Test of checkUserName method, of class Login.
     */
   @Test
    public void testCheckUserName() {
        Login user = new Login();
        user.username = "ab_1";

        assertTrue(user.checkUserName());
    }
     @Test
    public void testInvalidUserName() {
        Login user = new Login();
        user.username = "abcdef";

        assertFalse(user.checkUserName());
    }
    @Test
    public void testPasswordComplexity() {
        Login user = new Login();
        user.password = "Password1!";

        assertTrue(user.checkPasswordComplexity());
    }

    
    /**
     * Test of checkPasswordComplexity method, of class Login.
     */
    @Test
    public void testCheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
            
    }
     @Test
    public void testInvalidPassword() {
        Login user = new Login();
        user.password = "password";

        assertFalse(user.checkPasswordComplexity());
    }
    @Test
    public void testCellPhoneNumber() {
        Login user = new Login();
        user.cellPhoneNumber = "+27821234567";

        assertTrue(user.checkCellPhoneNumber());
    }
    @Test
    public void testInvalidCellPhoneNumber() {
        Login user = new Login();
        user.cellPhoneNumber = "0821234567";

        assertFalse(user.checkCellPhoneNumber());
    }
    @Test
    public void testIncorrectLogin() {
        Login user = new Login();
        user.username = "ab_1";
        user.password = "Password1!";

        assertFalse(user.loginUser("wrong", "wrong"));
    }
    

    
    /**
     * Test of checkCellPhoneNumber method, of class Login.
     */
    @Test
    public void testCheckCellPhoneNumber() {
        System.out.println("checkCellPhoneNumber");
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
      
    }
    
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String enteredUsername = "";
        String enteredPassword = "";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.loginUser(enteredUsername, enteredPassword);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }
    
    
    
}
