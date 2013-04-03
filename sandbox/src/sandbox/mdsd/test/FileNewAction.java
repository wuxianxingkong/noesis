package sandbox.mdsd.test;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.task.Action;

public class FileNewAction extends Action 
{
	@Override
	public void run() 
	{
		Log.info("New...");

	}

}
