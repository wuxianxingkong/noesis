package sandbox.mdsd.test;

import ikor.model.ui.Application;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;
import ikor.model.ui.UIModel;

public class ListModel  extends UIModel
{
	public ListModel (Application app)
	{
		super(app, "Lists...");

		setIcon( app.url("icon.gif") );		
		setAlignment( UIModel.Alignment.LEADING );
		
		Selector selector = new Selector();
		
		selector.setMultipleSelection(true);
		
		for (int i=1; i<=6; i++)
			selector.add( createOption(app, selector, "Network #"+i, "kiviat.png") );
		//selector.add( new Separator() );

		add (selector);
	}
	
	private Option createOption (Application app, Selector selector, String text, String icon)
	{
		Option option = new Option(text, new LogAction(text) );
		option.setIcon( app.url(icon) );
		option.setAction( new LogStateAction(selector) );
		return option;
	}
	
}
