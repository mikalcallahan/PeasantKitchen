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
		//then: construct a User object, and set the signin flag as appropriate.
		
		//Please return null if a user with the requested username cannot be found
		
		User user = new User();
		user.signedIn = true;
		
		return user;
	}
	
	public User createUser(User tempUserObject)
	{
		//do stuff
		return null;
	}
	
	//I dunno how happy the database will happy about receiving requests to sign in users
	//Simultaneously from multiple threads. To avoid funny race condition bugs, I've just bluntly
	//synchronized this method (and for now, I'd imagine any method that involves writing data to the
	//database should be synchronized)
	public synchronized User signInUser(String username)
	{
		//do the nessessary stuff to sign a user in
		
		return null;
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
}