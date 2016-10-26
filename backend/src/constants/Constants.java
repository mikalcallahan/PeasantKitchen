package constants;

import java.util.ArrayList;

import framework.AddnSet;

public class Constants 
{
	public static class ContextPaths
	{
		public static final AddnSet<String> all = new AddnSet<String>();
		
		public static class User
		{
			private static final String user = "/user";
			
			public static final String signIn = all.put(user + "/signin");
			public static final String signOut = all.put(user + "/signout");
			public static final String create = all.put(user + "/create");
		}
		
		
		private String descendingContextPath(ArrayList<String> contexts)
		{
			StringBuilder absolutePath = new StringBuilder();
			
			for(String uri : contexts)
				absolutePath.append("/" + uri);
			
			return absolutePath.toString();
		}
	}
	
	public static class PostWebSocketRequestKeys
	{
		public static final String id = "id";
		public static final String payload = "payload";
	}
	
	public static class MessageHandlerIDs
	{
		public static final String signIn = "/user/signin";
	}
	
	public static class StandardResponseObjectKeys
	{
		public static final AddnSet<String> keys = new AddnSet<String>();
		
		public static final String response = keys.put("response");
		public static final String error = keys.put("error");
	}
	
	public static class ExceptionMessageKeys
	{
		public static final AddnSet<String> keys = new AddnSet<String>();
		
		public static final String type = keys.put("type");
		public static final String message = keys.put("message");
		public static final String stackTrace = keys.put("stackTrace");
	}
}
