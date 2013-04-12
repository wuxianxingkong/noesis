package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.util.log.Log;

public class LogAction extends Action 
{
	private String message;
	
	public LogAction (String message)
	{
		this.message = message;
	}
	
	@Override
	public void run() 
	{
		Log.info("Action - "+message);
	}

}
