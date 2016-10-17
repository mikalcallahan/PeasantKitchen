package framework;

import javax.websocket.Session;

import com.google.gson.JsonObject;

public abstract class WebSocketMessageHandler
{
	public abstract void handleMessage(JsonObject payload, Session session) throws Exception;
}
