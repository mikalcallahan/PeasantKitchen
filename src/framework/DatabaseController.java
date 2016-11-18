package framework;

import java.util.ArrayList;

import designPatterns.ObserverSubject;

public abstract class DatabaseController extends ObserverSubject  
{
	public abstract Recipes getRecipesContainingIngredients(ArrayList<String> cleanedIngredients);
	public abstract Recipes getRecipesWithOnlyTheseIngredients(ArrayList<String> cleanedIngredients);
	public abstract User getUser(String username) throws Exception;
	public abstract User createUser(User tempUserObject);
	public abstract User signInUser(String username) throws Exception; 
	public abstract User signOutUser(String username) throws Exception; 
}
