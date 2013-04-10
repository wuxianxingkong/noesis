package sandbox.mdsd.test;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.UIModel;
import ikor.util.log.Log;

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
