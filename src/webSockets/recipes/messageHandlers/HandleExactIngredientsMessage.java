package webSockets.recipes.messageHandlers;

import com.google.gson.JsonObject;
import framework.Recipe;
import framework.Recipes;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.StringUtilites;
import utilities.Utilities;

import javax.websocket.Session;
import java.util.ArrayList;

/**
 * The type Handle exact ingredients message.
 */
public class HandleExactIngredientsMessage extends WebSocketMessageHandler
{
    @Override
    public void handleMessage(JsonObject payload, Session session) throws Exception
    {
        Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(payload, Request.class);

        verify(request);

        Recipes recipes = WebSocketGlobalEnvironment.instance().getBackendController().getRecipesWithOnlyTheseIngredients(request.ingredients, request.username);

        Response response = createResponse(recipes);
        Utilities.sendStandardWebSocketResponse(session, response);
        session.close();
    }

    private void verify(Request request)
    {
        if (StringUtilites.isVoidString(request.username))
            throw new NullPointerException("/recipes/exact: requires a username to process your request");

        if (request.ingredients == null || request.ingredients.isEmpty())
            throw new NullPointerException("/recipes/exact: requires a list of ingredients to process your request");

        for (String ingredient : request.ingredients)
            if (StringUtilites.isVoidString(ingredient))
                throw new NullPointerException("/recipes/exact: Malformed ingredient list. The list of ingredients contained: [" + ingredient + "]");
    }

    private Response createResponse(Recipes recipes)
    {
        Response response = new Response();

        response.recipes = recipes;

        //generate unique IDs for each recipe
        int counter = 0;

        for (Recipe recipe : response.recipes)
        {
            recipe.recipeID = counter;
            counter++;
        }

        return response;
    }

    private class Request
    {
        public String username;
        public ArrayList<String> ingredients = new ArrayList<String>();
    }

    private class Response
    {
        /**
         * The Recipes.
         */
        public Recipes recipes = new Recipes();
    }
}