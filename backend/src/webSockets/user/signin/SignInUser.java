package webSockets.user.signin;


import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;


import controllers.BackendController;
import framework.User;
import framework.WebSocketGlobalEnvironment;
import utilities.Utilities;


//Context: /user/signin

/*
 * this works for now, but we may want an always open User socket 
 * moving foward.
 */

public class SignInUser
{
	@OnOpen
    public void openConnection(Session session)
    {
        
    }
    
	
    @OnMessage
    public void handleMessage(String jsonMessage, Session session)
    {
    	try 
    	{
    		handleSignInRequest(jsonMessage, session);
    	}catch(Exception e)
    	{
    		Utilities.sendErrorMessageToUser(session, e);
    	}
    }
    
    private void handleSignInRequest(String jsonMessage, Session session) throws Exception
    {
    	Request request = WebSocketGlobalEnvironment.instance().jsonConverter.fromJson(jsonMessage, Request.class);
    	
    	if(!verify(request))
    		return;
    	
    	User user = WebSocketGlobalEnvironment.instance().backendController.signUserIn(request.username);
    	sendResponse(session, user);
    }
  

    
    private boolean verify(Request request) throws Exception
    {
    	return true;
    }
    
    private void sendResponse(Session session, User user)
    {
    	Response response = new Response();
    	response.success = user.isSignedIn();
    	
    	String jsonResponse = WebSocketGlobalEnvironment.instance().jsonConverter.toJson(response);
    	
    	Utilities.sendJsonMessageToClient(session, jsonResponse);
    } 
    
    @OnClose
    public void closeConnection(CloseReason reason)
    {
        
    }
    
    @OnError
    public void error(Throwable cause)
    {
        cause.printStackTrace(System.err);
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
