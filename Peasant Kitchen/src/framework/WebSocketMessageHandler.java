package framework;

import com.google.gson.JsonObject;

import javax.websocket.Session;

public abstract class WebSocketMessageHandler
{
    public abstract void handleMessage(JsonObject payload, Session session) throws Exception;
}
