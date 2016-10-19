package logging;

import java.io.PrintStream;

public class Logger 
{
	private PrintStream stream;
	
	private enum MessageCatagory {ERROR, WARNING, DEBUG}
	
	public Logger()
	{
		this.stream = System.err;
	}
	
	public Logger(PrintStream stream)
	{
		this.stream = stream;
	}
	
	public void logAsException(String message)
	{
		//get the calling class [via reflections]
		String callingClass = "";
		
		
	}
	
	public void logAsException(Exception e)
	{
		
	}
	
	
	public void error(String errorMessage)
	{
		
	}
	
	public void warning(String warningMessage)
	{
		
	}
	
	public void debug(String debugMessage)
	{
		
	}
	
	
	protected void logMessage(MessageCatagory catagory, String message)
	{
		
	}
}
