package server;

import com.sun.appserv.server.LifecycleEvent;
import com.sun.appserv.server.LifecycleListener;
import com.sun.appserv.server.ServerLifecycleException;
import framework.WebSocketGlobalEnvironment;

/**
 * The type Server startup.
 */
public class ServerStartup implements LifecycleListener
{
    @Override
    public void handleEvent(LifecycleEvent event) throws ServerLifecycleException
    {
        if (event.getEventType() == LifecycleEvent.STARTUP_EVENT)
            startupBackend(event);
        else if (event.getEventType() == LifecycleEvent.SHUTDOWN_EVENT)
            shutdownBackend(event);
    }

    private LifecycleEvent startupBackend(LifecycleEvent event)
    {
        return WebSocketGlobalEnvironment.instance().getBackendController().serverStartupTasks(event);
    }

    private LifecycleEvent shutdownBackend(LifecycleEvent event)
    {
        return WebSocketGlobalEnvironment.instance().getBackendController().serverShutdownTasks(event);
    }
}
