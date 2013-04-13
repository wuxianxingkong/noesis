package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;

public class URLAction extends Action 
{
	private Application app;
	private String target;
	
	public URLAction (Application app, String target)
	{
		this.app = app;
		this.target = target;
	}
	
	@Override
	public void run() 
	{
		app.open(target);
	}

}
