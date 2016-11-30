package logging;

public class Logger
{
    public void requiredParameterMissing(String message)
    {
        this.error(message);
    }

    public void error(String errorMessage)
    {
        //this.log(MessageCatagory.ERROR, errorMessage);
        throw new RuntimeException(MessageCatagory.ERROR.toString() + ": " + errorMessage);
    }

    public void warning(String warningMessage)
    {
        this.log(MessageCatagory.WARNING, warningMessage);
    }

    public void debug(String debugMessage)
    {
        this.log(MessageCatagory.DEBUG, debugMessage);
    }

    protected void log(MessageCatagory catagory, String message)
    {
        System.err.println(catagory.toString() + ": " + message);
    }

    private enum MessageCatagory
    {
        ERROR, WARNING, DEBUG
    }
}
