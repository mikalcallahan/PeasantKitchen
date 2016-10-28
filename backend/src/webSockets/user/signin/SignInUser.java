package webSockets.user.signin;


import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;

import constants.Constants;
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
	public SignInUser()
	{
		
	}
	
	@Override
	public boolean initialize() 
	{
		this.messageHandlers.put(Constants.MessageIDs.signInUser, new HandleSignInMessage());
		
		return true;
	}
    
    private class HandleSignInMessage extends WebSocketMessageHandler
    {
		@Override
		public void handleMessage(JsonObject messageJson, Session session) throws Exception 
		{
			Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(messageJson, Request.class);
			
	    	
	    	User user = WebSocketGlobalEnvironment.instance().getBackendController().signUserIn(request.username);
	    	
	    	//Create our response to the client
	    	Response response = new Response();
	    	response.success = user.isSignedIn();
	    	
	    	//send our response to the client
	    	Utilities.sendStandardWebSocketResponse(session, response);
	    	session.close();
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
