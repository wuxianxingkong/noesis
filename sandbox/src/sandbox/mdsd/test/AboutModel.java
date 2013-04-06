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
		
		setIcon( TestApplication.url("icon.gif") );
		
		add( new Image("logo", TestApplication.url("logo.gif") ) );

		add( new Separator () );
		
		add( new Label("<html><center><b>NOESIS</b></center></html>") );
		add( new Label("Network-Oriented Exploration, Simulation, and Induction System") );
		add( new Label("<html><center>&copy; Fernando Berzal (berzal@acm.org)</center></html>") );
		
		add ( new Separator() );
		
		Option ok = new Option("");
		ok.setIcon( TestApplication.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		add( ok );
		
		
	}
}
