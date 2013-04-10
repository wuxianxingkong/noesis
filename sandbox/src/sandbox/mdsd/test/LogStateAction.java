package sandbox.mdsd.test;

import ikor.model.ui.Action;
import ikor.model.ui.Component;
import ikor.util.log.Log;

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