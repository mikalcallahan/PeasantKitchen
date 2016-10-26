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
        
        /*
         * Servlets are the standard way to provide application logic that handles HTTP requests. 
         * Servlets are similar to a Jetty Handler except that the request object is not mutable 
         * and thus cannot be modified. Servlets are handled in Jetty by a ServletHandler. It uses standard path mappings to match a Servlet to a request; 
         * sets the requests servletPath and pathInfo; passes the request to the servlet, possibly via Filters to produce a response.
         */
        
        ServletContextHandler webSocketContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        webSocketContextHandler.setContextPath("/");
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { webSocketContextHandler, resource_handler }); //order matters here. 
        server.setHandler(handlers);													  //	The resource handler will respond to everything on its own, and will therefore cause the servlet context handler to be ignored!

////        //Create the web socket layer
////        ServerContainer webSocketContainer = WebSocketServerContainerInitializer.configureContext(webSocketContextHandler);
//        
//        
//        
//        
//        //SignInUser.class
//        
//        
//        webSocketContainer.addEndpoint(SignInUser.class);
//        webSocketContainer.addEndpoint(CreateNewUser.class);
        
        LinkedHashMap<String, Class<?>> endPoints = new LinkedHashMap<String, Class<?>>();
        endPoints.put(Constants.ContextPaths.User.create, CreateNewUser.class);
        endPoints.put(Constants.ContextPaths.User.signIn, SignInUser.class);
        endPoints.put(Constants.ContextPaths.User.signOut, SignUserOut.class);
        
        addEndpoints(webSocketContextHandler, endPoints);
        
        
        server.start();
        server.dump(System.err);
        server.join();
	}
	
	
	private static ServletContextHandler addEndpoints(ServletContextHandler rootContextHandler, LinkedHashMap<String, Class<?>> contextToEndpoint) throws Exception
	{
		Handler[] handlers = new Handler[contextToEndpoint.size()];
		int handlerIndex = 0;
		
		for(Entry<String, Class<?>> endpointContext : contextToEndpoint.entrySet())
			handlers[handlerIndex++] = buildContextHandler(endpointContext.getKey(), endpointContext.getValue());

		HandlerList subContextHandlers = new HandlerList();
		subContextHandlers.setHandlers(handlers);
		
		rootContextHandler.setHandler(subContextHandlers);
		
		return rootContextHandler;
	}
	
	private static ContextHandler buildContextHandler(String context, Class<?> endpoint) throws Exception
	{
        //Its websockethandeler
        //register your websocket handler
        //wrap in context handler


		
		return null;
	}
}
