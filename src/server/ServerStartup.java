package server;

import java.io.File;

import com.sun.appserv.server.LifecycleEvent;
import com.sun.appserv.server.LifecycleListener;
import com.sun.appserv.server.ServerLifecycleException;

import constants.Constants;
import framework.ApplicationData;
import framework.WebSocketGlobalEnvironment;

public class ServerStartup implements LifecycleListener 
{
	@Override
	public void handleEvent(LifecycleEvent event) throws ServerLifecycleException 
	{
		if(event.getEventType() == LifecycleEvent.STARTUP_EVENT)
			startupBackend(event);
		else if(event.getEventType() == LifecycleEvent.SHUTDOWN_EVENT)
			shutdownBackend(event);
	}
	
	private LifecycleEvent startupBackend(LifecycleEvent event)
	{
		//Set the parent directory of the application data folder
		try {
			File applicationDataParentFolder = initalizeApplicationDataStorageParentFolder();
			WebSocketGlobalEnvironment.instance().getApplicationData().loadFromDisk(applicationDataParentFolder);
		}catch(Exception e)
		{
			System.err.println("Failed to load the application data from disk");
			e.printStackTrace(System.err);
		}
		
		return event;
	}
	
	private LifecycleEvent shutdownBackend(LifecycleEvent event)
	{
		try 
		{
			File applicationDataParentFolder = initalizeApplicationDataStorageParentFolder();
			WebSocketGlobalEnvironment.instance().getApplicationData().saveToDisk(applicationDataParentFolder);
		} catch (Exception e) 
		{
			System.err.println("Failed to save the application data to disk");
			e.printStackTrace();
		}
		
		return event;
	}
	
	private File initalizeApplicationDataStorageParentFolder()
	{
		File appDataStorageParent = new File(getGlassfishDomainFolder(), "ApplicationDataStorage");
		
		if(!appDataStorageParent.exists())
			appDataStorageParent.mkdirs();
		
		return appDataStorageParent;
	}
	
	//example: c:/glassfish3/glassfish/domains/domain1/config
	//We're not interested in the config folder, so return that folder's parent, which is the
	//domain folder we're interested in
	
	private File getGlassfishDomainFolder()
	{
		return new File(System.getProperty("com.sun.aas.instanceRoot")).getParentFile(); 
	}
}
