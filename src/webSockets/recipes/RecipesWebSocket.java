package webSockets.recipes;

import javax.websocket.Session;


import webSockets.recipes.messageHandlers.HandleContainingIngredientsMessage;
import webSockets.recipes.messageHandlers.HandleExactIngredientsMessage;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import framework.PostWebSocket;

@ServerEndpoint("/recipes")
public class RecipesWebSocket extends PostWebSocket
{
	@Override
	public boolean initialize() 
	{
		this.messageHandlers.put("exact", new HandleExactIngredientsMessage());
		this.messageHandlers.put("contains", new HandleContainingIngredientsMessage());
		
		return true;
	}
	
	@OnOpen
	public void openConnection(Session session) 
	{
	}
	
	@OnMessage
	public void handleMessage(String messageJson, Session session) 
	{
		super.handleMessages(messageJson, session);
	}

	@OnClose
	public void closeConnection(CloseReason reason) 
	{
		
	}

	@OnError
	public void error(Throwable cause) 
	{
		System.err.println(cause.getMessage());
	}
}