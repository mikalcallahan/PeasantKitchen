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

		LinkedHashMap<String, Object> keyValuePairs = new LinkedHashMap<String, Object>();
		keyValuePairs.put("username", tempUserObject.username);
		keyValuePairs.put("firstName", tempUserObject.firstname);
		keyValuePairs.put("lastName", tempUserObject.lastname);
		keyValuePairs.put("email", tempUserObject.emailAddress);
		keyValuePairs.put("password", tempUserObject.password);
		keyValuePairs.put("signedIn", false);
		keyValuePairs.put("profilePictureName", ""); //this field isn't populated as yet
		keyValuePairs.put("diets", ""); //nor is this one

		String sqlCommand = insertInto("userInfo", keyValuePairs);

		//do stuff with the command.

		return new User();
	}

	//yeah...I overthought this one in retrospect. Oh well, it should kinda work.
	//we could have just added each value from the User object one at a time into a StringBuilder object
	private String insertInto(String tableName, LinkedHashMap<String, Object> keyValuePairs)
	{
		StringBuilder insertStatement = new StringBuilder("INSERT INTO " + tableName);

		insertStatement.append(addKeys(keyValuePairs));
		insertStatement.append(addValues(keyValuePairs));

		return insertStatement.toString();
	}

	//DIS WORKS: "INSERT INTO userInfo"+"(username,firstName,lastName,email,password,signedIn,profilePictureName,diets)" + "VALUES" +"('Mitch082','bRyan','Mitchell','@gmail.com','fuckthis',1,'null','null')";

	private String addKeys(LinkedHashMap<String, Object> keyValuePairs)
	{
		StringBuilder insertStatement = new StringBuilder();

		insertStatement.append("(");

		boolean isFirst = true;
		String key;

		for(Map.Entry<String, Object> entry : keyValuePairs.entrySet()) {

			key = entry.getKey();
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


	private String addValues(LinkedHashMap<String, Object> keyValuePairs)
	{
		StringBuilder values = new StringBuilder("VALUES");
		boolean isFirst = true;

		values.append("(");

		for(Map.Entry<String, Object> entry : keyValuePairs.entrySet())
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
	
	//I dunno how happy the database will be about receiving requests to sign in users
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