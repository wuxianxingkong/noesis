package sandbox.mdsd.test;

import sandbox.mdsd.ui.Action;
import sandbox.mdsd.ui.Log;

public class LogAction implements Action 
{
	private String message;
	
	public LogAction (String message)
	{
		this.message = message;
	}
	
	@Override
	public void run() 
	{
		Log.info("Action - "+message);
	}

}
