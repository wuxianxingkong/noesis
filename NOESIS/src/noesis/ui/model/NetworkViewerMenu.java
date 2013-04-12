package noesis.ui.model;


import ikor.model.ui.Application;
import ikor.model.ui.Menu;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;

import java.awt.event.KeyEvent;

import noesis.ui.model.actions.ExitAction;
import noesis.ui.model.actions.FileAction;
import noesis.ui.model.actions.ForwardAction;
import noesis.ui.model.actions.URLAction;
import noesis.ui.model.actions.ViewerCloseAction;
import noesis.ui.model.actions.ViewerOpenAction;

public class NetworkViewerMenu extends Menu 
{
	public NetworkViewerMenu (NetworkViewerUIModel ui)
	{
		super("NOESIS Network Viewer Menu");
		
		Application app = ui.getApplication();
		
		Menu file = new Menu("File");
		//Menu edit = new Menu("Edit");
		//Menu data = new Menu("Data");
		//Menu net  = new Menu("Network");
		Menu help = new Menu("Help");

		//Menu importMenu = new Menu("Import");
		Menu exportMenu = new Menu("Export");
		
		this.add(file);
		//this.add(edit);
		//this.add(data);
		//this.add(net);
		this.add(help);
		
		file.setIcon( app.url("icons/download.png") );
		//edit.setIcon( app.url("clipboard.png") );
		//data.setIcon( app.url("microscope.png") );
		//net.setIcon( app.url("kiviat.png") );
		help.setIcon( app.url("icons/search.png") );
		
		//edit.disable();

		//Option newX = new Option("New...", new FileAction(app,"New") );
		//newX.setIcon( app.url("new.png") );
		//file.add( newX );
		
		Option open = new Option("Open", new ViewerOpenAction(ui), KeyEvent.VK_F3 );
		open.setIcon( app.url("icons/open.png") );
		file.add( open );

		Option save =new Option("Save", new FileAction(app,"Save"), KeyEvent.VK_F2 );
		save.setIcon( app.url("icons/save.png") );
		file.add( save );


		Option close = new Option("Close", new ViewerCloseAction(ui), KeyEvent.VK_F4 );
		close.setIcon( app.url("icons/close.png") );
		file.add( close );
/*		
		
		importMenu.setIcon ( app.url("arrow-left.png") );
		file.add( importMenu );
		importMenu.add( createOption(app, "Import GDF network", "kiviat.png") );
		importMenu.add( createOption(app, "Import GML network", "kiviat.png") );
		importMenu.add( createOption(app, "Import GraphML network", "kiviat.png") );
		importMenu.add( createOption(app, "Import Pajek network", "kiviat.png") );
*/		
		file.add( new Separator() );
		
		exportMenu.setIcon ( app.url("icons/arrow-right.png") );
		file.add( exportMenu );
		
		Option saveSVG =new Option("SVG image", new FileAction(app,"Save") );
		saveSVG.setIcon( app.url("icons/kiviat.png") );
		exportMenu.add(saveSVG);
	    saveSVG.disable();

		Option savePNG=new Option("PNG image", new FileAction(app,"Save") );
		savePNG.setIcon( app.url("icons/kiviat.png") );
		exportMenu.add(savePNG);
	    savePNG.disable();
	    
		Option saveJPG=new Option("JPG image", new FileAction(app,"Save") );
		saveJPG.setIcon( app.url("icons/kiviat.png") );
		exportMenu.add(saveJPG);
	    saveJPG.disable();

		file.add( new Separator() );
/*
		Option email = new Option("E-mail", new LogAction("Email..."), KeyEvent.VK_F5 );
		email.setIcon( app.url("email.png") );
		email.disable();
		file.add( email );

		Option print = new Option("Print", new LogAction("Print..."), KeyEvent.VK_F6 );
		print.setIcon( app.url("print.png") );
		print.disable();
		file.add( print );

		file.add( new Separator() );
		
		Option config = new Option("Configuration...", new ForwardAction( new ListModel(app)), KeyEvent.VK_F9 );
		config.setIcon( app.url("config.png") );
		file.add( config );
		
		file.add( new Separator() );
*/
		Option exit = new Option("Exit", new ExitAction(app) );
		exit.setIcon( app.url("icons/exit.png") );
		file.add(exit);
/*		
		// Data
		
		Option viewData = new Option("View", new ForwardAction( new ViewerModel(app) ) );
		viewData.setIcon( app.url("microscope.png") );
		data.add(viewData);
		
		Option editData = new Option("Edit", new ForwardAction( new EditorModel(app) ) );
		editData.setIcon( app.url("microscope.png") );
		data.add(editData);

		Option mvcData = new Option("MVC", new ForwardAction( new MVCModel(app) ) );
		mvcData.setIcon( app.url("microscope.png") );
		data.add(mvcData);

		Option colorData = new Option("Color", new ForwardAction( new ColorUIModel(app) ) );
		colorData.setIcon( app.url("palette.png") );
		data.add(colorData);

		Option dataViewer = new Option("Dataset viewer", new ForwardAction( new DatasetTestModel(app, false) ) );
		dataViewer.setIcon( app.url("microscope.png") );
		data.add(dataViewer);

		Option dataEditor = new Option("Dataset editor", new ForwardAction( new DatasetTestModel(app, true) ) );
		dataEditor.setIcon( app.url("microscope.png") );
		data.add(dataEditor);

		Option figureEditor = new Option("Dataset MVC", new ForwardAction( new FigureTestModel(app) ) );
		figureEditor.setIcon( app.url("microscope.png") );
		data.add(figureEditor);
		
		// Help
		
		Option search = new Option("Tutorial", new ForwardAction( new NestedModel(app) ) );
		search.setIcon( app.url("tutor.png") );
		help.add(search);
*/		

		Option docs = new Option("Project web page", new URLAction("http://noesis.ikor.org/"), KeyEvent.VK_F1 );
		docs.setIcon( app.url("icons/docs.png") );
		help.add(docs);

		Option about = new Option("About...", new ForwardAction( new AboutUIModel(app) ) );
		about.setIcon( app.url("icons/info.png") );
		help.add(about);

	}
	

}
