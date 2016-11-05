package server;

import com.sun.appserv.server.LifecycleEvent;
import com.sun.appserv.server.LifecycleListener;
import com.sun.appserv.server.ServerLifecycleException;

import framework.WebSocketGlobalEnvironment;

public class ServerStartup implements LifecycleListener 
{
	@Override
	public void handleEvent(LifecycleEvent event) throws ServerLifecycleException 
	{
		if(event.getEventType() == LifecycleEvent.STARTUP_EVENT)
			startupBackend(event);
	}
	
	private LifecycleEvent startupBackend(LifecycleEvent event)
	{
		//perform any nessessary initialization of the backend here
		
		return event;
	}
}
