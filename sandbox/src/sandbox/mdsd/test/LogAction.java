package sandbox.mdsd.test;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.task.Action;

public class LogAction extends Action 
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
