package sandbox.mdsd.test;

import ikor.model.ui.Application;
import ikor.model.ui.Image;
import ikor.model.ui.Label;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;
import ikor.model.ui.UIModel;

public class AboutModel extends UIModel
{
	public AboutModel (Application app)
	{
		super(app, "About NOESIS...");
		
		setIcon( app.url("icon.gif") );
		
		add( new Image("logo", app.url("logo.gif") ) );

		add( new Separator () );
		
		add( new Label("<html><center><b>NOESIS</b></center></html>") );
		add( new Label("Network-Oriented Exploration, Simulation, and Induction System") );
		add( new Label("<html><center>&copy; Fernando Berzal (berzal@acm.org)</center></html>") );
		
		add ( new Separator() );
		
		Option ok = new Option("");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		add( ok );
		
		
	}
}
