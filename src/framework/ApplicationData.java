package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.opencsv.CSVReader;

import constants.Constants;
import designPatterns.Visitor;

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
		File storedRecipes = new File(this.parentDir, Constants.recipiesFileName);
		File storedUsers = new File(this.parentDir, Constants.usersFileName);
		
		if(!storedRecipes.exists())
			this.recipes = loadDefaultRecipes();
		else
			this.recipes = loadObjectFromDisk(storedRecipes, Recipes.class);
		
		this.concurrentRecipes = Collections.synchronizedList(this.recipes);
		
		if(!storedUsers.exists())
			this.users = loadDefaultUsers();
		else
			this.users = loadObjectFromDisk(storedUsers, users.getClass());
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
	
	public void visitRecipes(Visitor<Recipe> visitor)
	{
		synchronized (this.concurrentRecipes) 
		{
			for(Recipe recipe : this.concurrentRecipes)
				visitor.visit(recipe);
		}
	}
	
	public void visitUsers(Visitor<User> visitor)
	{
		for(Entry<String, User> entry : this.users.entrySet())
			visitor.visit(entry.getValue());
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
	
	private Recipes loadDefaultRecipes() throws Exception
	{
		List<String[]> recipesCSVData = parseCSV("RECIPES.csv");
		return convertCSVDataToRecipes(recipesCSVData);
	}
	
	private List<String[]> parseCSV(String csvFileName) throws Exception
	{
		File csvFile = new File(this.parentDir, csvFileName);
		CSVReader reader = new CSVReader(new FileReader(csvFile.getAbsolutePath()));
		List<String[]> csvEntries = reader.readAll();
		
		return csvEntries;
	}
	
	private Recipes convertCSVDataToRecipes(List<String[]> recipesCSVData)
	{
		Recipes recipes = new Recipes();
		Recipe currRecipe;
		String[] lineEntries;
		
		//Ignore the header (line 1)
		for (int index = 1; index < recipesCSVData.size(); index++) 
		{
			lineEntries = recipesCSVData.get(index);
			currRecipe = convertCSVLineToRecipe(lineEntries);
			
			if(currRecipe != null)
				recipes.add(currRecipe);
		}
		
		
		return recipes;
	}
	
	private Recipe convertCSVLineToRecipe(String[] lineEntries)
	{
		Recipe recipe = new Recipe();
		
		recipe.recipeID = Integer.parseInt(lineEntries[0].trim());
		recipe.recipeName = lineEntries[1];
		recipe.recipeRequirements = lineEntries[2];
		recipe.recipeProcess = lineEntries[3];
		recipe.ingredientQuantities = parseIngredientQuantities(recipe.recipeProcess);
		
		return recipe;
	}
	
	private ArrayList<IngredientQuantity> parseIngredientQuantities(String ingredientProcess)
	{
		ArrayList<IngredientQuantity> ingredientQuantities = new ArrayList<IngredientQuantity>();
		
		//<Number><Unit><Ingredient>, ...
		
		for(String ingredientQuantityString : ingredientProcess.split(","))
		{
			for(String element : ingredientQuantityString.split("<"))
			{
				if(!element.endsWith(">"))
					//malformed
					throw new NullPointerException("GAH WHY NO WORK");
				
			}
		}
		
		return ingredientQuantities;
	}
	
	private ConcurrentHashMap<String, User> loadDefaultUsers()
	{
		return new ConcurrentHashMap<String, User>(); //there are not default users
	}
	
	private String getAbsolutePath(File folder, String filenameInFolder)
	{
		return new File(folder.getAbsolutePath(), filenameInFolder).getAbsolutePath();
	}
	
	
	private <T> T loadObjectFromDisk(File objectLocation, Class<T> type) throws Exception
	{
		FileInputStream fis = new FileInputStream(objectLocation.getAbsolutePath());
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
