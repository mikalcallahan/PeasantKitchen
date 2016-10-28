package server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import constants.Constants;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import webSockets.user.create.CreateNewUser;
import webSockets.user.signin.SignInUser;
import webSockets.user.signout.SignOutUser;

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
        resource_handler.setWelcomeFiles(new String[]{ "index.htm" });
        resource_handler.setResourceBase(".");

        resource_handler.addBean(new Object());
        
        LinkedHashMap<String, Class<?>> endPoints = new LinkedHashMap<String, Class<?>>();
        endPoints.put(Constants.Contexts.User.create, CreateNewUser.class);
        endPoints.put(Constants.Contexts.User.signIn, SignInUser.class);
        endPoints.put(Constants.Contexts.User.signOut, SignOutUser.class);

        ArrayList<ContextHandler> contextHandlers = buildWebSocketHandlers(endPoints);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(makeHandlerList(contextHandlers, resource_handler));
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

	private static Handler[] makeHandlerList(ArrayList<ContextHandler> handlers, Handler handler)
    {
        Handler[] handlerArr = new Handler[handlers.size() + 1];

        for (int handlerIndex = 0; handlerIndex < handlers.size(); handlerIndex++) {
            handlerArr[handlerIndex] = handlers.get(handlerIndex);
        }

        handlerArr[handlers.size()] = handler;
        return handlerArr;
    }
}
