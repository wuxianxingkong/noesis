package sandbox.mdsd.test;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.task.Action;
import sandbox.mdsd.ui.Component;

public class LogStateAction extends Action 
{
	private Component component;
	
	public LogStateAction (Component component)
	{
		this.component = component;
	}
	
	@Override
	public void run() 
	{
		Log.info(component.toString());
	}

}