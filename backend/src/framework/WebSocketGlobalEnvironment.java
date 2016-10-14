package framework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.BackendController;

public class WebSocketGlobalEnvironment 
{
	private static WebSocketGlobalEnvironment environment;
	
	public Gson jsonConverter = new GsonBuilder().create();
	public BackendController backendController;
	//ResouceHandler class [maybe using jetty's. This handler manages files on the local file system]
	
	private WebSocketGlobalEnvironment()
	{
		
	}
	
	public static WebSocketGlobalEnvironment instance()
	{
		if(WebSocketGlobalEnvironment.environment == null)
			WebSocketGlobalEnvironment.environment = new WebSocketGlobalEnvironment();
		
		return WebSocketGlobalEnvironment.environment;
	}
}