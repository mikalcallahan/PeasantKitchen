package framework;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import constants.Constants;
import utilities.StringUtilites;
import utilities.Utilities;

import javax.websocket.Session;
import java.util.HashMap;


/**
 * The type Post web socket.
 */
public abstract class PostWebSocket
{
    /**
     * The Message handlers.
     */
    protected HashMap<String, WebSocketMessageHandler> messageHandlers = new HashMap<String, WebSocketMessageHandler>();

    /**
     * Instantiates a new Post web socket.
     */
    public PostWebSocket()
    {
        this.initialize();
    }

	/*
	 * abstract methods
	 */

    /**
     * Initialize boolean.
     *
     * @return the boolean
     */
//Adds all of the message handler for this web socket
    public abstract boolean initialize();


    /**
     * Handle messages.
     *
     * @param messageJson the message json
     * @param session     the session
     */
    protected void handleMessages(String messageJson, Session session)
    {
        try
        {
            handleCurrentMessage(messageJson, session);
        }
        catch (Exception e)
        {
            Utilities.sendExceptionDetailsToClient(session, e);
        }
    }

    private void handleCurrentMessage(String messageJson, Session session) throws Exception
    {
        Request request = parseRequestJson(messageJson);

        WebSocketMessageHandler messageHandler = this.messageHandlers.get(request.id);

        if (messageHandler == null)
            throw new NullPointerException("Sorry, but the backend cannot handle [" + request.id + "] messages yet!");

        messageHandler.handleMessage(request.payload, session);
    }

    private Request parseRequestJson(String messageJson)
    {
        if (messageJson == null || messageJson.isEmpty())
            throw new NullPointerException("ERROR: No request JSON!");

        JsonParser jsonParser = new JsonParser();

        JsonObject rootObject = jsonParser.parse(messageJson).getAsJsonObject();

        //let's make sure that the expected fields are present
        if (rootObject.get(Constants.PostWebSocketRequestKeys.id) == null || rootObject.get(Constants.PostWebSocketRequestKeys.payload) == null)
            throw new NullPointerException("ERROR: Requests to backend web sockets require a JSON object with id and payload fields...mumble...mumble...");

        String id = rootObject.get(Constants.PostWebSocketRequestKeys.id).getAsString();
        JsonObject payload = rootObject.get(Constants.PostWebSocketRequestKeys.payload).getAsJsonObject();

        return new Request(id, payload);
    }

    /**
     * Payload contains required fields.
     *
     * @param payload the payload
     * @param fields  the fields
     */
    protected void payloadContainsRequiredFields(JsonObject payload, String... fields)
    {
        JsonElement fieldElement = null;

        for (String field : fields)
        {
            fieldElement = payload.get(field);

            if (fieldElement == null)
            {
                throw new NullPointerException("ERROR: The required parameter [" + field + "] was not recieved by the backend!");
            }

            if (StringUtilites.isVoidString(fieldElement.getAsString()))
                throw new NullPointerException("ERROR: Please supply a value for the parameter [" + field + "]");
        }
    }

    private class Request
    {
        /**
         * The Id.
         */
        public String id;
        /**
         * The Payload.
         */
        public JsonObject payload;

        /**
         * Instantiates a new Request.
         *
         * @param id      the id
         * @param payload the payload
         */
        public Request(String id, JsonObject payload)
        {
            super();
            this.id = id;
            this.payload = payload;
        }
    }
}
