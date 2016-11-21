package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import constants.Constants;

public class ApplicationData 
{
	protected Recipes recipes = new Recipes();
	protected List<Recipe> concurrentRecipes = Collections.synchronizedList(recipes);
	protected ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();
	
	private transient File parentDir;
	
	public ApplicationData(File parentDir)
	{
		this.parentDir = parentDir;
	}
	
	public void loadFromDisk() throws Exception
	{
		String recipesAbsPath = getAbsolutePath(this.parentDir, Constants.recipiesFileName);
		String usersAbsPath = getAbsolutePath(this.parentDir, Constants.usersFileName);
		
		this.recipes = loadObjectFromDisk(recipesAbsPath, Recipes.class);
		
		if(this.recipes == null)
			this.recipes = new Recipes();
		
		this.concurrentRecipes = Collections.synchronizedList(this.recipes);
		
		this.users = loadObjectFromDisk(usersAbsPath, users.getClass());
		
		if(this.users == null)
			this.users = new ConcurrentHashMap<String, User>();
	}
	
	public void saveToDisk() throws Exception
	{	
		String recipesAbsPath = getAbsolutePath(this.parentDir, Constants.recipiesFileName);
		String usersAbsPath = getAbsolutePath(this.parentDir, Constants.usersFileName);
		
		prepareDestinationFolder(recipesAbsPath, usersAbsPath);
		
		saveObjectToDisk(recipesAbsPath, this.recipes);
		saveObjectToDisk(usersAbsPath, this.users);
	}
	
	private void prepareDestinationFolder(String recipesAbsPath, String usersAbsPath)
	{
		File storedRecipes = new File(recipesAbsPath);
		File storedUsers = new File(usersAbsPath);
		
		if(storedRecipes.exists())
			storedRecipes.delete();
		if(storedUsers.exists())
			storedUsers.delete();
		
		storedRecipes.mkdirs();
		storedUsers.mkdirs();
	}
	
	/*
	 * Getters
	 */
	
	public List<Recipe> getRecipes()
	{
		return this.concurrentRecipes;
	}
	
	public Map<String, User> getUsers()
	{
		return this.users;
	}
	
	
	/*
	 * Helper methods
	 */
	
	private String getAbsolutePath(File folder, String filenameInFolder)
	{
		return new File(folder.getAbsolutePath(), filenameInFolder).getAbsolutePath();
	}
	
	
	private <T> T loadObjectFromDisk(String absPathToSrc, Class<T> type) throws Exception
	{
		if(!(new File(absPathToSrc).exists()))
			return null;
		
		FileInputStream fis = new FileInputStream(absPathToSrc);
		ObjectInputStream ois = new ObjectInputStream(fis);
		T object = type.cast(ois.readObject());
		
		ois.close();
		
		return object;
	}
	
	private <T> T saveObjectToDisk(String absPathToDest, T object) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(absPathToDest);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(object);
		oos.close();
		
		return object;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplicationData [");
		if (recipes != null) {
			builder.append("recipes=");
			builder.append(recipes);
			builder.append(", ");
		}
		if (users != null) {
			builder.append("users=");
			builder.append(users);
		}
		builder.append("]");
		return builder.toString();
	}

	public static void main(String [] args) throws Exception
	{
		File parentDir = new File("/home/stoffel/Documents/School/Software Engineering/TestingOutput/");
		ApplicationData test = new ApplicationData(parentDir);
		
		User mahNewUser = new User();
		mahNewUser.username = "Mr user";
		mahNewUser.emailAddress = "mr@user.com";
		mahNewUser.firstname = "AnElifi";
		mahNewUser.lastname = "No";
		
		Recipe testRecipe = new Recipe();
		testRecipe.recipeID = 1;
		testRecipe.recipeName = "mahRecipe";
		testRecipe.recipeProcess = "1. Boil water. 2. Place egg in water. 3) Remove egg from water.";
		testRecipe.recipeRequirements = "1) Egg. 2) Water. 3) Time.";
		
		test.getUsers().put(mahNewUser.username, mahNewUser);
		test.getRecipes().add(testRecipe);
		
		System.out.println(test.toString());
		
		
		
		System.out.println("Attempting to save the application data to [" + parentDir.getAbsolutePath() + "]");
		test.saveToDisk();
		
		System.out.println("Attempting to load the application data from [" + parentDir.getAbsolutePath() + "]");
		test.loadFromDisk();
		
		System.out.println(test.toString());
	}
}
