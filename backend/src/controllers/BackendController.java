package controllers;

import framework.Recipes;
import framework.User;


public class BackendController 
{
	protected DatabaseController databaseController;
	protected RecomendationController recomendationController;
	
	public BackendController()
	{
		this.initalize();
	}
	
	private void initalize()
	{
		this.databaseController = new DatabaseController();
		this.recomendationController = new RecomendationController();
		
		//Set the recommendation controller as an observer of the database controller
		this.databaseController.addObserver(this.recomendationController);
	}
	
	
	/*
	 * USER METHODS
	 */
	
	
	public boolean isUsernameTaken(String username)
	{
		User user = this.databaseController.getUser(username);
		
		return
				user != null;
	}
	
	
	public User getUser(String username)
	{
		User user = this.databaseController.getUser(username);
		return user;
	}
	
	public User signUserIn(String username)
	{
		User user = this.getUser(username);
		
		//does the user exist?
		if(user == null)
			throw new NullPointerException("The user [" + username + "] does not exist!");
		
		//is the user already signed in?
		if(user.isSignedIn())
			return user; //if they are, ignore this signin request.
		
		//For now, we'll record which users are logged in by setting a flag in
		//the database entry for that user.
		//I has my concerns about that approach, but we'll see how it goes for now
		this.databaseController.signInUser(username);
		
		return user;
	}

	//The temp user object is created by the route calling this method, and populated with the information
	//that the route was given
	public User createUser(User tempUserObject)
	{
		if(this.isUsernameTaken(tempUserObject.username))
			//We should agree with the frontend on some sort of error handling procedure
			//this is a stop gap solution [maybe error codes, or maybe conditional handling of certain exception messages]
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
	
	
	//Returns a list of the most popular recipes stored in the database. If there are not
	//'enough' popular recipes, recipes will be added at random until 'enough' recipes 
	//have been chosen
	public Recipes getDefaultHomepageRecipes()
	{
		return null;
	}

	
	
	
	/*
	 * I dunno if filtering is a backend or a frontend feature [My SRS spec doesn't have enough info
	 * to answer this question, although I suspect it'll end up being a frontend feature]
	 */
	

}
