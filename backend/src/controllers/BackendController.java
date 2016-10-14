package controllers;

import framework.Recipes;
import framework.SearchQuery;
import framework.User;

/*
 * For now, this guy is a singleton [I shall write the singelton code laterish though]
 */

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
	
	public User getUser(String username)
	{
		User user = this.databaseController.getUser(username);
		return user;
	}
	
	public User signUserIn(String username)
	{
		//Does this user exist?
		
		User user = this.getUser(username); //if the user doesn't exist, this call will except
		return user;
	}

	//The temp user object is created by the route calling this method, and populated with the information
	//that the route was given
	public User createUser(User tempUserObject)
	{
		return null;
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
	
	public Recipes searchForReciepes(SearchQuery query)
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
