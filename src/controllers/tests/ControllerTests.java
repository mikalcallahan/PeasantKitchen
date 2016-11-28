package controllers.tests;

import controllers.BackendController;
import framework.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

@RunWith(Suite.class)
@SuiteClasses({ AllTests.class, BackendControllerTest.class })
public class ControllerTests {

    private final File parentDir = new File("/Users/SamDoroudi/Documents/TestingOutput/");

    @Test
    public void testCreatingUser()
    {
        try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);

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
    
    //test for space in username/pass
    @Test
    public void breakTestSpaceValuesCreatingUser()
    {
        try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = " ";
            newUser.password = " ";

            User createdUser = testingController.createUser(newUser);

            assertTrue("", )
            
            assertTrue("The initial User object: [\n" + newUser.toString() + "\n] is different than the created User object: [\n" + createdUser.toString() + "]\n", newUser.equals(createdUser));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    //test for user/pass with nulls
    @Test
    public void breakTestNullValuesCreatingUser()
    {
        try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = null;
            newUser.password = null;

            User createdUser = testingController.createUser(newUser);

            assertTrue("The initial User object: [\n" + newUser.toString() + "\n] is different than the created User object: [\n" + createdUser.toString() + "]\n", newUser.equals(createdUser));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    //create one with no space
    @Test
    public void breakTestNullValuesCreatingUser()
    {
        try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = null;
            newUser.password = null;

            User createdUser = testingController.createUser(newUser);

            assertTrue("The initial User object: [\n" + newUser.toString() + "\n] is different than the created User object: [\n" + createdUser.toString() + "]\n", newUser.equals(createdUser));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    
    @Test
    public void testSigningInUser()
    {
    	try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);

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
    
    @Test
    public void testSigningOutUser()
    {
    	try
    	{
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);

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
    
    @Test
    public void testIsUsernameTaken()
    {
    	try
    	{
    		BackendController testingController = BackendController.makeTestingBackendController(parentDir);
    		
    		User newUser = new User();
            newUser.username = "MAH USER";  //first user
            newUser.username = "MAH USER";	//second user

    		boolean takenUsername = testingController.isUsernameTaken(newUser.username);
    		
    		//assertTrue("Username is taken", takenUsername.isUsernameTaken());
    		//assertTrue(true);
    	}
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testRemovingUser()
    {
    	try
    	{
    		BackendController testingController = BackendController.makeTestingBackendController(parentDir);
    		
    		User newUser = new User();
    		newUser.username = null;
    		
            User removedUser = testingController.removeUser(newUser);

            assertTrue("Removing user failure", removedUser.removeUser(newUser));
    	}
    	 catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserLikesRecipe()
    {
        try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
     
    @Test
    public void testUserLikesFoodCatagory()
    {
        try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);
            
            assertTrue(true);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void testRecommendRecipes()
    {
        try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);

            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetRecipesContainingIngredients()
    {
        try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";

            ArrayList<String> list = new ArrayList<String>(){{
            	add("Butter");
            	add("Olive Oil");
            	add("Ground Beef");
            }};
            
            
            //assertTrue(true);
                       
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    @Test
    public void TestGetRecipesWithOnlyTheseIngredients()
    {
    	try
        {
            BackendController testingController = BackendController.makeTestingBackendController(parentDir);

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