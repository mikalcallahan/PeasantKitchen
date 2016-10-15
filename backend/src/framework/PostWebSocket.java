package framework;

import java.util.HashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import constants.Constants;
import utilities.Utilities;

/*
 * Can handle 1 to N messages
 */

public abstract class PostWebSocket
{
	protected HashMap<String, WebSocketMessageHandler> messageHandlers = new HashMap<String, WebSocketMessageHandler>();
	
	//Adds all of the message handler for this web socket
	public abstract boolean initialize();
	
	/*
	 * public WebSocket methods
	 */
	
	@OnOpen
    public void openConnection(Session session)
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
    
	
    @OnMessage
    public void handleMessages(String messageJson, Session session)
    {
    	try 
    	{
    		handleCurrentMessage(messageJson, session);
    	}catch(Exception e)
    	{
    		Utilities.sendErrorMessageToClient(session, e);
    	}
    }
    
    protected void handleCurrentMessage(String messageJson, Session session) throws Exception
    {
    	Request request = parseRequestJson(messageJson);
    	
    	WebSocketMessageHandler messageHandler = this.messageHandlers.get(request.id);
    	
    	if(messageHandler == null)
    		throw new NullPointerException("Sorry, but the backend cannot handle [" + request.id + "] messages yet!");
    	
    	messageHandler.handleMessage(request.payload, session);
    }
    
    private Request parseRequestJson(String messageJson)
    {
    	if(messageJson == null || messageJson.isEmpty())
    		throw new NullPointerException("ERROR: No request JSON!");
    	
    	JsonParser jsonParser = new JsonParser();
    	
    	JsonObject rootObject = jsonParser.parse(messageJson).getAsJsonObject();
    	
    	//let's make sure that the expected fields are present
    	if(rootObject.get(Constants.RequestKeys.id) == null || rootObject.get(Constants.RequestKeys.payload) == null)
    		throw new NullPointerException("ERROR: Requests to backend web sockets require a JSON object with id and payload fields mumble...mumble...");
    	
    	String id = rootObject.get(Constants.RequestKeys.id).getAsString();
    	JsonObject payload = rootObject.get(Constants.RequestKeys.payload).getAsJsonObject();
    	
    	return new Request(id, payload);
    }
    
    private static class Request
    {
    	public String id;
    	public JsonObject payload;
    	
		public Request(String id, JsonObject payload) 
		{
			super();
			this.id = id;
			this.payload = payload;
		}
    }
}
