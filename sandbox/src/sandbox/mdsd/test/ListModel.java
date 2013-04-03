package sandbox.mdsd.test;

import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.Selector;
import sandbox.mdsd.ui.UIModel;

public class ListModel  extends UIModel
{
	public ListModel (Application app)
	{
		super(app, "Lists...");
		
		Selector selector = new Selector();
		
		selector.setMultipleSelection(true);
		
		selector.add( createOption(selector, "GDF network", "kiviat.png") );
		selector.add( createOption(selector, "GML network", "kiviat.png") );
		selector.add( createOption(selector, "GraphML network", "kiviat.png") );
		selector.add( createOption(selector, "Pajek network", "kiviat.png") );
		//selector.add( new Separator() );

		add (selector);
		
		Option ok = new Option("");
		ok.setIcon( TestApplication.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		add( ok );		
	}
	
	private Option createOption (Selector selector, String text, String icon)
	{
		Option option = new Option(text, new LogAction(text) );
		option.setIcon( TestApplication.url(icon) );
		option.setAction( new LogStateAction(selector) );
		return option;
	}
	
}
