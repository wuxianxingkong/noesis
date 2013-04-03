package sandbox.mdsd.test;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.task.Action;
import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.UIModel;

public class ExitAction extends Action 
{
	private Application app;
	private UIModel model;

	public ExitAction (Application app)
	{
		this.app = app;
	}	
	
	public ExitAction (UIModel model)
	{
		this.app = model.getApplication();
		this.model = model;
	}
	
	@Override
	public void run() 
	{
		Log.info("Exit"+((model!=null)?" - "+model:""));
		
		if (model==null)
			System.exit(0);
		else
			app.exit(model);
			
	}

}
