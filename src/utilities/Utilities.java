package utilities;

import com.google.gson.Gson;
import constants.Constants;
import framework.WebSocketGlobalEnvironment;

import javax.websocket.Session;
import java.util.LinkedHashMap;

/**
 * The type Utilities.
 */
public class Utilities
{
    /**
     * Send standard web socket response.
     *
     * @param session        the session
     * @param responseObject the response object
     */
    public static void sendStandardWebSocketResponse(Session session, Object responseObject)
    {
        sendStandardWebSocketResponse(session, responseObject, null);
    }

    /**
     * Send standard web socket response.
     *
     * @param session        the session
     * @param responseObject the response object
     * @param errorObject    the error object
     */
    public static void sendStandardWebSocketResponse(Session session, Object responseObject, Object errorObject)
    {
        if (responseObject == null)
            responseObject = "";
        if (errorObject == null)
            errorObject = "";

        Gson jsonConverter = WebSocketGlobalEnvironment.instance().getJsonConverter();

        LinkedHashMap<String, String> standardResponseObject = new LinkedHashMap<String, String>();
        standardResponseObject.put(Constants.StandardResponseObjectKeys.response, jsonConverter.toJson(responseObject));
        standardResponseObject.put(Constants.StandardResponseObjectKeys.error, jsonConverter.toJson(errorObject));

        //Convert our response map into a JSON object
        String json = jsonConverter.toJson(standardResponseObject);

        //Send the response to the client
        Utilities.sendJsonMessageToClient(session, json);
    }

    /**
     * Send exception details to client.
     *
     * @param session the session
     * @param e       the e
     */
    public static void sendExceptionDetailsToClient(Session session, Exception e)
    {
        LinkedHashMap<String, String> errorObject = new LinkedHashMap<String, String>();

        errorObject.put(Constants.ExceptionMessageKeys.type, e.getClass().getSimpleName());
        errorObject.put(Constants.ExceptionMessageKeys.message, e.getMessage());
        errorObject.put(Constants.ExceptionMessageKeys.stackTrace, Utilities.stackTraceToString(e.getStackTrace()));

        Utilities.sendStandardWebSocketResponse(session, null, errorObject);
    }

    /**
     * Stack trace to string string.
     *
     * @param stackTrace the stack trace
     * @return the string
     */
    public static String stackTraceToString(StackTraceElement[] stackTrace)
    {
        if (stackTrace.length == 0)
            return "";

        StringBuilder stackTraceStr = new StringBuilder();

        stackTraceStr.append(stackTrace[0]);
        for (int index = 1; index < stackTrace.length; index++)
            stackTraceStr.append("\n" + stackTrace[index]);

        return stackTraceStr.toString();
    }

    /**
     * Send json message to client.
     *
     * @param session     the session
     * @param jsonMessage the json message
     */
    public static void sendJsonMessageToClient(Session session, String jsonMessage)
    {
        try
        {
            session.getBasicRemote().sendText(jsonMessage);
        }
        catch (Exception e)
        {
            //Failed to contact user.
            logFailureToRespondToClient(e);
        }
    }

    private static void logFailureToRespondToClient(Exception e)
    {
        System.err.println("The backend failed to deliver the following exception to the user because the user was unreachable.");
        System.err.println("Exception: ");
        System.err.println("Message: [" + e.getMessage() + "]");
        System.err.println("Stacktrace: [\n" + Utilities.stackTraceToString(e.getStackTrace()) + "\n]");
    }
}
