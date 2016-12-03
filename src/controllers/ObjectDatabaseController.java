package controllers;

import applicationData.ApplicationData;
import com.sun.appserv.server.LifecycleEvent;
import constants.Constants;
import designPatterns.Observer;
import framework.DatabaseController;
import framework.Recipe;
import framework.Recipes;
import framework.User;
import utilities.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/*
 * PERSONAL LOG
 * 
 * That requirements string must be changed into something which is parsable
 */


/**
 * The type Object database controller.
 */
public class ObjectDatabaseController extends DatabaseController
{
    /**
     * The Application data.
     */
    protected ApplicationData applicationData = new ApplicationData(new File("/home/stoffel/Documents/School/Software Engineering/TestingOutput/"));

    /**
     * Instantiates a new Object database controller.
     */
    public ObjectDatabaseController()
    {

    }

    /**
     * Instantiates a new Object database controller.
     *
     * @param applicationData the application data
     */
    public ObjectDatabaseController(ApplicationData applicationData)
    {
        super();
        this.applicationData = applicationData;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args)
    {

    }


    @Override
    public Recipes getRecipesContainingIngredients(ArrayList<String> cleanedIngredients)
    {
        return
                this.applicationData.filterRecipes(
                (Recipe recipe) -> {
                    Set<String> uniqueRecipeIngredients = recipe.getUniqueIngredients();

                    for (String ingredient : cleanedIngredients)
                        if (!uniqueRecipeIngredients.contains(ingredient))
                            return false;

                    return true;
                }
                )
    }

    @Override
    public Recipes getRecipesWithOnlyTheseIngredients(ArrayList<String> cleanedIngredients)
    {
        HashSet<String> uniqueCleanIngredients = CollectionUtils.hashSet(cleanedIngredients);

        return
                this.applicationData.filterRecipes(
                        (Recipe recipe) ->
                        {
                            Set<String> uniqueRecipeIngredients = recipe.getUniqueIngredients();
                            return CollectionUtils.equalSets(uniqueCleanIngredients, uniqueRecipeIngredients);
                        }
                );
    }

    @Override
    public User getUser(String username) throws Exception
    {
        return applicationData.getUsers().get(username);
    }

    @Override
    public User createUser(User tempUserObject)
    {
        User newUser = new User(tempUserObject);

        //Successfully handles the case where multiple threads are trying to add the same user (hence, same key)
        //at the same time.
        //I'll let the Java API decide which thread wins
        User storedUser = applicationData.getUsers().putIfAbsent(tempUserObject.username, newUser);

        if (storedUser != null)
            throw new RuntimeException("The requested username [" + tempUserObject.username + "] is already taken. Please select another");

        return newUser;
    }

    @Override
    public User signInUser(String username) throws Exception
    {
        User user = this.getUser(username);
        user.signedIn = true;
        return user;
    }

    @Override
    public User signOutUser(String username) throws Exception
    {
        User user = this.getUser(username);
        user.signedIn = false;
        return user;
    }

    @Override
    public void addObserver(Observer observer)
    {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer)
    {
        this.observers.remove(observer);
    }

    @Override
    public LifecycleEvent serverStartupTasks(LifecycleEvent startupEvent)
    {
        this.applicationData = new ApplicationData(Constants.applicationDataFolder);

        try
        {
            this.applicationData.loadFromDisk();
        }
        catch (Exception e)
        {
            System.err.println("Failed to load the application data from disk");
            e.printStackTrace(System.err);
        }

        return startupEvent;
    }


    //Testing main

    @Override
    public LifecycleEvent serverShutdownTasks(LifecycleEvent shutdownEvent)
    {
        try
        {
            this.applicationData.saveToDisk();
        }
        catch (Exception e)
        {
            System.err.println("Failed to save the application data to disk");
            e.printStackTrace();
        }

        return shutdownEvent;
    }

	@Override
	public User removeUser(String username) throws Exception 
	{
		return this.applicationData.getUsers().remove(username);
	}


}
