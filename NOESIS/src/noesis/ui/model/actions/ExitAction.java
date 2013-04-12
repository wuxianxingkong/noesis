package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.UIModel;


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
		if (model==null)
			System.exit(0);
		else
			app.exit(model);
	}

}
