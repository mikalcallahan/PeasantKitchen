package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.opencsv.CSVReader;

import constants.Constants;
import designPatterns.Visitor;
import org.apache.commons.io.FileUtils;
import utilities.StringUtilites;

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
		File storedRecipes = this.getStoredRecipesObject();
		File storedUsers = this.getStoredUsersObject();
		
		if(!storedRecipes.exists())
			this.recipes = loadDefaultRecipes();
		else
			this.recipes = loadObjectFromDisk(storedRecipes, Recipes.class);
		
		this.concurrentRecipes = Collections.synchronizedList(this.recipes);
		
		if(!storedUsers.exists())
			this.users = loadDefaultUsers();
		else
			this.users = loadObjectFromDisk(storedUsers, users.getClass());
		
//		if(this.users == null)
//			System.out.println("Failed to load the users!");
//		else if(this.users.isEmpty())
//			System.out.println("No users to load");
//		else
//			for(Entry<String, User> entry : this.users.entrySet())
//				System.out.println("Entry: " + entry.getKey() + "." + entry.getValue());
	}
	
	public void saveToDisk() throws Exception
	{	
        prepareDestinationFolder();
		
		saveObjectToDisk(this.getStoredRecipesObject(), this.recipes);
		saveObjectToDisk(this.getStoredUsersObject(), this.users);
	}

	private void prepareDestinationFolder() throws Exception
    {
        File storedObjectFolder = this.getStoredObjectsFolder();

        if(storedObjectFolder.exists())
			FileUtils.deleteDirectory(storedObjectFolder);

		storedObjectFolder.mkdirs();
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
		List<String[]> recipesCSVData = parseCSV(this.getRecipesCSV());

//		for (int index = 1; index < recipesCSVData.size(); index++)
//		{
//			for(String item : recipesCSVData.get(index))
//				System.out.print(item + "  ");
//			System.out.println();
//		}

		return convertCSVDataToRecipes(recipesCSVData);
	}
	
	private List<String[]> parseCSV(File csvFile) throws Exception
	{
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

		recipe.recipeID = getRecipeID(lineEntries[0]);
		recipe.recipeName = lineEntries[1];
		recipe.recipeRequirements = lineEntries[2];
		recipe.recipeProcess = lineEntries[3];
		recipe.ingredientQuantities = parseIngredientQuantities(recipe.recipeProcess);
		
		return recipe;
	}

	private Integer getRecipeID(String rawRecipeID)
	{
		if(StringUtilites.isVoidString(rawRecipeID))
			return new Integer(1);
		else
			return Integer.parseInt(rawRecipeID.trim());
	}
	
	public ArrayList<IngredientQuantity> parseIngredientQuantities(String ingredientProcess)
	{
		ArrayList<IngredientQuantity> ingredientQuantities = new ArrayList<IngredientQuantity>();
		String[] ingredientQuantityItems;
		String element;
		
		//<Number><Unit><Ingredient>, ...
		ArrayList<String> elementValues = new ArrayList<String>(3);
		
		for(String ingredientQuantityString : ingredientProcess.split(Constants.ApplicationData.elementSeperator))
		{
			ingredientQuantityString = ingredientQuantityString.trim();

			System.out.println("Ingredient quantity string:" + ingredientQuantityString);
			
			ingredientQuantityItems = ingredientQuantityString.split(Constants.ApplicationData.openElement);
			
			for(int index = 0; index < ingredientQuantityItems.length; index++)
			{
				element = ingredientQuantityItems[index].trim();
				
				if(StringUtilites.isVoidString(element))
					continue;

				
				if(!element.endsWith(Constants.ApplicationData.closingElement))
					throw new NullPointerException("Parse error: The Ingredient quantity string [" + element + "], in the element [" + ingredientQuantityString + "], was malformed.");
				
				elementValues.add(StringUtilites.removeEndingCharacters(element, 1)); //Remove the trailing closingElement from the element string
			}
			
			ingredientQuantities.add(makeIngredientQuantity(elementValues));
			elementValues.clear();
		}
		
		for(IngredientQuantity test : ingredientQuantities)
			System.out.println(test.toString());
		
		return ingredientQuantities;
	}
	
	//<Number><Unit><Ingredient>, ...
	
	private IngredientQuantity makeIngredientQuantity(ArrayList<String> elementValues)
	{
		if(elementValues.size() != 3)
			throw new NullPointerException("Parse error: You must specify the ingredient in this format: <Number><Unit><Ingredient>. You must have exactly 3 opening < and 3 closing >; none of the < or > can be ommitted!");
		
		IngredientQuantity ingredientQuantity = new IngredientQuantity();
		
		ingredientQuantity.quantity = getQuantity(elementValues.get(0));
		ingredientQuantity.unit = elementValues.get(1).trim();
		ingredientQuantity.ingredient = getIngredient(elementValues.get(2));
		
		return ingredientQuantity;
	}
	
	private Integer getQuantity(String rawQuantityString)
	{
		if(StringUtilites.isVoidString(rawQuantityString))
			rawQuantityString = "1";
		
		return Integer.parseInt(rawQuantityString.trim());
	}
	
	private String getIngredient(String rawIngredient)
	{
		if(StringUtilites.isVoidString(rawIngredient))
			throw new NullPointerException("Parse error: Missing ingredient!");
		
		return StringUtilites.cleanIngredient(rawIngredient);
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
	
	private <T> T saveObjectToDisk(File objectDestination, T object) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(objectDestination.getAbsolutePath());
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(object);
		oos.close();
		
		return object;
	}
	
	
	/*
	 * get File Objects
	 */

	private File getStoredObjectsFolder()
    {
        return new File(this.parentDir, Constants.storedObjectsFolderName);
    }
	
	private File getStoredUsersObject()
	{
        return new File(this.getStoredObjectsFolder(), Constants.storedUsersObjectFileName);
	}
	
	private File getStoredRecipesObject()
	{
		return new File(this.getStoredObjectsFolder(), Constants.storedRecipesObjectFileName);
	}

	public File getDabaseCSVsFolder()
	{
		return new File(this.parentDir, Constants.databaseCSVFolder);
	}
	
	public File getRecipesCSV()
	{
		return new File(this.getDabaseCSVsFolder(), Constants.recipesCSV);
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
		testLoadingAndSaving();
	}

	private static void testLoadingAndSaving() throws Exception
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
