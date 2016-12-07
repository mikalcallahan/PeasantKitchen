package applicationData;

import constants.Constants;
import designPatterns.Visitor;
import framework.Parser;
import framework.Recipe;
import framework.Recipes;
import framework.User;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * The type Application data.
 */
public class ApplicationData
{
    /**
     * The Recipes.
     */
    protected Recipes recipes = new Recipes();
    /**
     * The Concurrent recipes.
     */
    protected List<Recipe> concurrentRecipes = Collections.synchronizedList(recipes);
    /**
     * The Users.
     */
    protected ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();

    private transient File parentDir;

    /**
     * Instantiates a new Application data.
     *
     * @param parentDir the parent dir
     */
    public ApplicationData(File parentDir)
    {
        this.parentDir = parentDir;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception
    {
        testLoadingAndSaving();
    }

    private static void testLoadingAndSaving() throws Exception
    {
        System.out.println("Anything?");

        File parentDir = new File("/home/stoffel/Documents/School/Software Engineering/TestingOutput/");
        ApplicationData test = new ApplicationData(parentDir);

        if(!parentDir.exists())
            System.err.println("The target directory does not exist!");

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

        test.addUser(mahNewUser);
        test.addRecipe(testRecipe);

        System.out.println(test.toString());


        System.out.println("Attempting to save the application data to [" + parentDir.getAbsolutePath() + "]");
        test.saveToDisk();

        System.out.println("Attempting to load the application data from [" + parentDir.getAbsolutePath() + "]");
        test.loadFromDisk();

        System.out.println(test.toString());
    }

    /**
     * Load from disk.
     *
     * @throws Exception the exception
     */
    public void loadFromDisk() throws Exception
    {
        File storedRecipes = this.getStoredRecipesObject();
        File storedUsers = this.getStoredUsersObject();

        if (!storedRecipes.exists())
            this.recipes = loadDefaultRecipes();
        else
            this.recipes = loadObjectFromDisk(storedRecipes, Recipes.class);

        this.concurrentRecipes = Collections.synchronizedList(this.recipes);

        if (!storedUsers.exists())
            this.users = loadDefaultUsers();
        else
            this.users = loadObjectFromDisk(storedUsers, users.getClass());
    }

    /**
     * Save to disk.
     *
     * @throws Exception the exception
     */
    public void saveToDisk() throws Exception
    {
        prepareDestinationFolder();

        saveObjectToDisk(this.getStoredRecipesObject(), this.recipes);
        saveObjectToDisk(this.getStoredUsersObject(), this.users);
    }

    private void prepareDestinationFolder() throws Exception
    {
        File storedObjectFolder = this.getStoredObjectsFolder();

        if (storedObjectFolder.exists())
            FileUtils.cleanDirectory(storedObjectFolder);

        storedObjectFolder.mkdirs();
    }

	/*
     * Getters
	 */

    /**
     * Visit recipes.
     *
     * @param visitor the visitor
     */
    public void visitRecipes(Visitor<Recipe> visitor)
    {
        synchronized (this.concurrentRecipes)
        {
            for (Recipe recipe : this.concurrentRecipes)
                visitor.visit(recipe);
        }
    }

    public Recipes filterRecipes(Predicate<Recipe> predicate)
    {
        Recipes results = new Recipes();

        this.visitRecipes(
                (Recipe recipe) ->
                {
                    if (predicate.test(recipe))
                        results.add(recipe);
                }
        );

        return results;
    }

    public Recipe addRecipe(Recipe recipe)
    {
        if(this.concurrentRecipes.add(recipe))
            return recipe;

        throw new RuntimeException("Failed to add the recipe named " + recipe.recipeName + "!");
    }

    /**
     * Visit users.
     *
     * @param visitor the visitor
     */
    public void visitUsers(Visitor<User> visitor)
    {
        for (Entry<String, User> entry : this.users.entrySet())
            visitor.visit(entry.getValue());
    }


    public User getUser(String username)
    {
        return
                this.users.get(username);
    }

    public User removeUser(String username)
    {
        return
                this.users.remove(username);
    }

    public User addUser(User user)
    {
        User newUser = new User(user);

        //Successfully handles the case where multiple threads are trying to add the same user (hence, same key)
        //at the same time.
        //I'll let the Java API decide which thread wins
        User storedUser = this.users.putIfAbsent(user.username, newUser);

        if (storedUser != null)
            throw new RuntimeException("The requested username [" + user.username + "] is already taken. Please select another");

        return newUser;
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
	
	
	/*
	 * Get various resource files\folders
	 */

    private <T> T saveObjectToDisk(File objectDestination, T object) throws Exception
    {
        FileOutputStream fos = new FileOutputStream(objectDestination.getAbsolutePath());
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(object);
        oos.close();

        return object;
    }

    private Recipes loadDefaultRecipes() throws Exception
    {
        Parser<File, Recipes> recipesParser = new RecipesCSVParser();
        return recipesParser.parse(this.getRecipesCSV());
    }

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

    /**
     * Gets dabase cs vs folder.
     *
     * @return the dabase cs vs folder
     */
    public File getDabaseCSVsFolder()
    {
        return new File(this.parentDir, Constants.databaseCSVFolder);
    }

    /**
     * Gets recipes csv.
     *
     * @return the recipes csv
     */
    public File getRecipesCSV()
    {
        return new File(this.getDabaseCSVsFolder(), Constants.recipesCSV);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ApplicationData [");
        if (recipes != null)
        {
            builder.append("recipes=");
            builder.append(recipes);
            builder.append(", ");
        }
        if (users != null)
        {
            builder.append("users=");
            builder.append(users);
        }
        builder.append("]");
        return builder.toString();
    }
}
