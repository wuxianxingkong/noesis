package sandbox.mdsd.test;

import ikor.model.ui.Action;
import ikor.model.ui.UIModel;
import ikor.util.log.Log;


public class ForwardAction extends Action 
{
	private UIModel target;
	
	public ForwardAction (UIModel target)
	{
		this.target = target;
	}
	
	@Override
	public void run() 
	{
		Log.info("Forward - "+target);
		target.getApplication().run(target);
	}

}
