package webSockets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface BackendWebSocketInterface<Request, Result> 
{	
	public abstract Request convert(String clientRequest) throws Exception;
	public abstract void verify(Request clientRequestObject) throws Exception;
	public abstract Result process(Request clientRequestObject) throws Exception;
	public abstract void formatAndSendResponse(Result result) throws Exception;
}
