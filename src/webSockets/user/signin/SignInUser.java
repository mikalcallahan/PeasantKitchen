package webSockets.user.signin;


import com.google.gson.JsonObject;
import constants.Constants;
import framework.PostWebSocket;
import framework.User;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.Utilities;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


//Context: /user/signin

/*
 * this works for now, but we may want an always open User socket 
 * moving foward.
 */

/**
 * The type Sign in user.
 */
@ServerEndpoint("/user/signin")
public class SignInUser extends PostWebSocket
{
    /**
     * Instantiates a new Sign in user.
     */
    public SignInUser()
    {

    }

    @Override
    public boolean initialize()
    {
        this.messageHandlers.put(Constants.MessageIDs.signInUser, new HandleSignInMessage());

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


    private class HandleSignInMessage extends WebSocketMessageHandler
    {
        @Override
        public void handleMessage(JsonObject messageJson, Session session) throws Exception
        {
            Request request = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(messageJson, Request.class);


            User user = WebSocketGlobalEnvironment.instance().getBackendController().signUserIn(request.username);

            //Create our response to the client
            Response response = new Response();
            response.success = user.isSignedIn();
            response.username = user.username;

            //send our response to the client
            Utilities.sendStandardWebSocketResponse(session, response);
            session.close();
        }

        private class Request
        {
            /**
             * The Firstname.
             */
            public String firstname;
            /**
             * The Lastname.
             */
            public String lastname;
            /**
             * The Username.
             */
            public String username;
            /**
             * The Password.
             */
            public String password;
        }

        private class Response
        {
            /**
             * The Success.
             */
            public Boolean success = false;
            /**
             * The Username.
             */
            public String username = "";
        }
    }


}
