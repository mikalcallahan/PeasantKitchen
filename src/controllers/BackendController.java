package controllers;

import java.io.File;
import java.util.ArrayList;

import com.sun.appserv.server.LifecycleEvent;

import framework.ApplicationData;
import framework.DatabaseController;
import framework.Recipe;
import framework.Recipes;
import framework.User;
import utilities.StringUtilites;


public class BackendController 
{
	protected DatabaseController databaseController;
	protected RecomendationController recomendationController;
	
	public BackendController()
	{
		this.initalize(new ObjectDatabaseController());
	}
	
	public BackendController(DatabaseController databaseController)
	{
		this.initalize(databaseController);
	}
	
	private void initalize(DatabaseController databaseController)
	{
		this.databaseController = databaseController;
		this.recomendationController = new RecomendationController();
		
		//Set the recommendation controller as an observer of the database controller
		this.databaseController.addObserver(this.recomendationController);
	}
	
	public LifecycleEvent serverStartupTasks(LifecycleEvent startupEvent)
	{
		return this.databaseController.serverStartupTasks(startupEvent);
	}
	
	public LifecycleEvent serverShutdownTasks(LifecycleEvent shutdownEvent)
	{
		return this.databaseController.serverShutdownTasks(shutdownEvent);
	}
	
	
	/*
	 * USER METHODS
	 */
	
	
	public boolean isUsernameTaken(String username) throws Exception
	{
		User user = this.databaseController.getUser(username);
		
		return
				user != null;
	}
	
	
	public User getUser(String username) throws Exception
	{
		return this.databaseController.getUser(username);
	}
	
	public User signUserIn(String username) throws Exception
	{
		User user = this.getUser(username);
		
		//does the user exist?
		if(user == null)
			throw new NullPointerException("The user [" + username + "] does not exist!");
		
		//is the user already signed in?
		if(user.isSignedIn())
			return user; //if they are, ignore this signin request.
		
		this.databaseController.signInUser(username);
		
		return user;
	}
	
	public User signUserOut(String username) throws Exception
	{
		User user = this.getUser(username);
		
		//does the user exist?
		if(user == null)
			throw new NullPointerException("The user [" + username + "] does not exist!");
		
		//is the user already signed out?
		if(!user.isSignedIn())
			return user; //If they're already signed out, ignore this request
		
		user = this.databaseController.signOutUser(username);
		
		return user;
	}

	//The temp user object is created by the route calling this method, and populated with the information
	//that the route was given
	public User createUser(User tempUserObject) throws Exception
	{
		if(this.isUsernameTaken(tempUserObject.username))
			throw new RuntimeException("The requested username [" + tempUserObject.username + "] is already taken. Please select another");
		
		return this.databaseController.createUser(tempUserObject);
	}
	
	public User removeUser(User user)
	{
		return null;
	}
	
	public User userLikesRecipe(User user, String recipeName)
	{
		return null;
	}
	
	public User userLikesFoodCatagory(User user, String catagoryName)
	{
		return null;
	}
	
	
	
	
	/*
	 * RECOMENDER
	 */
	
	public Recipes recommendRecipes(User user)
	{
		return null;
	}
	 
	
	
	/*
	 * SEARCHING METHODS
	 */
	
	public Recipes searchForReciepes()
	{
		return null;
	}
	
	public Recipes getRecipesContainingIngredients(ArrayList<String> ingredients, String username)
	{
		//The username is given to this method in case the recomender system is going to be involved in this method. I dunno if
		//this is going to be the case, now that I think about it.
		
		ArrayList<String> cleanedIngredients = StringUtilites.cleanIngredients(ingredients);
		return this.databaseController.getRecipesContainingIngredients(cleanedIngredients);
	}
	
	public Recipes getRecipesWithOnlyTheseIngredients(ArrayList<String> ingredients, String username) throws Exception
	{
		ArrayList<String> cleanedIngredients = StringUtilites.cleanIngredients(ingredients);
		return this.databaseController.getRecipesWithOnlyTheseIngredients(cleanedIngredients);
	}
	

	//Returns a list of the most popular recipes stored in the database. If there are not
	//'enough' popular recipes, recipes will be added at random until 'enough' recipes 
	//have been chosen
	public Recipes getDefaultHomepageRecipes()
	{
		return null;
	}
	
	
	/**
	 * Only used for testing.
	 * @return
	 */
	
	public static BackendController makeTestingBackendController(File parentDir) throws Exception
	{
		ApplicationData appData = new ApplicationData(parentDir);
		appData.loadFromDisk();
		
		ObjectDatabaseController testingDatabase = new ObjectDatabaseController(appData);
		BackendController testingBackendController = new BackendController(testingDatabase);
		
		for(Recipe recipe : appData.getRecipes())
			System.out.println(recipe.toString());
		
		return testingBackendController;
	}
}
