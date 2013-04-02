package sandbox.mdsd.ui.console;

import sandbox.mdsd.ui.UIModel;
import sandbox.mdsd.ui.UI;
import sandbox.mdsd.ui.UIBuilder;

public class ConsoleUIBuilder extends UIBuilder 
{

	@Override
	public UI build(UIModel context) 
	{
		return new ConsoleUI(context);
	}
	

}
