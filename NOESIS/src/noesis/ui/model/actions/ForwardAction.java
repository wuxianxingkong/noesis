package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.UIModel;


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
		target.getApplication().run(target);
	}

}
