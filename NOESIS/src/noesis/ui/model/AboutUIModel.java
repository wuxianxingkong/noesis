package noesis.ui.model;

import ikor.model.ui.Application;
import ikor.model.ui.Image;
import ikor.model.ui.Label;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;
import ikor.model.ui.UIModel;

import noesis.ui.model.actions.ExitAction;

public class AboutUIModel extends UIModel
{
	public AboutUIModel (Application app)
	{
		super(app, "About NOESIS...");
		
		setIcon( app.url("icon.gif") );
		
		add( new Image("logo", app.url("logo.gif") ) );

		add( new Separator () );
		
		add( new Label("<html><h1>NOESIS</h1><b>Network-Oriented Exploration, Simulation, and Induction System</b></html>") );
		add( new Label("<html>&copy; Fernando Berzal (<a href=\"mailto:berzal@acm.org\">berzal@acm.org</a>)"
				+ " &amp; V&iacute;ctor Mart&iacute;nez (<a href=\"mailto:victormg@acm.org\">victormg@acm.org</a>),"
				+ "<br/> with contributions by Aar&oacute;n Rosas and Francisco Javier Gij&oacute;n.</html>") );
		
		add ( new Separator() );
		
		Option ok = new Option("");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		add( ok );	
	}
}
