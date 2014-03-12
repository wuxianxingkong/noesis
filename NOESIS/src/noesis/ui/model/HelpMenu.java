package noesis.ui.model;

import java.awt.event.KeyEvent;

import noesis.ui.model.actions.ForwardAction;
import noesis.ui.model.actions.URLAction;

import ikor.model.ui.Application;
import ikor.model.ui.Menu;
import ikor.model.ui.Option;


public class HelpMenu extends Menu 
{
	public HelpMenu (Application app)
	{
		super("Help");
		
		setIcon( app.url("icons/search.png") );
		
		//Option tutorial = new Option("Tutorial", new URLAction(app,"http://noesis.ikor.org/") );
		//tutorial.setIcon( app.url("icons/tutor.png") );
		//add(tutorial);

		Option web = new Option("Project web page", new URLAction(app,"http://noesis.ikor.org/"), KeyEvent.VK_F1 );
		web.setIcon( app.url("icons/docs.png") );
		add(web);
		
		//Option config = new Option("Configuration...", new ForwardAction(null), KeyEvent.VK_F9 );
		//config.setIcon( app.url("icons/config.png") );
		//add( config );
		

		Option about = new Option("About...", new ForwardAction( new AboutUIModel(app) ) );
		about.setIcon( app.url("icons/info.png") );
		add(about);
	}

}
