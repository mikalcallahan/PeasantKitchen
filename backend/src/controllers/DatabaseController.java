package controllers;

import designPatterns.Observer;
import designPatterns.ObserverSubject;
import framework.User;

import java.util.LinkedHashMap;
import java.util.Map;

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
	
	public synchronized User createUser(User tempUserObject)
	{
		//DIS WORKS: "INSERT INTO userInfo"+"(username,firstName,lastName,email,password,signedIn,profilePictureName,diets)" + "VALUES" +"('Mitch082','bRyan','Mitchell','@gmail.com','fuckthis',1,'null','null')";


	}

	private String insertInto(String tableName, LinkedHashMap<String, String> keyValuePairs)
	{
		StringBuilder insertStatement = new StringBuilder("INSERT INTO " + tableName);

		insertStatement.append(addKeys(orderedKeySet(keyValuePairs)));
		insertStatement.append(addValues(keyValuePairs));

		return insertStatement.toString();
	}

	private ArrayList<String> orderedKeySet(LinkedHashMap<String, String> keyValuePairs)
	{
		ArrayList<String> orderedKeys = new ArrayList<String>();

		for(Entry<String, String> entry : keyValuePairs.entrySet())
			orderedKeys.add(entry.getKey());

		return orderedKeys;
	}

	//DIS WORKS: "INSERT INTO userInfo"+"(username,firstName,lastName,email,password,signedIn,profilePictureName,diets)" + "VALUES" +"('Mitch082','bRyan','Mitchell','@gmail.com','fuckthis',1,'null','null')";

	private String addKeys(ArrayList<String> keys)
	{
		StringBuilder insertStatement = new StringBuilder();

		insertStatement.append("(");

		boolean isFirst = true;

		for(String key : keys) {

			if(isFirst) {
				insertStatement.append(key);
				isFirst = false;
			}
			else
				insertStatement.append("," + key);

		}

		insertStatement.append(")");

		return insertStatement.toString();
	}

	//DIS WORKS: "INSERT INTO userInfo"+"(username,firstName,lastName,email,password,signedIn,profilePictureName,diets)" + "VALUES" +"('Mitch082','bRyan','Mitchell','@gmail.com','fuckthis',1,'null','null')";


	private String addValues(LinkedHashMap<String, String> keyValuePairs)
	{
		StringBuilder values = new StringBuilder("VALUES");
		boolean isFirst = true;

		values.append("(");

		for(Map.Entry<String, String> entry : keyValuePairs.entrySet())
		{
			if(isFirst) {
				values.append("'" + entry.getValue() + "'");
				isFirst = false;
			}
			else
				values.append(",'" + entry.getValue() + "'");
		}

		values.append(")");

		return values.toString();
	}
	
	//I dunno how happy the database will happy about receiving requests to sign in users
	//Simultaneously from multiple threads. To avoid funny race condition bugs, I've just bluntly
	//synchronized this method (and for now, I'd imagine any method that involves writing data to the
	//database should be synchronized)
	public synchronized User signInUser(String username)
	{
		//do the necessary stuff to sign a user in
		
		return null;
	}
	
	public synchronized User signOutUser(String username)
	{
		
		
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