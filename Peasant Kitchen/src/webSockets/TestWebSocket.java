package webSockets;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/events")
public class TestWebSocket
{
    @OnOpen
    public void onWebSocketConnect(Session sess)
    {
        System.out.println("Socket Connected: " + sess);
    }

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

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("Socket Closed: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }
}
