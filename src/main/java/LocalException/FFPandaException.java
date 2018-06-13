package LocalException;

import org.apache.log4j.Logger;

public class FFPandaException extends Exception{
    private String message;
    private StringBuffer messageB;
    public FFPandaException(String errmessage)
    {
        message = errmessage;
        System.out.println("FFPandaException occurs:(String): " + this.getClass() + " :: ");
    }
    public FFPandaException(StringBuffer errmessage)
    {
        messageB = errmessage;
        System.out.println("FFPandaException occurs:(StringBuffer): " + this.getClass() + " :: ");
    }

	public FFPandaException(String errmessage, Logger theLog)
	{
        message = errmessage;
        theLog.error(message);
	}


    public FFPandaException(StringBuffer errmessage, Logger theLog)
    {
        messageB = errmessage;
        theLog.error(message);
    }

    public String getMessage()
    {
        return message;
    }

    public void getMessage(Throwable cause, Logger theLogger) {
        message = cause.toString();
        theLogger.error(message);
    }

    public void getClassError(String cause, Logger theLogger) {
        message = cause;
        theLogger.error(message);
    }
}