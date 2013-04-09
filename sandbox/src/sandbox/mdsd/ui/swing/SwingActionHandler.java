package sandbox.mdsd.ui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.ui.Action;

class SwingActionHandler implements ActionListener
{
	Action action;
	
	public SwingActionHandler (Action action)
	{
		this.action = action;
	}

	@Override
	public void actionPerformed (ActionEvent e)
	{ 
		if (action!=null)
			action.run();
		else
			Log.warning( "Attempt to execute null action - " + e );
	}
}	
