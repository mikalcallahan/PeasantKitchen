package webSockets.user.create;

import com.google.gson.JsonObject;
import constants.Constants;
import framework.PostWebSocket;
import framework.User;
import framework.WebSocketGlobalEnvironment;
import framework.WebSocketMessageHandler;
import utilities.StringUtilites;
import utilities.Utilities;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * The type Create new user.
 */
@ServerEndpoint("/user/create")
public class CreateNewUser extends PostWebSocket
{
    /**
     * Instantiates a new Create new user.
     */
    public CreateNewUser()
    {
        super();
    }

    @Override
    public boolean initialize()
    {
        this.messageHandlers.put(Constants.MessageIDs.createNewUser, new HandleCreateNewUserMessage());
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

    private class HandleCreateNewUserMessage extends WebSocketMessageHandler
    {
        @Override
        public void handleMessage(JsonObject payload, Session session) throws Exception
        {
            RequestParameters requestParameters = WebSocketGlobalEnvironment.instance().getJsonConverter().fromJson(payload, RequestParameters.class);

            verify(requestParameters);

            //Used to prevent createUser having a bigillion parameters
            User tempUser = createTempUser(requestParameters);

            User createdUser = WebSocketGlobalEnvironment.instance().getBackendController().createUser(tempUser);

            Response response = createResponse(createdUser);
            Utilities.sendStandardWebSocketResponse(session, response);
            session.close();
        }

        private void verify(RequestParameters requestParameters) throws Exception
        {
            if (StringUtilites.isVoidString(requestParameters.username))
                throw new NullPointerException("You must specify a username to create a new account");

            if (StringUtilites.isVoidString(requestParameters.password))
                throw new NullPointerException("You must specify a password to create a new account");
        }

        private User createTempUser(RequestParameters requestParameters)
        {
            User tempUser = new User();
            tempUser.username = requestParameters.username;
            tempUser.emailAddress = requestParameters.email;
            tempUser.password = requestParameters.password;

            return tempUser;
        }

        private Response createResponse(User createdUser)
        {
            Response response = new Response();
            response.success = true;
            response.username = createdUser.username;

            return response;
        }

    }

	
	/*
	 * Note: the selected user name may already exist. There should be a method on the BackendController
	 * that can detect if a user with the given username already exists
	 * -Futher note: Each user's username must be unique
	 */

    private class RequestParameters
    {
        /**
         * The Username.
         */
        public String username;
        /**
         * The Email.
         */
        public String email;
        /**
         * The Password.
         */
        public String password;
    }

    private class Response
    {
        /**
         * The Username.
         */
        public String username = "";
        /**
         * The Success.
         */
        public Boolean success = true;
    }
}
