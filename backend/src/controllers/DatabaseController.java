package controllers;

import designPatterns.Observer;
import designPatterns.ObserverSubject;
import framework.User;

public class DatabaseController extends ObserverSubject 
{
	private static DatabaseController instance = null;
	
	private DatabaseController()
	{
		
	}
	
	public static DatabaseController instance() 
	{
		if(instance == null)
			instance = new DatabaseController();
		
		return instance;
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