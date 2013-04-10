package sandbox.mdsd.test;

import ikor.model.ui.Application;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

public class NestedModel2 extends UIModel
{
	public NestedModel2 (Application app)
	{
		super(app, "Panels...");
		
		
		// Button panel
		
		UIModel buttons = new UIModel(app, "Buttons...");
		buttons.setAlignment( UIModel.Alignment.ADJUST );
		
		buttons.add ( createButton ("OK") );
		buttons.add ( createButton ("Cancel") );
		buttons.add ( createButton ("Help") );
		
		// Content panel
		
		UIModel panel = new UIModel(app, "Panel...");
		panel.setAlignment( UIModel.Alignment.TRAILING );
		
		panel.add( new ViewerModel(app) );
		panel.add(buttons);
		
		add(panel);
	}
	
	public Option createButton (String id)
	{
		Option ok = new Option(id);
		ok.setIcon( TestApplication.url("icon.gif") );
		ok.setAction( new LogAction("Button pressed - "+id) );
		return ok;
	}
}
