package webSockets.user.signin;


import javax.websocket.CloseReason;

@ServerEndpoint("/user/createNewUser")
public class CreateUser extends PostWebSocket
{
	private class Request 
	{
		public String firstname;
		public String lastname;
		public String username;
		public String password;
	}
	
	public void handleMessage(JsonObject messageJson, Session session) throws Exception 
	{
		Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(messageJson, Request.class);
		requestChecker(request);
    	
    	User user = WebSocketGlobalEnvironment.instance().getBackendController().createUser(request);
    	sendResponseToClient(session, user);
	}
	
	public void requestChecker(Request request)
	{
		isAllWhiteSpace(request);
		isEmpty(request);
	}
	
	public void isAllWhiteSpace(Request request)
	{
		//Check if all characters are whitespace here
		//If all white space: System.out.println("All white space used, please reinput name or password")
		//call handleMessage
	}
	
	public void isEmpty(Request request)
	{
		//Check if any fields are left empty
		//If empty: System.out.println("Empty field: Please input a username or password)
		//call handleMessage
	}
	
	private void sendResponseToClient(Session session, User user)
    {
    	Response response = new Response();
    	response.success = user.isSignedIn();
    	
    	String jsonResponse = WebSocketGlobalEnvironment.instance().getJsonConverter().toJson(response);
    	
    	Utilities.sendJsonMessageToClient(session, jsonResponse);
    }
	
	private class Response
	{
		public Boolean success = false;
	}

	
}
