package framework;

import javax.websocket.Session;

import com.google.gson.JsonObject;

public abstract class WebSocketMessageHandler 
{
	public abstract void handleMessage(JsonObject messageJson, Session session) throws Exception;
}
