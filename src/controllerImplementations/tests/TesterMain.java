package controllerImplementations.tests;

import controllerImplementations.BackendControllerImpl;
import designPatterns.Visitor;
import framework.Recipe;
import framework.Recipes;
import framework.User;
import utilities.CollectionUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * The type Tester main.
 */
public class TesterMain
{

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args)
    {
        try
        {
            File parentDir = new File("/home/stoffel/Documents/School/Software Engineering/TestingOutput");
            BackendControllerImpl testingController = BackendControllerImpl.makeTestingBackendControllerImpl(parentDir);

            User newUser = new User();
            newUser.username = "MAH USER";
            newUser.password = "password";

            User signedInUser;

            ArrayList<String> ingredients = CollectionUtils.arrayList("Ground Beef");

            signedInUser = testingController.createUser(newUser);
            signedInUser = testingController.signUserIn(signedInUser.username);

            Recipes recipes = testingController.getRecipesContainingIngredients(ingredients, newUser.username);
            
            for(Recipe recipe : recipes)
                System.out.println("recipe.toString() = " + recipe.toString());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
