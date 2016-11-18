package webSockets;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;

import framework.PostWebSocket;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.Utilities;

@ServerEndpoint("/change/me")
public class WebSocketTemplate extends PostWebSocket
{
	@Override
	public boolean initialize() 
	{
		//add message handlers here
		
		return false;
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

	private class MessageHandler extends WebSocketMessageHandler
	{
		@Override
		public void handleMessage(JsonObject payload, Session session) throws Exception 
		{
			Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(payload, Request.class);
			
			verify(request);
			
			//Call some method(s) from the BackendController
			
			//Response response = createResponse(object);
			//Utilities.sendStandardWebSocketResponse(session, response);
			session.close();
		}
		
		private void verify(Request request)
		{
			
		}
		
		private Response createResponse()
		{
			Response response = new Response();
			
			//add stuff to the response
			
			return response;
		}
	}
	
	private class Request
	{
		
	}
	
	private class Response
	{
		
	}
}