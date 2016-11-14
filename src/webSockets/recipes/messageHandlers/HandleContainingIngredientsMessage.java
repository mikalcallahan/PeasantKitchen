package webSockets.recipes.messageHandlers;

import java.util.ArrayList;

import javax.websocket.Session;

import com.google.gson.JsonObject;

import framework.Recipes;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.StringUtils;
import utilities.Utilities;

public class HandleContainingIngredientsMessage extends WebSocketMessageHandler
{
	@Override
	public void handleMessage(JsonObject payload, Session session) throws Exception 
	{
		Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(payload, Request.class);
		
		verify(request);
		
		Recipes recipes = WebSocketGlobalEnvironment.instance().getBackendController().getRecipesContainingIngredients(request.ingredients, request.username);
		
		Response response = createResponse(recipes);
		Utilities.sendStandardWebSocketResponse(session, response);
		session.close();
	}
	
	private void verify(Request request)
	{
		if(StringUtils.isVoidString(request.username))
			throw new NullPointerException("/recipes/contains: requires a username to process your request");
		
		if(request.ingredients == null || request.ingredients.isEmpty())
			throw new NullPointerException("/recipes/contains: requires a list of ingredients to process your request");
		
		for(String ingredient : request.ingredients)
			if(StringUtils.isVoidString(ingredient))
				throw new NullPointerException("/recipes/contains: Malformed ingredient list. The list of ingredients contained: [" + ingredient + "]");
	}
	
	private Response createResponse(Recipes recipes)
	{
		Response response = new Response();
		
		response.recipes = recipes;
		
		return response;
	}
	
	
	private class Request
	{
		public ArrayList<String> ingredients = new ArrayList<String>();
		public String username;
	}
	
	private class Response
	{
		public Recipes recipes = new Recipes();
	}
	
}