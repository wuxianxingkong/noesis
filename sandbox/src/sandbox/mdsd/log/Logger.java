package sandbox.mdsd.log;

/**
 * Logger interface (inspired by the Javascript console).
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public interface Logger 
{
	/**
	 * Log error.
	 * 
	 * @param messsage Error message
	 */
	public void error (String message);
	
	/**
	 * Log warning.
	 * 
	 * @param message Warning message
	 */
	public void warning (String message);
	
	/**
	 * Log information.
	 * 
	 * @param message Informational message
	 */
	public void info (String message);

}