package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import designPatterns.Observer;
import framework.ApplicationData;
import framework.DatabaseController;
import framework.Recipe;
import framework.Recipes;
import framework.User;
import framework.WebSocketGlobalEnvironment;


/*
 * PERSONAL LOG
 * 
 * That requirements string must be changed into something which is parsable
 */


public class ObjectDatabaseController extends DatabaseController 
{	
	@Override
	public Recipes getRecipesContainingIngredients(ArrayList<String> cleanedIngredients) 
	{
		ApplicationData applicationData = WebSocketGlobalEnvironment.instance().getApplicationData();
		List<Recipe> recipes = applicationData.getRecipes();
		Recipes results = new Recipes();
		
		synchronized (recipes) 
		{
			for(Recipe recipe : recipes)
			{
				if(recipeContainsIngredients(recipe, cleanedIngredients))
					results.add(recipe);
			}
		}
		
		return results;
	}
	
	//This method may not work so well in practice
	//Couple issues: 1) This assumes that the user is typing in ingredients in the same way that we have them
	//stored in our database
	
	private boolean recipeContainsIngredients(Recipe recipe, Collection<String> ingredients)
	{
		for(String ingredient : ingredients)
			if(!recipe.recipeRequirements.toLowerCase().contains(ingredient.toLowerCase().trim()))
				return false;
		
		return true;
	}

	@Override
	public Recipes getRecipesWithOnlyTheseIngredients(ArrayList<String> cleanedIngredients) 
	{
		ApplicationData applicationData = WebSocketGlobalEnvironment.instance().getApplicationData();
		List<Recipe> recipes = applicationData.getRecipes();
		Recipes results = new Recipes();
		
		synchronized (recipes) 
		{
			for(Recipe recipe : recipes)
			{
				if(recipeContainsExactIngredients(recipe, cleanedIngredients))
					results.add(recipe);
			}
		}
		
		return results;
	}
	
	private boolean recipeContainsExactIngredients(Recipe recipe, Collection<String> ingredients)
	{
		return this.recipeContainsIngredients(recipe, ingredients); //CHANGE ME
	}
 
	@Override
	public User getUser(String username) throws Exception 
	{
		ApplicationData applicationData = WebSocketGlobalEnvironment.instance().getApplicationData();
		return applicationData.getUsers().get(username);
	}

	@Override
	public User createUser(User tempUserObject) 
	{
		ApplicationData applicationData = WebSocketGlobalEnvironment.instance().getApplicationData();
		
		User newUser = new User(tempUserObject);
		
		//Successfully handles the case where multiple threads are trying to add the same user (hence, same key)
		//at the same time. 
		//I'll let the Java API decide which thread wins
		User storedUser = applicationData.getUsers().putIfAbsent(tempUserObject.username, newUser);
		
		if(storedUser != null)
			throw new RuntimeException("The requested username [" + tempUserObject.username + "] is already taken. Please select another");
		
		return storedUser;
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
	
	
	//Testing main

	public static void main(String[] args) 
	{

	}

}
