package noesis.ui.model.actions;

import ikor.model.ui.Action;

import ikor.util.log.Log;

public class URLAction extends Action 
{
	private String target;
	
	public URLAction (String target)
	{
		this.target = target;
	}
	
	@Override
	public void run() 
	{
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(target));
		} catch (java.io.IOException e) {
			Log.error("URLAction: "+e.getMessage());
		}
	}

}
