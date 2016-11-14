package webSockets.user.signout;


import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;

import constants.Constants;
import framework.PostWebSocket;
import framework.User;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.Utilities;


//Context: /user/signout

/*
 * THIS CODE NEEDS MODIFICATIONS MOST LIKELY! 
 */


@ServerEndpoint("/user/signout")
public class SignOutUser extends PostWebSocket
{
	public SignOutUser()
	{
		
	}
	
	@Override
	public boolean initialize() 
	{
		this.messageHandlers.put(Constants.MessageHandlerIDs.signOut, new HandleSignOutMessage());
		
		return true;
	}
    
    private class HandleSignOutMessage extends WebSocketMessageHandler
    {
		@Override
		public void handleMessage(JsonObject messageJson, Session session) throws Exception 
		{
	    	//User user = WebSocketGlobalEnvironment.instance().getBackendController().signUserOut(request.username);
	    	
	    	//Create our response to the client
	    	Response response = new Response();
	    	response.success = user.isSignedOut();
	    	
request.session().invalidate();
response.sendRedirect(request.index() + "/homePage.html");

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





