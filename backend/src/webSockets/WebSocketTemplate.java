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

public class WebSocketTemplate
{
	@OnOpen
    public void openConnection(Session session)
    {
        
    }
    
    @OnMessage
    public void handleMessage(String jsonMessage, Session session)
    {
    	
    }
    
    private Request convert(String jsonMessage)
    {
    	return null;
    }
    
    private boolean verify(Request request)
    {
    	return true;
    }
    
    private Object process(Request request)
    {
    	return null;
    }
    
    private void sendResponse(Object object)
    {
    	
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
		public String username; 
	}
	
	private class Response
	{
		public Boolean success = false;
	}
}
