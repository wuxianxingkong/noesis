package ikor.model.ui.swing;

import ikor.model.ui.Action;
import ikor.util.log.Log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
