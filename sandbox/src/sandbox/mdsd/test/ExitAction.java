package sandbox.mdsd.test;

import sandbox.mdsd.ui.Action;
import sandbox.mdsd.ui.Log;

public class ExitAction implements Action 
{
	@Override
	public void run() 
	{
		Log.info("Exit");		
		System.exit(0);
	}

}
