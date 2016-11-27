package controllers.tests;

import java.io.File;
import java.util.ArrayList;

import controllers.BackendController;
import designPatterns.Visitor;
import framework.Recipe;
import framework.Recipes;
import framework.User;
import utilities.CollectionUtils;

public class TesterMain {

	public static void main(String[] args) 
	{
		try 
		{
			File parentDir = new File("/home/stoffel/Documents/School/Software Engineering/TestingOutput/");
			BackendController testingController = BackendController.makeTestingBackendController(parentDir);

			User newUser = new User();
			newUser.username = "MAH USER";
			newUser.password = "password";

			User signedInUser;

			ArrayList<String> ingredients = CollectionUtils.arrayList("ground beef");

			signedInUser = testingController.createUser(newUser);
			signedInUser = testingController.signUserIn(signedInUser.username);

			Recipes recipes = testingController.getRecipesContainingIngredients(ingredients, newUser.username);
			
			recipes.visit(new Visitor<Recipe>() 
			{
				@Override
				public void visit(Recipe item) 
				{
					System.out.println(item.toString() + "\n");
				}
			});
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
