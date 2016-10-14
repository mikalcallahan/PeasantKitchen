package controllers;

import designPatterns.Observer;
import designPatterns.ObserverSubject;
import framework.User;

public class DatabaseController extends ObserverSubject 
{
	public DatabaseController()
	{
		
	}
	
	public User getUser(String username)
	{
		//Do whatever is nessessary to query the database....
		//then: construct a User object, and set the signin flag as appropiate.
		
		User user = new User();
		user.signedIn = true;
		
		return user;
	}
	
	public User addUser(User tempUserObject)
	{
		//do stuff
		return null;
	}

	@Override
	public void addObserver(Observer observer) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void removeObserver(Observer observer) 
	{
		// TODO Auto-generated method stub
	}
}