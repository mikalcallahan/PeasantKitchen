package webSockets.user.signout;

import com.google.gson.JsonObject;
import constants.Constants;
import framework.PostWebSocket;
import framework.User;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.StringUtilites;
import utilities.Utilities;

import javax.websocket.*;

public class SignOutUser extends PostWebSocket
{
    @Override
    public boolean initialize()
    {
        this.messageHandlers.put(Constants.MessageIDs.signOutUser, new HandleSignOutMessage());

        return true;
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


    private class HandleSignOutMessage extends WebSocketMessageHandler
    {

        @Override
        public void handleMessage(JsonObject payload, Session session) throws Exception
        {
            Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(payload, Request.class);

            verify(request);

            User user = WebSocketGlobalEnvironment.instance().getBackendController().signUserOut(request.username);

            Response response = new Response();
            response.success = true;

            Utilities.sendStandardWebSocketResponse(session, response);
            session.close();
        }

        private void verify(Request request)
        {
            if (StringUtilites.isVoidString(request.username))
                throw new NullPointerException("ERROR: You must specify the username of the user to sign out");
        }

        private class Request
        {
            public String username;
        }

        private class Response
        {
            public Boolean success = true;
        }
    }

}
