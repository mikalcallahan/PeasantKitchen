package webSockets.user.signout;

import javax.websocket.Session;

import com.google.gson.JsonObject;

import constants.Constants;
import framework.PostWebSocket;
import framework.User;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.StringUtils;
import utilities.Utilities;

public class SignOutUser extends PostWebSocket
{
	@Override
	public boolean initialize() 
	{
		this.messageHandlers.put(Constants.MessageIDs.signOutUser, new HandleSignOutMessage());
		
		return true;
	}
	
	
	private class HandleSignOutMessage extends WebSocketMessageHandler
	{

		@Override
		public void handleMessage(JsonObject payload, Session session) throws Exception 
		{
			Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(payload, Request.class);
			
			verify(request);
			
			User user = WebSocketGlobalEnvironment.instance().getBackendController().signUserOut(request.username);
			
			Response response = new Response();
			response.success = true;
			
			Utilities.sendStandardWebSocketResponse(session, response);
			session.close();
		}
		
		private void verify(Request request)
		{
			if(StringUtils.isVoidString(request.username))
				throw new NullPointerException("ERROR: You must specify the username of the user to sign out");
		}
		
		private class Request
		{
			public String username;
		}
		
		private class Response
		{
			public Boolean success = true;
		}
	}

}