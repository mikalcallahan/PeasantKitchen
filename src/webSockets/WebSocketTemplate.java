package webSockets;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import framework.PostWebSocket;

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

	
}
