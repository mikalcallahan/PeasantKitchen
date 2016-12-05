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
import java.util.HashSet;


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

    /*
     *Test for creatingUser method.
     *Tests to make sure backend can successfully create a user.
     *given username and password.
     */
    @Test
    public void testCreatingUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            //Creating legitimate username and password and passing it to controller
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
     *Test for creatingUser method.
     *Tests same user creating user.
     *Testing to make sure the backend doesn't create the same user.
     * @throws Exception the exception
     */
//test for creatingUser by creating the same user twice
    @Test (expected = NullPointerException.class) 
    public void testSameUserCreatingUser() throws Exception
    {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            //Creating the same user twice. Passing it to controller
            User newUser = new User();
            newUser.username = "MAH USER";
            newUser.username = "MAH USER";

            User createdUser = testingController.createUser(newUser);
            createdUser = testingController.createUser(newUser);

    }

    /**
     *Test for creatingUser method.
     *Break test space values creating user.
     *Testing to make sure the backend doesn't create empty username/password.
     * @throws Exception the exception
     */
    @Test (expected = NullPointerException.class)
    public void breakTestSpaceValuesCreatingUser() throws Exception
    {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);
            
            //Creating username and password with only space, and passing it to controller
            User newUser = new User();
            newUser.username = " ";
            newUser.password = " ";

            User createdUser = testingController.createUser(newUser);

    }

    /**
     *Test for creatingUser method.
     *Break test null values creating user.
     *Test for creatingUser with null in username/password.
     */
    @Test (expected = NullPointerException.class)
    public void breakTestNullValuesCreatingUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            //creating username and password with NULL and passing it to controller
            //Will expect NULL
            User newUser = new User();
            newUser.username = null;
            newUser.password = null;

            User createdUser = testingController.createUser(newUser);

            assertTrue("Exception was expected but we recieved a user object instead.", createdUser == null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     *Test for creatingUser method.
     *Break test no values creating user.
     *Testing to make sure the backend doesn't create empty username/password.
     */
    @Test (expected = NullPointerException.class)
    public void breakTestNoValuesCreatingUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            //Creating new username and password with an empty string and passing it to controller
            //Will expect NULL
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
     *Test for signingInUser method.
     *Tests signing in user.
     *Testing to make sure the backend signs in user using given parameters.
     */
    @Test
    public void testSigningInUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            //Creating new user with given username/password and passing it to controller
            //This should be able to sign in, if not, failure message will show
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
     * Test for signingOutUser method.
     * Tests signing out user.
     * Testing to make sure the backend signs out user using given parameters.
     */
    @Test
    public void testSigningOutUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);
            //Creating new user with given username/password and passing it to the controller
            User newUser = new User();
            User newPass = new User();
            newUser.username = "MAH USER";
            newPass.password = "password";
            
            User signedOutUser = testingController.signUserOut(newUser.username);
            
           //Make sure they have the same username and password
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
     * Test for isUsernameTaken method.
     * Tests whether is username taken.
     * Testing to make sure the backend checks for username's existence to avoid creating the same one.
     */
    @Test
    public void testIsUsernameTaken()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);
            
            //Creating the same user twice. Passing it to the controller
            //Displays failure message if username is taken
            User newUser = new User();
            newUser.username = "MAH USER";  //first user
            newUser.username = "MAH USER";  //second user

            User user1 = testingController.createUser(newUser);
            boolean takenUsername = testingController.isUsernameTaken(newUser.username);
            
            assertTrue("The system failed to reserve the username [" + newUser.username + "]", takenUsername);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Test for removingUser method.
     * Tests removing user.
     * Testing to make sure the backend could remove a user.
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
     * Test for recommendRecipes method.
     * Test recommend recipes.
     * This method isn't implemented yet.
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
     * Test for getRecipesContainingIngredients method.
     * Tests getting recipes containing ingredients.
     * 
     */
    @Test
    public void testGetRecipesContainingIngredients()
    {
        //may contain others; if you pass egg but they may contain other things
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";

            ArrayList<String> ingredients = new ArrayList<String>();
            ingredients.add("butter");
            ingredients.add("olive oil");
            ingredients.add("ground beef");

           /*
            *HashSet<String> ingredientsRequested;
            *ingredientsRequested = new HashSet<String>();
            *ingredientsRequested.add("Eggs");
            *
            *HashSet<String> returnedRecipes;
            *returnedRecipes = new HashSet<String>();
            *returnedRecipes.add("Eggs");
            */
            
            
            /*
            ArrayList<String> mahList = new ArrayList<String>();
            mahList.add("Butter");
            */
                                   
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Test for getRecipesWithOnlyTheseIngredients method.
     * Tests getting recipes with only these ingredients.
     *
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

    /**
     * Try creating a new user, and then try signing that same user in alot. This is meeant to test
     * that the system can correctly detect when a user is already logged in, and hence ignore subsequent sign
     * in requests.
     */

    @Test
    public void testSigningInAlot()
    {
        BackendController testingController = BackendControllerImpl.makeTestingBackendController(parentDir);

        User testUser = new User();
        testUser.username = "my new user";
        testUser.password = "password";

        testingController.createUser(testUser);

        //Attempt to sign in a bunch of times
        testingController.signUserIn(testUser.username);
        testingController.signUserIn(testUser.username);
        testingController.signUserIn(testUser.username);
        User lastSigninAttemptUser = testingController.signUserIn(testUser.username);

        assertTrue("Repeatily signing in resulted in the user being signed out!", lastSigninAttemptUser.isSignedIn());
    }
    
}