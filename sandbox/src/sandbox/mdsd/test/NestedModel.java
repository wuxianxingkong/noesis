package sandbox.mdsd.test;

import ikor.model.ui.Application;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

public class NestedModel extends UIModel
{
	public NestedModel (Application app)
	{
		super(app, "Nested controls...");
		
		// Nested panels
		
		UIModel panel = new UIModel(app, "Panels...");

		panel.setAlignment( UIModel.Alignment.LEADING );
		
		panel.add( new ListModel(app) );
		panel.add( new ViewerModel(app) );
		
		add(panel);
		
		// Button panel
		
		UIModel buttons = new UIModel(app, "Buttons...");
		
		buttons.setAlignment( UIModel.Alignment.ADJUST );
		
		buttons.add ( createButton (app, "OK") );
		buttons.add ( createButton (app, "Cancel") );
		buttons.add ( createButton (app, "Help") );
		
		add(buttons);
		
	}
	
	public Option createButton (Application app, String id)
	{
		Option ok = new Option(id);
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new LogAction("Button pressed - "+id) );
		return ok;
	}
}
