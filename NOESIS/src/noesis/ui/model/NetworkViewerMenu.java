package noesis.ui.model;


import ikor.model.ui.Application;
import ikor.model.ui.Menu;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;

import java.awt.event.KeyEvent;

import noesis.ui.model.actions.ExitAction;
import noesis.ui.model.actions.ForwardAction;
import noesis.ui.model.actions.URLAction;
import noesis.ui.model.actions.ViewerOpenAction;
import noesis.ui.model.actions.ViewerSaveAction;
import noesis.ui.model.actions.ViewerCloseAction;
import noesis.ui.model.networks.AnchoredRandomNetworkUI;
import noesis.ui.model.networks.BinaryTreeNetworkUI;
import noesis.ui.model.networks.CompleteNetworkUI;
import noesis.ui.model.networks.ConnectedRandomNetworkUI;
import noesis.ui.model.networks.ErdosRenyiNetworkUI;
import noesis.ui.model.networks.GilbertNetworkUI;
import noesis.ui.model.networks.HypercubeNetworkUI;
import noesis.ui.model.networks.IsolateNetworkUI;
import noesis.ui.model.networks.MeshNetworkUI;
import noesis.ui.model.networks.RingNetworkUI;
import noesis.ui.model.networks.StarNetworkUI;
import noesis.ui.model.networks.TandemNetworkUI;
import noesis.ui.model.networks.ToroidalNetworkUI;

public class NetworkViewerMenu extends Menu 
{
	public NetworkViewerMenu (NetworkViewerUIModel ui)
	{
		super("NOESIS Network Viewer Menu");
		
		Application app = ui.getApplication();
		
		Menu net = new Menu("Network");
		Menu view = new Menu("View");
		Menu data = new Menu("Data");
		Menu analysis = new Menu("Analysis");
		Menu help = new Menu("Help");
		
		this.add(net);
		this.add(view);
		this.add(data);
		this.add(analysis);
		this.add(help);
		
		net.setIcon( app.url("icons/download.png") );
		view.setIcon( app.url("icons/kiviat.png") );
		data.setIcon( app.url("icons/chart.png") );
		analysis.setIcon( app.url("icons/microscope.png") );
		help.setIcon( app.url("icons/search.png") );
		
		// New network...

		Menu newNetwork = new Menu("New...");
		newNetwork.setIcon( app.url("icons/new.png") );
		net.add( newNetwork );

		// Random networks
		
		Menu newRandomNetwork = new Menu("Random network");
		newRandomNetwork.setIcon( app.url("icons/new.png") );
		newNetwork.add (newRandomNetwork);
		
		Option newER = new Option("Erdös-Renyi network", new ForwardAction( new ErdosRenyiNetworkUI(app) ) );
		newER.setIcon( app.url("icons/kiviat.png") );
		newRandomNetwork.add(newER);

		Option newGilbert = new Option("Gilbert network", new ForwardAction( new GilbertNetworkUI(app) ) );
		newGilbert.setIcon( app.url("icons/kiviat.png") );
		newRandomNetwork.add(newGilbert);

		Option newAnchored = new Option("Anchored random network", new ForwardAction( new AnchoredRandomNetworkUI(app) ) );
		newAnchored.setIcon( app.url("icons/kiviat.png") );
		newRandomNetwork.add(newAnchored);

		Option newConnected = new Option("Connected random network", new ForwardAction( new ConnectedRandomNetworkUI(app) ) );
		newConnected.setIcon( app.url("icons/kiviat.png") );
		newRandomNetwork.add(newConnected);
		
		// Regular networks
		
		Menu newRegularNetwork = new Menu("Regular network");
		newRegularNetwork.setIcon( app.url("icons/new.png") );
		newNetwork.add (newRegularNetwork);
		
		Option newComplete = new Option("Complete network", new ForwardAction( new CompleteNetworkUI(app) ) );
		newComplete.setIcon( app.url("icons/kiviat.png") );
		newRegularNetwork.add(newComplete);

		Option newStar = new Option("Star network", new ForwardAction( new StarNetworkUI(app) ) );
		newStar.setIcon( app.url("icons/kiviat.png") );
		newRegularNetwork.add(newStar);

		newRegularNetwork.add( new Separator() );
		
		Option newRing = new Option("Ring network", new ForwardAction( new RingNetworkUI(app) ) );
		newRing.setIcon( app.url("icons/kiviat.png") );
		newRegularNetwork.add(newRing);
		
		Option newTandem = new Option("Tandem network", new ForwardAction( new TandemNetworkUI(app) ) );
		newTandem.setIcon( app.url("icons/kiviat.png") );
		newRegularNetwork.add(newTandem);

		newRegularNetwork.add( new Separator() );

		Option newMesh = new Option("Mesh network", new ForwardAction( new MeshNetworkUI(app) ) );
		newMesh.setIcon( app.url("icons/kiviat.png") );
		newRegularNetwork.add(newMesh);

		Option newToroidal = new Option("Toroidal network", new ForwardAction( new ToroidalNetworkUI(app) ) );
		newToroidal.setIcon( app.url("icons/kiviat.png") );
		newRegularNetwork.add(newToroidal);

		Option newHypercube = new Option("Hypercube network", new ForwardAction( new HypercubeNetworkUI(app) ) );
		newHypercube.setIcon( app.url("icons/kiviat.png") );
		newRegularNetwork.add(newHypercube);

		Option newBinaryTree = new Option("Binary tree network", new ForwardAction( new BinaryTreeNetworkUI(app) ) );
		newBinaryTree.setIcon( app.url("icons/kiviat.png") );
		newRegularNetwork.add(newBinaryTree);
		
		newRegularNetwork.add( new Separator() );
		
		Option newIsolate = new Option("Isolate network", new ForwardAction( new IsolateNetworkUI(app) ) );
		newIsolate.setIcon( app.url("icons/kiviat.png") );
		newRegularNetwork.add(newIsolate);
		
		// File operations
		
		Option open = new Option("Open", new ViewerOpenAction(ui), KeyEvent.VK_F3 );
		open.setIcon( app.url("icons/open.png") );
		net.add( open );

		Option save = new Option("Save", new ViewerSaveAction(ui), KeyEvent.VK_F2 );
		save.setIcon( app.url("icons/save.png") );
		net.add( save );

		Option close = new Option("Close", new ViewerCloseAction(ui), KeyEvent.VK_F4 );
		close.setIcon( app.url("icons/close.png") );
		net.add( close );
	
		net.add( new Separator() );

		//Menu importMenu = new Menu("Import");
		//importMenu.setIcon ( app.url("icons/arrow-left.png") );
		//net.add( importMenu );
		
		Menu exportMenu = new Menu("Export");
		exportMenu.setIcon ( app.url("icons/arrow-right.png") );
		net.add( exportMenu );
		
		Option saveSVG =new Option("SVG image", new ViewerSaveAction(ui) );
		saveSVG.setIcon( app.url("icons/kiviat.png") );
		exportMenu.add(saveSVG);
	    saveSVG.disable();

		Option savePNG=new Option("PNG image", new ViewerSaveAction(ui) );
		savePNG.setIcon( app.url("icons/kiviat.png") );
		exportMenu.add(savePNG);
	    savePNG.disable();
	    
		Option saveJPG=new Option("JPG image", new ViewerSaveAction(ui) );
		saveJPG.setIcon( app.url("icons/kiviat.png") );
		exportMenu.add(saveJPG);
	    saveJPG.disable();

		net.add( new Separator() );
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
*/
		Option exit = new Option("Exit", new ExitAction(app) );
		exit.setIcon( app.url("icons/exit.png") );
		net.add(exit);

		// View
		
		view.disable();

		// Data menu
		
		data.disable();
		
		// Analysis menu
		
		analysis.disable();
				
		
		// Help
		// ----
		
		//Option tutorial = new Option("Tutorial", new URLAction("http://noesis.ikor.org/") );
		//tutorial.setIcon( app.url("icons/tutor.png") );
		//help.add(tutorial);

		Option web = new Option("Project web page", new URLAction("http://noesis.ikor.org/"), KeyEvent.VK_F1 );
		web.setIcon( app.url("icons/docs.png") );
		help.add(web);
		
		//Option config = new Option("Configuration...", new ForwardAction(null), KeyEvent.VK_F9 );
		//config.setIcon( app.url("icons/config.png") );
		//help.add( config );
		

		Option about = new Option("About...", new ForwardAction( new AboutUIModel(app) ) );
		about.setIcon( app.url("icons/info.png") );
		help.add(about);
	}
	

}
