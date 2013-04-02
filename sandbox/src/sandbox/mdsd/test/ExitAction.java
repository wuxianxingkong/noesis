package sandbox.mdsd.test;

import sandbox.mdsd.ui.Action;

public class ExitAction implements Action 
{
	@Override
	public void run() 
	{
		System.exit(0);
	}

}
