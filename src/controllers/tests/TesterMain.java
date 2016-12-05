package controllers.tests;

import a.c.T;
import controllers.BackendController;
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
//        try
//        {
//            File parentDir = new File("/home/stoffel/Documents/School/Software Engineering/TestingOutput/");
//            BackendController testingController = BackendController.makeTestingBackendController(parentDir);
//
//            User newUser = new User();
//            newUser.username = "MAH USER";
//            newUser.password = "password";
//
//            User signedInUser;
//
//            ArrayList<String> ingredients = CollectionUtils.arrayList("ground beef");
//
//            signedInUser = testingController.createUser(newUser);
//            signedInUser = testingController.signUserIn(signedInUser.username);
//
//            Recipes recipes = testingController.getRecipesContainingIngredients(ingredients, newUser.username);
//
//            recipes.visit(new Visitor<Recipe>()
//            {
//                @Override
//                public void visit(Recipe item)
//                {
//                    System.out.println(item.toString() + "\n");
//                }
//            });
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }


    /*
        function chunk(array, chunkSize) {
        var totalChunks = Math.ceil(array.length / chunkSize);
        var chunks = new Array(totalChunks);
        var beginningOfChunk = 0;
        var endOfChunk = chunkSize;
        var chunkCount = 0;

        while (endOfChunk <= array.length) {
            chunks.add(array.slice(beginningOfChunk, endOfChunk));

            beginningOfChunk = endOfChunk;
            endOfChunk = endOfChunk + chunkSize;
            chunkCount = chunkCount + 1;
        }

        if (chunkCount < totalChunks)
            chunks.add(array.slice(beginningOfChunk, array.length))
    }


    }
    */
    }




}
