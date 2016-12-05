package controllerImplementations.tests;

import controllerImplementations.BackendControllerImpl;
import framework.controllers.BackendController;
import framework.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * The type Controller tests.
 *
 *
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ AllTests.class, BackendControllerTest.class })
public class ControllerTests {

    private final File parentDir = new File("/Users/SamDoroudi/Documents/TestingOutput/");

    /**
     * Test creating user.
     */
    @Test
    public void testCreatingUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";
            newUser.password = "password";

            User createdUser = testingController.createUser(newUser);

            assertTrue("The initial User object: [\n" + newUser.toString() + "\n] is different than the created User object: [\n" + createdUser.toString() + "]\n", newUser.equals(createdUser));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Test same user creating user.
     *
     * @throws Exception the exception
     */
//test for creatingUser by creating the same user twice
    @Test (expected = NullPointerException.class) 
    public void testSameUserCreatingUser() throws Exception
    {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";
            newUser.username = "MAH USER";

            User createdUser = testingController.createUser(newUser);
            createdUser = testingController.createUser(newUser);

    }

    /**
     * Break test space values creating user.
     *
     * @throws Exception the exception
     */
//test for creatingUser with space in username/pass
    @Test (expected = NullPointerException.class)
    public void breakTestSpaceValuesCreatingUser() throws Exception
    {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = " ";
            newUser.password = " ";

            User createdUser = testingController.createUser(newUser);

    }

    /**
     * Break test null values creating user.
     */
//test for creatingUser with nulls in user/pass
    @Test (expected = NullPointerException.class)
    public void breakTestNullValuesCreatingUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = null;
            newUser.password = null;

            User createdUser = testingController.createUser(newUser);

            assertTrue("Exception was expected but we recieved a user object instead.", createdUser == null);
            //assertNull("Username is null.", newUser.username);
            //assertNull("Password is null.", newUser.password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Break test no values creating user.
     */
//test for creatingUser with empty string in user/pass
    @Test (expected = NullPointerException.class)
    public void breakTestNoValuesCreatingUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = "";
            newUser.password = "";

            User createdUser = testingController.createUser(newUser);

            assertTrue("Exception was expected but we recieved a user object instead.", createdUser == null);

            //assertTrue("Empty username", newUser.username.isEmpty());
            //assertTrue("Empty passwrord", newUser.password.isEmpty());
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    /**
     * Test signing in user.
     */
    @Test
    public void testSigningInUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";
            newUser.password = "password";

            User createdUser = testingController.createUser(newUser);
            User signedInUser = testingController.signUserIn(newUser.username);

            //assertTrue("The initial User object: [\n" + newUser.toString() + "\n] is different than the signedInUser User object: [\n" + createdUser.toString() + "]\n", newUser.equals(signedInUser));
            assertTrue("Sign in failure", signedInUser.isSignedIn()); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Test signing out user.
     */
    @Test
    public void testSigningOutUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            User newPass = new User();
            newUser.username = "MAH USER";
            newPass.password = "password";
            
            User signedOutUser = testingController.signUserOut(newUser.username);
            
           //make sure they have the same username and password
           //assertEquals(, newUser);
            assertTrue("Sign out failure", !signedOutUser.isSignedIn());
            assertTrue("Error: Different username", newUser.username.equals(signedOutUser.username));
            assertTrue("Error: Different password", newPass.password.equals(signedOutUser.username));
           
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Test is username taken.
     */
    @Test
    public void testIsUsernameTaken()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);
            
            User newUser = new User();
            newUser.username = "MAH USER";  //first user
            newUser.username = "MAH USER";  //second user

            User user1 = testingController.createUser(newUser);
            boolean takenUsername = testingController.isUsernameTaken(newUser.username);
            
            assertTrue("The system failed to reserve the username [" + newUser.username + "]", takenUsername);
            //assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Test removing user.
     */
    @Test
    public void testRemovingUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);
            
            User newUser = new User();
            newUser.username = null;

            User removedUser = testingController.removeUser(newUser);

            assertTrue("Removing user failure", removedUser == null);
        }
         catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Test recommend recipes.
     */
    public void testRecommendRecipes()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Test get recipes containing ingredients.
     */
    @Test
    public void testGetRecipesContainingIngredients()
    {
        //contains if you pass egg but they may contain other things
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";

            ArrayList<String> ingredients = new ArrayList<String>();
            ingredients.add("butter");
            ingredients.add("olive oil");
            ingredients.add("ground beef");



            
            //assertTrue(true);
                       
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Test get recipes with only these ingredients.
     */
    @Test
    public void TestGetRecipesWithOnlyTheseIngredients()
    {
    	//if you pass eggs, it's gonna return only eggs.
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";
            
            ArrayList<String> recipe = new ArrayList<String>();
            recipe.add("Boil Water, cook ground beef until brown 2. Drain grease from ground beef "
                    + "3. Add noodles to water…cook until soft, add pasta sauce to ground beef and let simmer.");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}