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
		File storedRecipes = new File(this.parentDir, Constants.storedRecipesObjectFileName);
		File storedUsers = new File(this.parentDir, Constants.storedUsersObjectFileName);
		
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
		
		//<Number><Unit><Ingredient>, ...
		ArrayList<String> elementValues = new ArrayList<String>(3);
		
		for(String ingredientQuantityString : ingredientProcess.split(Constants.ApplicationData.elementSeperator))
		{
			ingredientQuantityString = ingredientQuantityString.trim();

			System.out.println("Ingredient quantity string:" + ingredientQuantityString);
			
			for(String element : ingredientQuantityString.split(Constants.ApplicationData.openElement))
			{
				element = element.trim();

				System.out.println("Attempting to parse: " + element);
				
				if(!element.endsWith(Constants.ApplicationData.closingElement))
					throw new NullPointerException("Parse error: The Ingredient quantity string [" + element + "] was malformed.");
				
				elementValues.add(StringUtilites.removeEndingCharacters(element, 1)); //Remove the trailing closingElement from the element string
			}
			
			ingredientQuantities.add(makeIngredientQuantity(elementValues));
			elementValues.clear();
		}
		
		return ingredientQuantities;
	}
	
	//<Number><Unit><Ingredient>, ...
	
	private IngredientQuantity makeIngredientQuantity(ArrayList<String> elementValues)
	{
		if(elementValues.size() != 3)
			throw new NullPointerException("Parse error: The <Number><Unit><Ingredient> must all be specified. You cannot leave one off!");
		
		IngredientQuantity ingredientQuantity = new IngredientQuantity();
		
		ingredientQuantity.quantity = getQuantity(elementValues.get(0));
		ingredientQuantity.unit = elementValues.get(1).trim();
		
		if(StringUtilites.isVoidString(elementValues.get(2)))
			throw new NullPointerException("Parse error: You must specify an ingredient in the last element of: [" + StringUtilites.join(elementValues) + "]");
		
		ingredientQuantity.ingredient = elementValues.get(2).trim();
		
		return ingredientQuantity;
	}
	
	private Integer getQuantity(String rawQuantityString)
	{
		if(StringUtilites.isVoidString(rawQuantityString))
			rawQuantityString = "1";
		
		return Integer.parseInt(rawQuantityString.trim());
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
		//<2><lb><Eggs>

		String test = "<2><lb><Eggs>";

		for(String str : StringUtils.split("<"))
			System.out.println(str);
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
