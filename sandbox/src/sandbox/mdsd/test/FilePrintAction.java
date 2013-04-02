package sandbox.mdsd.test;

import sandbox.mdsd.ui.Action;
import sandbox.mdsd.ui.Log;

public class FilePrintAction implements Action 
{
	@Override
	public void run() 
	{
		Log.info("Print...");
	}

}
