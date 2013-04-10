package ikor.util.log;

/**
 * Standard logger
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */	
class StandardLogger implements Logger
{
	@Override
	public void error(String message) 
	{
		System.err.println("ERROR: "+message);			
	}

	@Override
	public void warning(String message) 
	{
		System.err.println("WARNING: "+message);
	}

	@Override
	public void info(String message) 
	{
		System.out.println("INFO: "+message);			
	}
}
