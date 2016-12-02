package webSockets;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * The type Test web socket.
 */
@ServerEndpoint("/events")
public class TestWebSocket
{
    /**
     * On web socket connect.
     *
     * @param sess the sess
     */
    @OnOpen
    public void onWebSocketConnect(Session sess)
    {
        System.out.println("Socket Connected: " + sess);
    }

    /**
     * On web socket text.
     *
     * @param message the message
     * @param session the session
     */
    @OnMessage
    public void onWebSocketText(String message, Session session)
    {
        System.out.println("Received TEXT message: " + message);

        try
        {
            session.getBasicRemote().sendText("Yar web sockets work");
        }
        catch (Exception e)
        {
            System.err.print("Maybe they don't");
        }
    }

    /**
     * On web socket close.
     *
     * @param reason the reason
     */
    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("Socket Closed: " + reason);
    }

    /**
     * On web socket error.
     *
     * @param cause the cause
     */
    @OnError
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }
}
