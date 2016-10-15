package webSockets.user.signin;


import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;

import constants.Constants;
import controllers.BackendController;
import framework.PostWebSocket;
import framework.User;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.Utilities;


//Context: /user/signin

/*
 * this works for now, but we may want an always open User socket 
 * moving foward.
 */

@ServerEndpoint("/user/signin")
public class SignInUser extends PostWebSocket
{
	@Override
	public boolean initialize() 
	{
		this.messageHandlers.put(Constants.MessageHandlerIDs.signIn, new HandleSignInMessage());
		
		return true;
	}
    
    private class HandleSignInMessage extends WebSocketMessageHandler
    {
		@Override
		public void handleMessage(JsonObject messageJson, Session session) throws Exception 
		{
			Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(messageJson, Request.class);
	    	
	    	User user = WebSocketGlobalEnvironment.instance().getBackendController().signUserIn(request.username);
	    	
	    	sendResponseToClient(session, user);
		}
		
		private void sendResponseToClient(Session session, User user)
	    {
	    	Response response = new Response();
	    	response.success = user.isSignedIn();
	    	
	    	String jsonResponse = WebSocketGlobalEnvironment.instance().getJsonConverter().toJson(response);
	    	
	    	Utilities.sendJsonMessageToClient(session, jsonResponse);
	    }
		
		
		private class Request 
		{
			public String firstname;
			public String lastname;
			public String username;
			public String password;
		}
		
		private class Response
		{
			public Boolean success = false;
		}
    }
}
