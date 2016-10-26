package webSockets.testing;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import webSockets.user.signin.SignInUser;

public class TestWebSocket {
	
	public static void main(String[] args)
	{
//		String destUri = "ws://echo.websocket.org";
//        if (args.length > 0)
//        {
//            destUri = args[0];
//        }
//
//        WebSocketClient client = new WebSocketClient();
//        SignInUser socket = new SignInUser();
//        try
//        {
//            client.start();
//
//            URI echoUri = new URI(destUri);
//            ClientUpgradeRequest request = new ClientUpgradeRequest();
//            client.connect(socket,echoUri,request);
//            System.out.printf("Connecting to : %s%n",echoUri);
//
//            // wait for closed socket connection.
//            socket.awaitClose(5,TimeUnit.SECONDS);
//        }
//        catch (Throwable t)
//        {
//            t.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                client.stop();
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
	}

}
