package sandbox.mdsd.test;

import sandbox.mdsd.ui.Menu;
import sandbox.mdsd.ui.Option;

public class TestMenu extends Menu 
{
	public TestMenu ()
	{
		super("NOESIS");
		
		Menu file = new Menu("File");
		Menu data = new Menu("Data");
		Menu net  = new Menu("Network");
		Menu help = new Menu("Help");
		
		this.add(file);
		this.add(data);
		this.add(net);
		this.add(help);
		
		file.add( new Option("Open", new FileOpenAction() ) );
		file.add( new Option("Close", new FileCloseAction() ) );		
		file.add( new Option("Exit", new ExitAction() ) );		
	}

}
