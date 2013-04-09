package sandbox.mdsd.ui.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import sandbox.mdsd.ui.Action;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.UIFactory;

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
