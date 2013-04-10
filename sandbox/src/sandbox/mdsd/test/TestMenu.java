package sandbox.mdsd.test;


import ikor.model.ui.Application;
import ikor.model.ui.Menu;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;

import java.awt.event.KeyEvent;

public class TestMenu extends Menu 
{
	public TestMenu (Application app)
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
		
		file.setIcon( TestApplication.url("download.png") );
		edit.setIcon( TestApplication.url("clipboard.png") );
		data.setIcon( TestApplication.url("microscope.png") );
		net.setIcon( TestApplication.url("kiviat.png") );
		help.setIcon( TestApplication.url("search.png") );
		
		edit.disable();

		Option newX = new Option("New...", new FileAction(app,"New") );
		newX.setIcon( TestApplication.url("new.png") );
		file.add( newX );
		
		Option save =new Option("Save", new FileAction(app,"Save"), KeyEvent.VK_F2 );
		save.setIcon( TestApplication.url("save.png") );
		file.add( save );

		Option open = new Option("Open", new FileAction(app,"Open"), KeyEvent.VK_F3 );
		open.setIcon( TestApplication.url("open.png") );
		file.add( open );
		
		Option close = new Option("Close", new LogAction("Close file..."), KeyEvent.VK_F4 );
		close.setIcon( TestApplication.url("close.png") );
		file.add( close );
		
		file.add( new Separator() );
		
		importMenu.setIcon ( TestApplication.url("arrow-left.png") );
		file.add( importMenu );
		importMenu.add( createOption("Import GDF network", "kiviat.png") );
		importMenu.add( createOption("Import GML network", "kiviat.png") );
		importMenu.add( createOption("Import GraphML network", "kiviat.png") );
		importMenu.add( createOption("Import Pajek network", "kiviat.png") );
		
		exportMenu.setIcon ( TestApplication.url("arrow-right.png") );
		file.add( exportMenu );
		exportMenu.add( createOption("Export GDF network", "kiviat.png") );
		exportMenu.add( createOption("Export GML network", "kiviat.png") );
		exportMenu.add( createOption("Export GraphML network", "kiviat.png") );
		exportMenu.add( createOption("Export Pajek network", "kiviat.png") );

		file.add( new Separator() );

		Option email = new Option("E-mail", new LogAction("Email..."), KeyEvent.VK_F5 );
		email.setIcon( TestApplication.url("email.png") );
		email.disable();
		file.add( email );

		Option print = new Option("Print", new LogAction("Print..."), KeyEvent.VK_F6 );
		print.setIcon( TestApplication.url("print.png") );
		print.disable();
		file.add( print );

		file.add( new Separator() );
		
		Option config = new Option("Configuration...", new ForwardAction( new ListModel(app)), KeyEvent.VK_F9 );
		config.setIcon( TestApplication.url("config.png") );
		file.add( config );
		
		file.add( new Separator() );
		
		Option exit = new Option("Exit", new ExitAction(app) );
		exit.setIcon( TestApplication.url("exit.png") );
		file.add(exit);
		
		// Data
		
		Option viewData = new Option("View", new ForwardAction( new ViewerModel(app) ) );
		viewData.setIcon( TestApplication.url("microscope.png") );
		data.add(viewData);
		
		Option editData = new Option("Edit", new ForwardAction( new EditorModel(app) ) );
		editData.setIcon( TestApplication.url("microscope.png") );
		data.add(editData);

		Option mvcData = new Option("MVC", new ForwardAction( new MVCModel(app) ) );
		mvcData.setIcon( TestApplication.url("microscope.png") );
		data.add(mvcData);

		Option colorData = new Option("Color", new ForwardAction( new ColorUIModel(app) ) );
		colorData.setIcon( TestApplication.url("palette.png") );
		data.add(colorData);

		Option dataViewer = new Option("Dataset viewer", new ForwardAction( new DatasetTestModel(app, false) ) );
		dataViewer.setIcon( TestApplication.url("microscope.png") );
		data.add(dataViewer);

		Option dataEditor = new Option("Dataset editor", new ForwardAction( new DatasetTestModel(app, true) ) );
		dataEditor.setIcon( TestApplication.url("microscope.png") );
		data.add(dataEditor);

		Option figureEditor = new Option("Dataset MVC", new ForwardAction( new FigureTestModel(app) ) );
		figureEditor.setIcon( TestApplication.url("microscope.png") );
		data.add(figureEditor);
		
		// Help
		
		Option search = new Option("Tutorial", new ForwardAction( new NestedModel(app) ) );
		search.setIcon( TestApplication.url("tutor.png") );
		help.add(search);

		Option docs = new Option("Documentation", new ForwardAction( new NestedModel2(app) ), KeyEvent.VK_F1 );
		docs.setIcon( TestApplication.url("docs.png") );
		help.add(docs);
		
		Option about = new Option("About...", new ForwardAction( new AboutModel(app) ) );
		about.setIcon( TestApplication.url("info.png") );
		help.add(about);

	}
	
	private Option createOption (String text, String icon)
	{
		Option option = new Option(text, new LogAction(text) );
		option.setIcon( TestApplication.url(icon) );
		return option;
	}

}
