package webSockets;

import com.google.gson.JsonObject;
import framework.PostWebSocket;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * The type Web socket template.
 */
@ServerEndpoint("/change/me")
public class WebSocketTemplate extends PostWebSocket
{
    @Override
    public boolean initialize()
    {
        //add message handlers here

        return false;
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

    private class MessageHandler extends WebSocketMessageHandler
    {
        @Override
        public void handleMessage(JsonObject payload, Session session) throws Exception
        {
            Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(payload, Request.class);

            verify(request);

            //Call some method(s) from the BackendControllerImpl

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