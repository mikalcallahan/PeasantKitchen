package logging;

/**
 * The type Logger.
 */
public class Logger
{
    /**
     * Required parameter missing.
     *
     * @param message the message
     */
    public void requiredParameterMissing(String message)
    {
        this.error(message);
    }

    /**
     * Error.
     *
     * @param errorMessage the error message
     */
    public void error(String errorMessage)
    {
        //this.log(MessageCatagory.ERROR, errorMessage);
        throw new RuntimeException(MessageCatagory.ERROR.toString() + ": " + errorMessage);
    }

    /**
     * Warning.
     *
     * @param warningMessage the warning message
     */
    public void warning(String warningMessage)
    {
        this.log(MessageCatagory.WARNING, warningMessage);
    }

    /**
     * Debug.
     *
     * @param debugMessage the debug message
     */
    public void debug(String debugMessage)
    {
        this.log(MessageCatagory.DEBUG, debugMessage);
    }

    /**
     * Log.
     *
     * @param catagory the catagory
     * @param message  the message
     */
    protected void log(MessageCatagory catagory, String message)
    {
        System.err.println(catagory.toString() + ": " + message);
    }

    private enum MessageCatagory
    {
        ERROR, WARNING, DEBUG
    }
}
