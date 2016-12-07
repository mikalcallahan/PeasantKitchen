package webSockets.recipes;

import framework.PostWebSocket;
import webSockets.recipes.messageHandlers.HandleContainingIngredientsMessage;
import webSockets.recipes.messageHandlers.HandleExactIngredientsMessage;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * The type Recipes web socket.
 */
@ServerEndpoint("/recipes")
public class RecipesWebSocket extends PostWebSocket
{
    @Override
    public boolean initialize()
    {
        this.messageHandlers.put("exact", new HandleExactIngredientsMessage());
        this.messageHandlers.put("contains", new HandleContainingIngredientsMessage());

        return true;
    }

    /**
     * Open connection.
     *
     * @param session the session
     */
    @OnOpen
    public void openConnection(Session session)
    {
    }

    /**
     * Handle message.
     *
     * @param messageJson the message json
     * @param session     the session
     */
    @OnMessage
    public void handleMessage(String messageJson, Session session)
    {
        super.handleMessages(messageJson, session);
    }

    /**
     * Close connection.
     *
     * @param reason the reason
     */
    @OnClose
    public void closeConnection(CloseReason reason)
    {

    }

    /**
     * Error.
     *
     * @param cause the cause
     */
    @OnError
    public void error(Throwable cause)
    {
        System.err.println(cause.getMessage());
    }
}