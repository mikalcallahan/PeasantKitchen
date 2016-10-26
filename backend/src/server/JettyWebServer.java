package server;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlets.EventSourceServlet;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import constants.Constants;
import controllers.BackendController;
import framework.WebSocketGlobalEnvironment;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import webSockets.user.create.CreateNewUser;
import webSockets.user.signin.SignInUser;
import webSockets.user.signout.SignUserOut;

public class JettyWebServer 
{
	
	public static void main(String[] args) throws Exception
	{
		Server server = new Server();
		
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        connector.setHost("localhost");
        
        server.addConnector(connector);
        
        // Create the ResourceHandler. It is the object that will actually handle the request for a given file. It is
        // a Jetty Handler object so it is suitable for chaining with other handlers we well.
        ResourceHandler resource_handler = new ResourceHandler();
        
        // Configure the ResourceHandler. Setting the resource base indicates where the files should be served out of.
        // In this example it is the current directory but it can be configured to anything that the jvm has access to.
        resource_handler.setDirectoriesListed(true);
        
        //serve our index.html page
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        resource_handler.setResourceBase(".");
        
        LinkedHashMap<String, Class<?>> endPoints = new LinkedHashMap<String, Class<?>>();
        endPoints.put(Constants.ContextPaths.User.create, CreateNewUser.class);
        endPoints.put(Constants.ContextPaths.User.signIn, SignInUser.class);
        endPoints.put(Constants.ContextPaths.User.signOut, SignUserOut.class);

        ArrayList<ContextHandler> contextHandlers = buildWebSocketHandlers(endPoints);


        HandlerList handlers = new HandlerList();
        //handlers.setHandlers(new Handler[] { webSocketContextHandler, resource_handler }); //order matters here.
        handlers.setHandlers(new Handler[] { makeHandlerList(contextHandlers, resource_handler) });
        server.setHandler(handlers);													  //	The resource handler will respond to everything on its own, and will therefore cause the servlet context handler to be ignored!


        server.start();
        server.dump(System.err);
        server.join();
	}
	
	
	private static ArrayList<ContextHandler> buildWebSocketHandlers(LinkedHashMap<String, Class<?>> contextToEndpoint) throws Exception
	{
		ArrayList<ContextHandler> contextHandlers = new ArrayList<ContextHandler>();
		
		for(Entry<String, Class<?>> endpointContext : contextToEndpoint.entrySet())
            contextHandlers.add(buildContextHandler(endpointContext.getKey(), endpointContext.getValue()));
		
		return contextHandlers;
	}
	
	private static ContextHandler buildContextHandler(String contextPath, Class<?> endpoint) throws Exception
	{
        WebSocketHandler webSocketHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory webSocketServletFactory) {
                webSocketServletFactory.register(endpoint);
            }
        };

        ContextHandler context = new ContextHandler();
        context.setContextPath(contextPath);
        context.setHandler(webSocketHandler);

        return context;
	}

	private static Handler[] makeHandlerList(ArrayList<Handler> handlers, Handler handler)
    {
        Handler[] handlerArr = new Handler[handlers.size() + 1];

        for (int handlerIndex = 0; handlerIndex < handlers.size(); handlerIndex++) {
            handlerArr[handlerIndex] = handlers.get(handlerIndex);
        }

        handlerArr[handlers.size()] = handler;
        return handlerArr;
    }
}
