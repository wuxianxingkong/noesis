package sandbox.mdsd.ui;

import sandbox.mdsd.Subject;

/**
 * Command design pattern: Base class for actions/commands.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public abstract class Action implements Runnable 
{
	private Subject context;

	/**
	 * Get action context.
	 * 
	 * @return the context
	 */
	public Subject getContext() 
	{
		return context;
	}

	/**
	 * Set action context.
	 * 
	 * @param context the context to set
	 */
	public void setContext(Subject context) 
	{
		this.context = context;
	}
}
