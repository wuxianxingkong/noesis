package sandbox.mdsd.ui.console;

import ikor.model.ui.UI;
import ikor.model.ui.UIModel;

public class ConsoleUI implements UI 
{
	UIModel context;
	
	public ConsoleUI (UIModel context)
	{
		this.context = context;
	}

	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void exit()
	{
		System.exit(0);
	}
	

}
