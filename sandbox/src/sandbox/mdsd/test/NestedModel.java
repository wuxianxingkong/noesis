package sandbox.mdsd.test;

import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.UIModel;

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
		
		buttons.add ( createButton ("OK") );
		buttons.add ( createButton ("Cancel") );
		buttons.add ( createButton ("Help") );
		
		add(buttons);
		
	}
	
	public Option createButton (String id)
	{
		Option ok = new Option(id);
		ok.setIcon( TestApplication.url("icon.gif") );
		ok.setAction( new LogAction("Button pressed - "+id) );
		return ok;
	}
}
