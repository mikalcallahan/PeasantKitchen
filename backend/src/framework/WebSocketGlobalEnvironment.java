package framework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.BackendController;

public class WebSocketGlobalEnvironment 
{
	private static WebSocketGlobalEnvironment environment;
	
	private Gson jsonConverter;
	private BackendController backendController;
	//ResouceHandler class [maybe using jetty's. This handler manages files on the local file system]
	
	private WebSocketGlobalEnvironment()
	{
		this.backendController = new BackendController();
		this.jsonConverter = new GsonBuilder().create();
	}
	
	public static WebSocketGlobalEnvironment instance()
	{
		if(WebSocketGlobalEnvironment.environment == null)
			WebSocketGlobalEnvironment.environment = new WebSocketGlobalEnvironment();
		
		return WebSocketGlobalEnvironment.environment;
	}
	
	public BackendController getBackendController()
	{
		return this.backendController;
	}
	
	public Gson getJsonConverter()
	{
		return this.jsonConverter;
	}
}