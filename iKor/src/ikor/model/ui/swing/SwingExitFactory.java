package ikor.model.ui.swing;

import ikor.model.ui.Action;
import ikor.model.ui.Option;
import ikor.model.ui.UIFactory;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class SwingExitFactory implements UIFactory<SwingUI,Option>
{

	@Override
	public void build(SwingUI ui, Option option) 
	{
		ui.addWindowListener(new WindowHandler(option.getAction()));		
	}

	
	// Event handling
	
	class WindowHandler extends WindowAdapter
	{
		Action action;
		
		public WindowHandler (Action action)
		{
			this.action = action;
		}

		public void windowClosing (WindowEvent e)
		{ 
			action.run();
		}
	}
	
}
