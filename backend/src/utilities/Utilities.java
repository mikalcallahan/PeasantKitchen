package utilities;

import java.io.IOException;

import javax.websocket.Session;

public class Utilities 
{
	public static void sendErrorMessageToUser(Session session, Exception e)
	{
		try {
			session.getBasicRemote().sendText(formatWebSocketException(e));
		} catch (IOException e1) 
		{
			//If we cannot reach the remote user, print the stack trace of this
			//exception in the web server's log.
			e1.printStackTrace();
			logFailureToRespondToClient(e1);
		}
	}
	
	public static String formatStackTrace(StackTraceElement[] stackTrace)
	{
		StringBuilder stackTraceStr = new StringBuilder();
		
		if(stackTrace.length == 0)
			return "";
		
		stackTraceStr.append(stackTrace[0]);
		
		for(int index = 1; index < stackTrace.length; index++)
		{
			stackTraceStr.append("\n" + stackTrace[index]);
		}
		
		return stackTraceStr.toString();
	}
	
	public static String formatWebSocketException(Exception e)
	{
		StringBuilder exceptionMessage = new StringBuilder();
		
		return exceptionMessage.toString();
	}
	
	
	public static void sendJsonMessageToClient(Session session, String jsonMessage)
	{
		try {
			session.getBasicRemote().sendText(jsonMessage);
		}catch(Exception e)
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
		System.err.println("Stacktrace: [\n" + formatStackTrace(e.getStackTrace()) + "\n]");
	}
}
