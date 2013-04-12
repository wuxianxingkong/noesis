package noesis.ui.model.actions;

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
		if (target!=null)
			target.getApplication().run(target);
		else
			Log.error("ForwardAction - Unavailable target.");
	}

}
