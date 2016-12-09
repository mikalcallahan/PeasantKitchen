package controllerImplementations.tests;

import controllerImplementations.BackendControllerImpl;
import framework.Recipe;
import framework.Recipes;
import framework.User;
import framework.controllers.BackendController;
import org.junit.Test;
import utilities.CollectionUtils;
import utilities.StringUtilites;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static controllerImplementations.BackendControllerImpl.makeTestingBackendControllerImpl;
import static org.junit.Assert.assertTrue;


/**
 * The type Backend controller test.
 */
public class BackendControllerImplTest
{
    private final File parentDir = new File("/home/stoffel/Documents/School/Software Engineering/TestingOutput/");

    /**
     * Test creating user.
     */
    @Test
    public void testCreatingUser()
    {
        try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendControllerImpl(parentDir);

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
     * Test signing in user.
     */
    @Test
    public void testSigningInUser()
    {
    	try
        {
            BackendController testingController = BackendControllerImpl.makeTestingBackendControllerImpl(parentDir);

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
     *test for creatingUser by creating the same user twice
     *Tests same user creating user.
     *Testing to make sure the backend doesn't create the same user.
     * @throws Exception the exception
     */
    @Test (expected = NullPointerException.class)
    public void testSameUserCreatingUser() throws Exception
    {
        BackendController testingController = makeTestingBackendControllerImpl(parentDir);

        //Creating the same user twice. Passing it to controller
        User newUser = new User();
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
        BackendController testingController = makeTestingBackendControllerImpl(parentDir);

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
            BackendController testingController = makeTestingBackendControllerImpl(parentDir);

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
            BackendController testingController = makeTestingBackendControllerImpl(parentDir);

            //Creating new username and password with an empty string and passing it to controller
            //Will expect NULL
            User newUser = new User();
            newUser.username = "";
            newUser.password = "";

            User createdUser = testingController.createUser(newUser);

            assertTrue("Exception was expected but we recieved a user object instead.", createdUser == null);

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
    public void testSigningOutNonExistingUser()
    {
        try
        {
            BackendController testingController = makeTestingBackendControllerImpl(parentDir);
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
            BackendController testingController = makeTestingBackendControllerImpl(parentDir);

            //Creating the same user twice. Passing it to the controller
            //Displays failure message if username is taken
            User newUser = new User();
            newUser.username = "MAH USER";  //first user

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
            BackendController testingController = makeTestingBackendControllerImpl(parentDir);

            User newUser = new User();
            newUser.username = null;

            testingController.createUser(newUser);
            User removedUser = testingController.removeUser(newUser);

            assertTrue("Removing user failure", removedUser != null);
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
            BackendController testingController = makeTestingBackendControllerImpl(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";

            HashSet<String> expectedRecipeNames = new HashSet<String>();
            expectedRecipeNames.add("recipeName1");
            expectedRecipeNames.add("recipeName2");

            ArrayList<String> ingredients = new ArrayList<String>();
            ingredients.add("eggs");
            ingredients.add("butter");

            Recipes results = testingController.getRecipesContainingIngredients(ingredients, newUser.username);
            Set<String> uniqueRecipeNames = CollectionUtils.set(results, (Recipe recipe) -> {return  recipe.recipeName; });

            assertTrue("The system could not find all of the recipes we expected it to locate (expected were: " + StringUtilites.join(expectedRecipeNames) + ")",
                    CollectionUtils.equalSets(uniqueRecipeNames, expectedRecipeNames));
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
        //may contain others; if you pass egg but they may contain other things
        try
        {
            BackendController testingController = makeTestingBackendControllerImpl(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";

            HashSet<String> expectedRecipeNames = new HashSet<String>();
            expectedRecipeNames.add("recipeName1");
            expectedRecipeNames.add("recipeName2");

            ArrayList<String> ingredients = new ArrayList<String>();
            ingredients.add("eggs");

            Recipes results = testingController.getRecipesWithOnlyTheseIngredients(ingredients, newUser.username);
            Set<String> uniqueRecipeNames = CollectionUtils.set(results, (Recipe recipe) -> {return  recipe.recipeName; });

            assertTrue("The system could not find all of the recipes we expected it to locate (expected were: " + StringUtilites.join(expectedRecipeNames) + ")",
                    CollectionUtils.equalSets(uniqueRecipeNames, expectedRecipeNames));
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
        try
        {
            BackendController testingController = makeTestingBackendControllerImpl(parentDir);

            User testUser = new User();
            testUser.username = "my new user";
            testUser.password = "password";

            testingController.createUser(testUser);

            //Attempt to sign in a bunch of times
            testingController.signUserIn(testUser.username);
            testingController.signUserIn(testUser.username);
            testingController.signUserIn(testUser.username);
            User lastSigninAttemptUser = testingController.signUserIn(testUser.username);

            assertTrue("Repeatily signing in the same user resulted in the user being signed out!", lastSigninAttemptUser.isSignedIn());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
