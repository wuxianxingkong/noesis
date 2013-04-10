package sandbox.mdsd.ui.console;

import ikor.model.ui.UI;
import ikor.model.ui.UIBuilder;
import ikor.model.ui.UIModel;

public class ConsoleUIBuilder extends UIBuilder 
{

	@Override
	public UI build(UIModel context) 
	{
		return new ConsoleUI(context);
	}
	

}
