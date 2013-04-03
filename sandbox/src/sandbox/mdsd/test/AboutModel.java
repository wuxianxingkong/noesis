package sandbox.mdsd.test;

import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.Image;
import sandbox.mdsd.ui.Label;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.Separator;
import sandbox.mdsd.ui.UIModel;

public class AboutModel extends UIModel
{
	public AboutModel (Application app)
	{
		super(app, "About NOESIS...");
		
		add( new Image("$icon", TestApplication.url("icon.gif") ) );
		
		add( new Image("logo", TestApplication.url("logo.gif") ) );

		add( new Separator () );
		
		add( new Label("NOESIS") );
		add( new Label("Network-Oriented Exploration, Simulation, and Induction System") );
		
		add ( new Separator() );
		
		Option ok = new Option("");
		ok.setIcon( TestApplication.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		add( ok );
		
		
	}
}
