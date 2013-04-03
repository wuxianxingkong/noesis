package sandbox.mdsd.test;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.task.Action;

import sandbox.mdsd.ui.UIModel;

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
