package sandbox.mdsd.test;

import ikor.model.ui.Application;
import ikor.model.ui.Image;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

public class TestUIModel extends UIModel
{
	public TestUIModel (Application app)
	{
		super(app,"NOESIS");
		
		// setAlignment( UIModel.Alignment.ADJUST );
		// setAlignment( UIModel.Alignment.LEADING );
		// setAlignment( UIModel.Alignment.TRAILING );
		
		add( new Option("$exit", new ExitAction(app) ) );
		
		add( new Image("$icon", app.url("icon.gif") ) );
		
		add( new Image("$background", app.url("logo.gif") ) );
		
		add( new TestMenu(app));
	}
}
