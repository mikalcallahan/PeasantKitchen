package framework;

import java.util.ArrayList;

import com.sun.appserv.server.LifecycleEvent;

import designPatterns.ObserverSubject;

public abstract class DatabaseController extends ObserverSubject
{
	public abstract Recipes getRecipesContainingIngredients(ArrayList<String> cleanedIngredients);
	public abstract Recipes getRecipesWithOnlyTheseIngredients(ArrayList<String> cleanedIngredients) throws Exception;
	public abstract User getUser(String username) throws Exception;
	public abstract User createUser(User tempUserObject);
	public abstract User signInUser(String username) throws Exception; 
	public abstract User signOutUser(String username) throws Exception;
	
	//server related methods
	public abstract LifecycleEvent serverStartupTasks(LifecycleEvent startupEvent);
	public abstract LifecycleEvent serverShutdownTasks(LifecycleEvent shutdownEvent);
}
