package sandbox.mdsd.test;

import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.UI;
import sandbox.mdsd.ui.UIBuilder;
import sandbox.mdsd.ui.UIModel;

import sandbox.mdsd.ui.console.ConsoleUIBuilder;
import sandbox.mdsd.ui.swing.SwingUIBuilder;

public class TestApplication 
{

	public static UIModel createUIModel ()
	{
		UIModel model = new UIModel("NOESIS");
		
		model.add( new Option("$exit", new ExitAction() ) );
		
		model.add( new TestMenu());
		
		return model;
	}
	
	public static void main(String[] args) 
	{
		
		UIModel   model = createUIModel();
		UIBuilder builder = new SwingUIBuilder(); // new ConsoleUIBuilder();
		UI        ui = builder.build(model);
		
		ui.run();
	}

}
