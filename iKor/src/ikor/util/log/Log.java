package ikor.util.log;

/**
 * Logging service (as Javascript console)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class Log 
{
	private static Logger logger = new StandardLogger();
	
	/**
	 * Log error.
	 * 
	 * @param messsage Error message
	 */	
	public static void error (String message)
	{
		logger.error(message);
	}

	/**
	 * Log warning.
	 * 
	 * @param message Warning message
	 */	
	public static void warning (String message)
	{
		logger.warning(message);
	}

	/**
	 * Log information.
	 * 
	 * @param message Informational message
	 */	
	public static void info (String message)
	{
		logger.info(message);
	}
	
	// Logger instance

	/**
	 * Get logger.
	 * 
	 * @return the logger
	 */
	public static Logger getLogger() 
	{
		return logger;
	}

	/**
	 * Set logger.
	 * 
	 * @param logger the logger to set
	 */
	public static void setLogger(Logger logger) 
	{
		Log.logger = logger;
	}

	
}
