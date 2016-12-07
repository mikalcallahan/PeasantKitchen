package controllerImplementations.tests;

import controllerImplementations.BackendControllerImpl;
import framework.User;
import framework.controllers.BackendController;
import org.junit.Test;

import java.io.File;

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

}