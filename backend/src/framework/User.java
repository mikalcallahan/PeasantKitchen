package framework;

import java.util.HashSet;


/*
 * Stores all of the information associated with a user
 * If this class starts to explode with data, we can move some of the data elsewhere
 */

public class User 
{
	public String username;
	public String emailAddress; //super secure
	public String profilePictureName;
	public Boolean signedIn = false;

	public HashSet<String> diets = new HashSet<String>(); //All of the diets' that the user is following. 
	//(This would include the user being vegan, for example)
	
	//where keys = unique identifier for recipe\category of food in the database.
	public class likes {
		public HashSet<String> recipeKeys = new HashSet<String>();
		public HashSet<String> foodCatagoryKeys = new HashSet<String>();
	}
	
	
	public User()
	{
		
	}
	
	public User(String jsonMessage)
	{
		
	}
	
	public boolean isSignedIn()
	{
		if(this.signedIn == null || this.signedIn.booleanValue() == false)
			return false;
		
		return true;
	}
}
