package sandbox.mdsd.test;

import sandbox.mdsd.ui.Menu;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.Separator;

import java.awt.event.KeyEvent;

public class TestMenu extends Menu 
{
	private static final String PATH = "sandbox/mdsd/test/image/";
	
	public TestMenu ()
	{
		super("NOESIS");
		
		Menu file = new Menu("File");
		Menu edit = new Menu("Edit");
		Menu data = new Menu("Data");
		Menu net  = new Menu("Network");
		Menu help = new Menu("Help");

		Menu importMenu = new Menu("Import");
		Menu exportMenu = new Menu("Export");
		
		this.add(file);
		this.add(edit);
		this.add(data);
		this.add(net);
		this.add(help);
		
		edit.disable();

		Option newX = new Option("New...", new FileNewAction() );
		newX.setIcon( PATH+"new.png");
		file.add( newX );
		
		Option save =new Option("Save", KeyEvent.VK_F2, new FileSaveAction() );
		save.setIcon( PATH+"save.png" );
		file.add( save );

		Option open = new Option("Open", KeyEvent.VK_F3, new FileOpenAction() );
		open.setIcon( PATH+"open.png");
		file.add( open );
		
		Option close = new Option("Close", KeyEvent.VK_F4, new FileCloseAction() );
		close.setIcon( PATH+"close.png");
		file.add( close );
		
		file.add( new Separator() );
		
		importMenu.setIcon ( PATH+"arrow-left.png");
		file.add( importMenu );
		importMenu.add( createOption("Import GDF network", "kiviat.png") );
		importMenu.add( createOption("Import GML network", "kiviat.png") );
		importMenu.add( createOption("Import GraphML network", "kiviat.png") );
		importMenu.add( createOption("Import Pajek network", "kiviat.png") );
		
		exportMenu.setIcon ( PATH+"arrow-right.png");
		file.add( exportMenu );
		exportMenu.add( createOption("Export GDF network", "kiviat.png") );
		exportMenu.add( createOption("Export GML network", "kiviat.png") );
		exportMenu.add( createOption("Export GraphML network", "kiviat.png") );
		exportMenu.add( createOption("Export Pajek network", "kiviat.png") );

		file.add( new Separator() );

		Option email = new Option("E-mail", KeyEvent.VK_F5, new LogAction("Email...") );
		email.setIcon( PATH+"email.png" );
		email.disable();
		file.add( email );

		Option print = new Option("Print", KeyEvent.VK_F6, new FilePrintAction() );
		print.setIcon( PATH+"print.png" );
		print.disable();
		file.add( print );

		file.add( new Separator() );
		
		Option config = new Option("Configuration...", KeyEvent.VK_F9, new LogAction("Configuration") );
		config.setIcon( PATH+"config.png" );
		file.add( config );
		
		file.add( new Separator() );
		
		Option exit = new Option("Exit", new ExitAction() );
		exit.setIcon( PATH+"exit.png" );
		file.add(exit);
		
		Option search = new Option("Documentation", KeyEvent.VK_F1,  new LogAction("Documentation") );
		search.setIcon( PATH+"docs.png" );
		help.add(search);
		
		Option about = new Option("About...", new LogAction("About...") );
		about.setIcon( PATH+"info.png" );
		help.add(about);

	}
	
	private Option createOption (String text, String icon)
	{
		Option option = new Option(text, new LogAction(text) );
		option.setIcon( PATH+icon );
		return option;
	}

}
