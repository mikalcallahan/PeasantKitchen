package framework;

import com.google.gson.JsonObject;

import javax.websocket.Session;

/**
 * The type Web socket message handler.
 */
public abstract class WebSocketMessageHandler
{
    /**
     * Handle message.
     *
     * @param payload the payload
     * @param session the session
     * @throws Exception the exception
     */
    public abstract void handleMessage(JsonObject payload, Session session) throws Exception;
}
