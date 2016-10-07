package webSockets;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.BackendController;
import framework.User;


//Context: /user/signin

/*
 * this works for now, but we may want an always open User socket 
 * moving foward.
 */

public class SignIn
{
	@OnOpen
    public void openConnection(Session session)
    {
        System.out.println("WebSocket connected: " + session);
    }
    
    @OnMessage
    public void handleMessage(String jsonMessage, Session session)
    {
//    	Gson gson = new GsonBuilder().create();
//    	UserJsonMessage userRequest =  gson.fromJson(jsonMessage, UserJsonMessage.class);
//    	
//    	User user = BackendController.instance().signUserIn(username);
//  
//    	
//    	Response response = new Response();
//    	response.success = true;
    	
    	
    }
    
    @OnClose
    public void closeConnection(CloseReason reason)
    {
        System.out.println("Socket Closed: " + reason);
    }
    
    @OnError
    public void error(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }
	
	private class Request 
	{
		public String username; 
	}
	
	private class Response
	{
		public Boolean success = false;
	}
}
