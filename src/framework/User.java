package framework;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;


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
	public String password; //also super secure
	public String firstname = "";
	public String lastname = "";

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
		return !(this.signedIn == null || !this.signedIn);

	}

	public LinkedHashMap<String, Object> toMap()
	{
		LinkedHashMap<String, Object> fieldNameToValue = new LinkedHashMap<String, Object>();

		try {
			for (Field field : this.getClass().getFields())
				fieldNameToValue.put(field.getName(), field.get(this));
		}catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}

		return fieldNameToValue;
	}
}