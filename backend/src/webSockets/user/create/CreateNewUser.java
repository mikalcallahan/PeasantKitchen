package webSockets.user.create;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;

import constants.Constants;
import framework.PostWebSocket;
import framework.User;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.StringUtils;
import utilities.Utilities;

@ServerEndpoint("/user/create")
public class CreateNewUser extends PostWebSocket 
{

	@Override
	public boolean initialize() 
	{
		this.messageHandlers.put(Constants.MessageIDs.createNewUser, new CreateUserMessageHandler());
		
		return true;
	}
	
	private class CreateUserMessageHandler extends WebSocketMessageHandler
	{
		@Override
		public void handleMessage(JsonObject payload, Session session) throws Exception 
		{
			RequestParameters requestParameters = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(payload, RequestParameters.class);
			
			verify(requestParameters);
			
			//Used to prevent createUser having a bigillion parameters
			User tempUser = createTempUser(requestParameters);
			
			User createdUser = WebSocketGlobalEnvironment.instance().getBackendController().createUser(tempUser);
			
			Response response = createResponse(createdUser);
			Utilities.sendStandardWebSocketResponse(session, response);
			session.close();
		}
		
		private void verify(RequestParameters requestParameters) throws Exception
		{
			if(StringUtils.isVoidString(requestParameters.username))
				throw new NullPointerException("You must specify a username to create a new account");
			
			if(StringUtils.isVoidString(requestParameters.password))
				throw new NullPointerException("You must specify a password to create a new account");
		}
		
		private User createTempUser(RequestParameters requestParameters)
		{
			User tempUser = new User();
			tempUser.username = requestParameters.username;
			tempUser.emailAddress = requestParameters.email;
			tempUser.password = requestParameters.password;
			
			return tempUser;
		}
		
		private Response createResponse(User createdUser)
		{
			Response response = new Response();
			response.success = true;
			response.username = createdUser.username;
			
			return response;
		}
		
	}
	
	
	/*
	 * Note: the selected user name may already exist. There should be a method on the BackendController
	 * that can detect if a user with the given username already exists
	 * -Futher note: Each user's username must be unique
	 */
	
	private class RequestParameters
	{
		public String username;
		public String email;
		public String password;
	}
	
	private class Response
	{
		public String username = "";
		public Boolean success = true;
	}
}
