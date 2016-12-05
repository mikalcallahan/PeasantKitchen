package framework;

import applicationData.ApplicationData;
import com.sun.appserv.server.LifecycleEvent;
import controllersImplementations.BackendControllerImpl;
import controllersImplementations.ObjectDatabaseController;

import java.io.File;

public interface BackendController
{
    LifecycleEvent serverStartupTasks(LifecycleEvent startupEvent);

    LifecycleEvent serverShutdownTasks(LifecycleEvent shutdownEvent);

    boolean isUsernameTaken(String username) throws Exception;

    User getUser(String username) throws Exception;

    User signUserIn(String username) throws Exception;

    User signUserOut(String username) throws Exception;

    //The temp user object is created by the route calling this method, and populated with the information
    //that the route was given
    User createUser(User tempUserObject) throws Exception;

    User removeUser(User user) throws Exception;

    User userLikesRecipe(User user, String recipeName);

    User userLikesFoodCatagory(User user, String catagoryName);

    Recipes recommendRecipes(User user);
}
