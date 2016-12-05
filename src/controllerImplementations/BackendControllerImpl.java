package controllerImplementations;

import applicationData.ApplicationData;
import com.sun.appserv.server.LifecycleEvent;
import framework.Recipes;
import framework.User;
import framework.controllers.BackendController;
import framework.controllers.DatabaseController;
import framework.controllers.RecommendationController;
import utilities.StringUtilites;

import java.io.File;
import java.util.ArrayList;


/**
 * The type Backend controller.
 */
public class BackendControllerImpl implements BackendController
{
    protected DatabaseController databaseController;
    protected RecommendationController recommendationController;

    /**
     * Instantiates a new Backend controller.
     */
    public BackendControllerImpl()
    {
        this.initalize(new ObjectDatabaseController());
    }

    /**
     * Instantiates a new Backend controller.
     *
     * @param databaseController the database controller
     */
    public BackendControllerImpl(DatabaseController databaseController)
    {
        this.initalize(databaseController);
        this.databaseController.serverStartupTasks(null);
    }

    /**
     * Only used for testing.
     *
     * @param parentDir the parent dir
     * @return backend controller
     * @throws Exception the exception
     */
    public static BackendController makeTestingBackendController(File parentDir) throws Exception
    {
        ApplicationData appData = new ApplicationData(parentDir);
        appData.loadFromDisk();

        ObjectDatabaseController testingDatabase = new ObjectDatabaseController(appData);
        return new BackendControllerImpl(testingDatabase);
    }

    private void initalize(DatabaseController databaseController)
    {
        this.databaseController = databaseController;
        this.recommendationController = new RecommendationControllerImpl();

        //Set the recommendation controller as an observer of the database controller
        this.databaseController.addObserver(this.recommendationController);
    }

    /**
     * Server startup tasks lifecycle event.
     *
     * @param startupEvent the startup event
     * @return the lifecycle event
     */
    @Override
    public LifecycleEvent serverStartupTasks(LifecycleEvent startupEvent)
    {
        return this.databaseController.serverStartupTasks(startupEvent);
    }

	
	/*
	 * USER METHODS
	 */

    /**
     * Server shutdown tasks lifecycle event.
     *
     * @param shutdownEvent the shutdown event
     * @return the lifecycle event
     */
    @Override
    public LifecycleEvent serverShutdownTasks(LifecycleEvent shutdownEvent)
    {
        return this.databaseController.serverShutdownTasks(shutdownEvent);
    }

    /**
     * Is username taken boolean.
     *
     * @param username the username
     * @return the boolean
     * @throws Exception the exception
     */
    @Override
    public boolean isUsernameTaken(String username) throws Exception
    {
        User user = this.databaseController.getUser(username);

        return
                user != null;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     * @throws Exception the exception
     */
    @Override
    public User getUser(String username) throws Exception
    {
        return this.databaseController.getUser(username);
    }

    /**
     * Sign user in user.
     *
     * @param username the username
     * @return the user
     * @throws Exception the exception
     */
    @Override
    public User signUserIn(String username) throws Exception
    {
        User user = this.getUser(username);

        //does the user exist?
        if (user == null)
            throw new NullPointerException("The user [" + username + "] does not exist!");

        //is the user already signed in?
        if (user.isSignedIn())
            return user; //if they are, ignore this signin request.

        return this.databaseController.signInUser(username);
    }

    /**
     * Sign user out user.
     *
     * @param username the username
     * @return the user
     * @throws Exception the exception
     */
    @Override
    public User signUserOut(String username) throws Exception
    {
        User user = this.getUser(username);

        //does the user exist?
        if (user == null)
            throw new NullPointerException("The user [" + username + "] does not exist!");

        //is the user already signed out?
        if (!user.isSignedIn())
            return user; //If they're already signed out, ignore this request

        return this.databaseController.signOutUser(username);
    }

    /**
     * Create user user.
     *
     * @param tempUserObject the temp user object
     * @return the user
     * @throws Exception the exception
     */
//The temp user object is created by the route calling this method, and populated with the information
    //that the route was given
    @Override
    public User createUser(User tempUserObject) throws Exception
    {
        if (this.isUsernameTaken(tempUserObject.username))
            throw new RuntimeException("The requested username [" + tempUserObject.username + "] is already taken. Please select another");

        return this.databaseController.createUser(tempUserObject);
    }

    /**
     * Remove user user.
     *
     * @param user the user
     * @return the user
     * @throws Exception the exception
     */
    @Override
    public User removeUser(User user) throws Exception
    {
        return this.databaseController.removeUser(user.username);
    }

    /**
     * User likes recipe user.
     *
     * @param user       the user
     * @param recipeName the recipe name
     * @return the user
     */
    @Override
    public User userLikesRecipe(User user, String recipeName)
    {
        return null;
    }
	
	
	
	
	/*
	 * RECOMENDER
	 */

    /**
     * User likes food catagory user.
     *
     * @param user         the user
     * @param catagoryName the catagory name
     * @return the user
     */
    @Override
    public User userLikesFoodCatagory(User user, String catagoryName)
    {
        return null;
    }
	 
	
	
	/*
	 * SEARCHING METHODS
	 */

    /**
     * Recommend recipes recipes.
     *
     * @param user the user
     * @return the recipes
     */
    @Override
    public Recipes recommendRecipes(User user)
    {
        return null;
    }

    /**
     * Search for reciepes recipes.
     *
     * @return the recipes
     */
    public Recipes searchForReciepes()
    {
        return null;
    }

    /**
     * Gets recipes containing ingredients.
     *
     * @param ingredients the ingredients
     * @param username    the username
     * @return the recipes containing ingredients
     */
    public Recipes getRecipesContainingIngredients(ArrayList<String> ingredients, String username)
    {
        //The username is given to this method in case the recomender system is going to be involved in this method. I dunno if
        //this is going to be the case, now that I think about it.

        ArrayList<String> cleanedIngredients = StringUtilites.cleanIngredients(ingredients);
        return this.databaseController.getRecipesContainingIngredients(cleanedIngredients);
    }

    /**
     * Gets recipes with only these ingredients.
     *
     * @param ingredients the ingredients
     * @param username    the username
     * @return the recipes with only these ingredients
     * @throws Exception the exception
     */
    public Recipes getRecipesWithOnlyTheseIngredients(ArrayList<String> ingredients, String username) throws Exception
    {
        ArrayList<String> cleanedIngredients = StringUtilites.cleanIngredients(ingredients);
        return this.databaseController.getRecipesWithOnlyTheseIngredients(cleanedIngredients);
    }

    /**
     * Gets default homepage recipes.
     *
     * @return the default homepage recipes
     */
//Returns a list of the most popular recipes stored in the database. If there are not
    //'enough' popular recipes, recipes will be added at random until 'enough' recipes
    //have been chosen
    public Recipes getDefaultHomepageRecipes()
    {
        return null;
    }

}
