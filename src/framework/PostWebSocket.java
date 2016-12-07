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
    protected HashMap<String, WebSocketMessageHandler> messageHandlers = new HashMap<String, WebSocketMessageHandler>();

    public PostWebSocket()
    {
        this.initialize();
    }

	/*
	 * Abstract methods
	 */

    /**
     * Adds all of the message handler for this web socket
     *
     * @return the boolean
     */
    public abstract boolean initialize();


    /**
     * In each reqest, the user must specify which message handler they would like to use. This method
     * extracts the message handler ID, and uses that as a key into a hashmap to find the appropriate handler.
     * Uses the command design pattern.
     *
     * @param messageJson the json of the message
     * @param session     the web socket session
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

    /**
     * Delegates this request to the requested message handler.
     * @param messageJson
     * @param session
     * @throws Exception
     */

    private void handleCurrentMessage(String messageJson, Session session) throws Exception
    {
        Request request = parseRequestJson(messageJson);

        WebSocketMessageHandler messageHandler = this.messageHandlers.get(request.id);

        if (messageHandler == null)
            throw new NullPointerException("Sorry, but the backend cannot handle [" + request.id + "] messages yet!");

        messageHandler.handleMessage(request.payload, session);
    }

    /**
     * Extracts the message ID and the request object.
     * @param messageJson
     * @return
     */

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
     * Verifies that the payload contains the required fields 'payload' and 'id' (the ID of the message handler to use)
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
                throw new NullPointerException("ERROR: The required parameter [" + field + "] was not received by the backend!");
            }

            if (StringUtilites.isVoidString(fieldElement.getAsString()))
                throw new NullPointerException("ERROR: Please supply a value for the parameter [" + field + "]");
        }
    }

    /**
     * Models the request object's form. Needed for GSON.
     */

    private class Request
    {
        public String id;
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
