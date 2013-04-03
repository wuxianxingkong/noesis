package sandbox.mdsd.task;

import sandbox.mdsd.Context;

/**
 * Command design pattern: Base class for actions/commands.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public abstract class Action implements Runnable 
{
	private Context context;

	/**
	 * Get action context.
	 * 
	 * @return the context
	 */
	public Context getContext() 
	{
		return context;
	}

	/**
	 * Set action context.
	 * 
	 * @param context the context to set
	 */
	public void setContext(Context context) 
	{
		this.context = context;
	}
}
