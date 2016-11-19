package constants;

import java.util.ArrayList;
import java.io.File;

import framework.AddnSet;

public class Constants 
{
	public static final String usersFileName = "users.obj";
	public static final String recipiesFileName = "recipies.obj";
	
	public static final File webSocketTestingDir = new File("/home/stoffel/Documents/School/Software Engineering/SemesterProject/PeasantKitchen/backend/testing/web sockets/");
	public static final File applicationDataFolder = getApplicationDataFolder();

	private static File getApplicationDataFolder()
	{
		String glassfishDomainConfigFolder = System.getProperty("com.sun.aas.instanceRoot");

		if(glassfishDomainConfigFolder == null)
			return new File(".");
		else
			return new File(glassfishDomainConfigFolder).getParentFile();
	}
	
	
	public static class Contexts
	{
		public static AddnSet<String> all = new AddnSet<String>();
		
		public static class User
		{
			private static final String user = "/user";
			
			public static final String signIn = all.put(user + "/signin");
			public static final String signOut = all.put(user + "/signout");
			public static final String create = all.put(user + "/create");
		}
		
		
//		private String descendingContextPath(ArrayList<String> contexts)
//		{
//			StringBuilder absolutePath = new StringBuilder();
//
//			for(String uri : contexts)
//				absolutePath.append("/" + uri);
//
//			return absolutePath.toString();
//		}
	}
	
	public static class PostWebSocketRequestKeys
	{
		public static final String id = "id";
		public static final String payload = "payload";
	}
	
	public static class MessageIDs
	{
		public static final String signInUser = "user.signin";
		public static final String signOutUser = "user.signout";
		public static final String createNewUser = "user.create";
	}
	
	public static class StandardResponseObjectKeys
	{
		public static AddnSet<String> keys = new AddnSet<String>();
		
		public static final String response = keys.put("response");
		public static final String error = keys.put("error");
	}
	
	public static class ExceptionMessageKeys
	{
		public static AddnSet<String> keys = new AddnSet<String>();
		
		public static final String type = keys.put("type");
		public static final String message = keys.put("message");
		public static final String stackTrace = keys.put("stackTrace");
	}
}
