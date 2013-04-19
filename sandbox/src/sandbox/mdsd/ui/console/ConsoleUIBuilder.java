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

	@Override
	public void message(String title, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean confirm(String title, String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void open(String url) {
		// TODO Auto-generated method stub
		
	}
	

}
