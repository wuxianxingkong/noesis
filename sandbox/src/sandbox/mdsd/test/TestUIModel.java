package sandbox.mdsd.test;

import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.Image;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.UIModel;

public class TestUIModel extends UIModel
{
	public TestUIModel (Application app)
	{
		super(app,"NOESIS");
		
		// setAlignment( UIModel.Alignment.ADJUST );
		// setAlignment( UIModel.Alignment.LEADING );
		// setAlignment( UIModel.Alignment.TRAILING );
		
		add( new Option("$exit", new ExitAction(app) ) );
		
		add( new Image("$icon", TestApplication.url("icon.gif") ) );
		
		add( new Image("$background", TestApplication.url("logo.gif") ) );
		
		add( new TestMenu(app));
	}
}
