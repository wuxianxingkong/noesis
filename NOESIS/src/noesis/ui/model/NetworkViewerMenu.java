package noesis.ui.model;


import ikor.model.ui.Application;
import ikor.model.ui.Menu;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;

import java.awt.event.KeyEvent;

import noesis.algorithms.visualization.BinaryTreeLayout;
import noesis.algorithms.visualization.CircularLayout;
import noesis.algorithms.visualization.FruchtermanReingoldLayout;
import noesis.algorithms.visualization.HypercubeLayout;
import noesis.algorithms.visualization.LinearLayout;
import noesis.algorithms.visualization.MeshLayout;
import noesis.algorithms.visualization.RandomLayout;
import noesis.algorithms.visualization.StarLayout;
import noesis.algorithms.visualization.ToroidalLayout;
import noesis.analysis.structure.AveragePathLength;
import noesis.analysis.structure.Betweenness;
import noesis.analysis.structure.Closeness;
import noesis.analysis.structure.ClusteringCoefficient;
import noesis.analysis.structure.InDegree;
import noesis.analysis.structure.NormalizedInDegree;
import noesis.analysis.structure.NormalizedOutDegree;
import noesis.analysis.structure.OutDegree;
import noesis.analysis.structure.PageRank;
import noesis.analysis.structure.Radius;
import noesis.io.graphics.ColorMapNodeRenderer;
import noesis.io.graphics.ColorNodeRenderer;
import noesis.io.graphics.DefaultNodeRenderer;
import noesis.io.graphics.GradientNodeRenderer;
import noesis.io.graphics.GrayscaleColorMap;
import noesis.ui.model.actions.ExitAction;
import noesis.ui.model.actions.FlipAction;
import noesis.ui.model.actions.ForwardAction;
import noesis.ui.model.actions.LayoutAction;
import noesis.ui.model.actions.LinkWidthAction;
import noesis.ui.model.actions.NodeMetricsAction;
import noesis.ui.model.actions.NodeSizeAction;
import noesis.ui.model.actions.NodeStyleAction;
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
	private Menu net;
	private Menu view;
	private Menu data;
	private Menu analysis;
	private Menu help;
	
	private Option open;
	private Option close;
	private Menu   save;
	private Menu   export;

	public NetworkViewerMenu (NetworkViewerUIModel ui)
	{
		super("NOESIS Network Viewer Menu");
		
		Application app = ui.getApplication();
		
		net = new Menu("Network");
		view = createViewMenu(app, ui.getFigure());
		data = createDataMenu(app, ui.getModel());
		analysis = createAnalysisMenu(app, ui.getModel());
		help = createHelpMenu(app);
		
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

		Menu newNetwork = createNewMenu(app);
		newNetwork.setIcon( app.url("icons/new.png") );
		net.add( newNetwork );

		// File operations
		
		open = new Option("Open", new ViewerOpenAction(ui), KeyEvent.VK_F3 );
		open.setIcon( app.url("icons/open.png") );
		net.add( open );

		close = new Option("Close", new ViewerCloseAction(ui), KeyEvent.VK_F4 );
		close.setIcon( app.url("icons/close.png") );
		net.add( close );
	
		net.add( new Separator() );

		save = new Menu("Save");
		save.setIcon( app.url("icons/save.png") );
		net.add( save );

		Option saveGraphML =new Option("GraphML network", new ViewerSaveAction(ui, "graphml") );
		saveGraphML.setIcon( app.url("icons/save.png") );
		save.add(saveGraphML);

		Option saveGML =new Option("GML network", new ViewerSaveAction(ui, "gml") );
		saveGML.setIcon( app.url("icons/save.png") );
		save.add(saveGML);

		Option saveGDF =new Option("GDF network", new ViewerSaveAction(ui, "gdf") );
		saveGDF.setIcon( app.url("icons/save.png") );
		save.add(saveGDF);

		//Option savePajek =new Option("Pajek network", new ViewerSaveAction(ui, "pajek") );
		//savePajek.setIcon( app.url("icons/save.png") );
		//save.add(savePajek);
		
		//Menu importMenu = new Menu("Import");
		//importMenu.setIcon ( app.url("icons/arrow-left.png") );
		//net.add( importMenu );
		
		export = new Menu("Export");
		export.setIcon ( app.url("icons/arrow-right.png") );
		net.add( export );
		
		Option saveSVG =new Option("SVG image", new ViewerSaveAction(ui, "svg") );
		saveSVG.setIcon( app.url("icons/kiviat.png") );
		export.add(saveSVG);
	    saveSVG.disable();

		Option savePNG=new Option("PNG image", new ViewerSaveAction(ui, "png") );
		savePNG.setIcon( app.url("icons/kiviat.png") );
		export.add(savePNG);
	    
		Option saveJPG=new Option("JPG image", new ViewerSaveAction(ui, "jpg") );
		saveJPG.setIcon( app.url("icons/kiviat.png") );
		export.add(saveJPG);

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
	}


	
	// New menu
	// --------
	
	public Menu createNewMenu (Application app)
	{
		Menu newNetwork = new Menu("New...");
		
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
		
		return newNetwork;
	}
	
	// View menu
	// ---------

	public Menu createViewMenu (Application app, NetworkFigure figure)
	{
		Menu view = new Menu("View");

		Option layoutFR = new Option("Fruchterman-Reingold layout", new LayoutAction( app, figure, new FruchtermanReingoldLayout() ) );
		layoutFR.setIcon( app.url("icons/spiral.png") );
		view.add(layoutFR);

		Option layoutRandom = new Option("Random layout", new LayoutAction( app, figure, new RandomLayout() ) );
		layoutRandom.setIcon( app.url("icons/spiral.png") );
		view.add(layoutRandom);

		Menu layoutRegular = new Menu("Regular layout");
		layoutRegular.setIcon( app.url("icons/spiral.png") );
		view.add(layoutRegular);
		
		Option layoutCircular = new Option("Circular layout", new LayoutAction( app, figure, new CircularLayout() ) );
		layoutCircular.setIcon( app.url("icons/spiral.png") );
		layoutRegular.add(layoutCircular);

		Option layoutStar = new Option("Star layout", new LayoutAction( app, figure, new StarLayout() ) );
		layoutStar.setIcon( app.url("icons/spiral.png") );
		layoutRegular.add(layoutStar);

		Option layoutMesh = new Option("Mesh layout", new LayoutAction( app, figure, new MeshLayout() ) );
		layoutMesh.setIcon( app.url("icons/spiral.png") );
		layoutRegular.add(layoutMesh);

		Option layoutHypercube = new Option("Hypercube layout", new LayoutAction( app, figure, new HypercubeLayout() ) );
		layoutHypercube.setIcon( app.url("icons/spiral.png") );
		layoutRegular.add(layoutHypercube);

		Option layoutTree = new Option("Binary tree layout", new LayoutAction( app, figure, new BinaryTreeLayout() ) );
		layoutTree.setIcon( app.url("icons/spiral.png") );
		layoutRegular.add(layoutTree);
		
		Option layoutToroidal = new Option("Toroidal layout", new LayoutAction( app, figure, new ToroidalLayout() ) );
		layoutToroidal.setIcon( app.url("icons/spiral.png") );
		layoutRegular.add(layoutToroidal);

		Option layoutLinear = new Option("Linear layout", new LayoutAction( app, figure, new LinearLayout() ) );
		layoutLinear.setIcon( app.url("icons/spiral.png") );
		layoutRegular.add(layoutLinear);
		
		view.add ( new Separator() );

		Option nodeAttributes = new Option("Node attributes...", new ForwardAction (new NodeAttributesUIModel(app, figure) ));
		nodeAttributes.setIcon( app.url("icons/palette.png") );
		view.add(nodeAttributes);
		nodeAttributes.disable(); // TODO ...

		Menu nodeSize = new Menu("Node size");
		nodeSize.setIcon( app.url("icons/size.png") );
		view.add(nodeSize);
		
		Option nodeSizeIncrease = new Option ("Increase node size", new NodeSizeAction(app,figure,+1), KeyEvent.VK_F7 );
		nodeSizeIncrease.setIcon( app.url("icons/size.png") );
		nodeSize.add(nodeSizeIncrease);

		Option nodeSizeDecrease = new Option ("Decrease node size", new NodeSizeAction(app,figure,-1), KeyEvent.VK_F6 );
		nodeSizeDecrease.setIcon( app.url("icons/size-flip.png") );
		nodeSize.add(nodeSizeDecrease);

		Menu nodeStyle = new Menu("Node style");
		nodeStyle.setIcon( app.url("icons/paint.png") );
		view.add(nodeStyle);

		Option nodeStyle3D = new Option ("3D", new NodeStyleAction(app,figure,new GradientNodeRenderer(true)) );
		nodeStyle3D.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyle3D);

		Option nodeStyleFlat = new Option ("Flat", new NodeStyleAction(app,figure,new GradientNodeRenderer(false)) );
		nodeStyleFlat.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleFlat);

		Option nodeStyleJet = new Option ("Jet", new NodeStyleAction(app,figure,new ColorMapNodeRenderer() ) );
		nodeStyleJet.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleJet);

		Option nodeStyleGrayscale = new Option ("Grayscale", new NodeStyleAction(app,figure,new ColorMapNodeRenderer( new GrayscaleColorMap(16)) ) );
		nodeStyleGrayscale.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleGrayscale);

		Option nodeStyleColor = new Option ("Red-green-blue", new NodeStyleAction(app,figure,new ColorNodeRenderer() ) );
		nodeStyleColor.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleColor);

		
		Option nodeStyleDefault = new Option ("Default", new NodeStyleAction(app,figure,new DefaultNodeRenderer() ) );
		nodeStyleDefault.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleDefault);
		
		view.add ( new Separator() );
		
		Menu linkWidth = new Menu("Link width");
		linkWidth.setIcon( app.url("icons/size.png") );
		view.add(linkWidth);

		Option linkWidthIncrease = new Option ("Increase link width", new LinkWidthAction(app,figure,+1), KeyEvent.VK_F9 );
		linkWidthIncrease.setIcon( app.url("icons/size.png") );
		linkWidth.add(linkWidthIncrease);

		Option linkWidthDecrease = new Option ("Decrease link width", new LinkWidthAction(app,figure,-1), KeyEvent.VK_F8 );
		linkWidthDecrease.setIcon( app.url("icons/size-flip.png") );
		linkWidth.add(linkWidthDecrease);
		
		// Menu linkStyle = new Menu("Link style");
		// linkStyle.setIcon( app.url("icons/paint.png") );
		// view.add(linkStyle);
		
		view.add ( new Separator() );
		
		Option flipHorizontal = new Option ("Mirror", new FlipAction(app,figure,FlipAction.Mode.HORIZONTAL) );
		flipHorizontal.setIcon( app.url("icons/flip-horizontal.png") );
		view.add(flipHorizontal);

		Option flipVertical = new Option ("Flip", new FlipAction(app,figure,FlipAction.Mode.VERTICAL) );
		flipVertical.setIcon( app.url("icons/flip-vertical.png") );
		view.add(flipVertical);

		
		return view;
	}
	
	// Data menu
	// ---------
	
	public Menu createDataMenu (Application app, NetworkModel model)
	{
		Menu data = new Menu("Data");
		
		Option dataNodes = new Option("Nodes", new ForwardAction( new NodesetUIModel(app, model) ) );
		dataNodes.setIcon( app.url("icons/chart.png") );
		data.add(dataNodes);

		Option dataLinks = new Option("Links", new ForwardAction( new LinksetUIModel(app, model) ) );
		dataLinks.setIcon( app.url("icons/chart.png") );
		data.add(dataLinks);

		return data;
	}

	// Analysis menu
	// -------------
	
	public Menu createAnalysisMenu (Application app, NetworkModel model)
	{
		Menu analysis = new Menu("Analysis");
		
		
		Menu degree = new Menu("Degree");
		degree.setIcon( app.url("icons/microscope.png") );
		analysis.add(degree);
		
		Option inDegree = new Option("In-degree", new NodeMetricsAction(app, model, InDegree.class) );
		inDegree.setIcon( app.url("icons/microscope.png") );
		degree.add(inDegree);

		Option outDegree = new Option("Out-degree", new NodeMetricsAction(app, model, OutDegree.class) );
		outDegree.setIcon( app.url("icons/microscope.png") );
		degree.add(outDegree);

		Option inDegreeNormalized = new Option("Normalized in-degree", new NodeMetricsAction(app, model, NormalizedInDegree.class) );
		inDegreeNormalized.setIcon( app.url("icons/microscope.png") );
		degree.add(inDegreeNormalized);

		Option outDegreeNormalized = new Option("Normalized out-degree", new NodeMetricsAction(app, model, NormalizedOutDegree.class) );
		outDegreeNormalized.setIcon( app.url("icons/microscope.png") );
		degree.add(outDegreeNormalized);
		
		Option radius = new Option("Radius", new NodeMetricsAction(app, model, Radius.class) );
		radius.setIcon( app.url("icons/microscope.png") );
		analysis.add(radius);
		
		Option avl = new Option("Average path length", new NodeMetricsAction(app, model, AveragePathLength.class) );
		avl.setIcon( app.url("icons/microscope.png") );
		analysis.add(avl);

		Option closeness = new Option("Closeness", new NodeMetricsAction(app, model, Closeness.class) );
		closeness.setIcon( app.url("icons/microscope.png") );
		analysis.add(closeness);		
		
		Option betweenness = new Option("Betweenness", new NodeMetricsAction(app, model, Betweenness.class) );
		betweenness.setIcon( app.url("icons/microscope.png") );
		analysis.add(betweenness);		

		Option pagerank = new Option("PageRank", new NodeMetricsAction(app, model, PageRank.class) );
		pagerank.setIcon( app.url("icons/microscope.png") );
		analysis.add(pagerank);		

		Option cc = new Option("Clustering coefficient", new NodeMetricsAction(app, model, ClusteringCoefficient.class) );
		cc.setIcon( app.url("icons/microscope.png") );
		analysis.add(cc);		
		
		return analysis;
	}
	
	// Help menu
	// ---------
	
	public Menu createHelpMenu (Application app)
	{
		Menu help = new Menu("Help");
		
		//Option tutorial = new Option("Tutorial", new URLAction(app,"http://noesis.ikor.org/") );
		//tutorial.setIcon( app.url("icons/tutor.png") );
		//help.add(tutorial);

		Option web = new Option("Project web page", new URLAction(app,"http://noesis.ikor.org/"), KeyEvent.VK_F1 );
		web.setIcon( app.url("icons/docs.png") );
		help.add(web);
		
		//Option config = new Option("Configuration...", new ForwardAction(null), KeyEvent.VK_F9 );
		//config.setIcon( app.url("icons/config.png") );
		//help.add( config );
		

		Option about = new Option("About...", new ForwardAction( new AboutUIModel(app) ) );
		about.setIcon( app.url("icons/info.png") );
		help.add(about);
		
		return help;
	}
	
	
	// Menu update

	public void reset ()
	{
		view.hide();
		data.hide();
		analysis.hide();

		save.disable();
		close.disable();
		export.disable();
		
		notifyObservers();
	}
	
	public void activate ()
	{
		save.enable();
		close.enable();
		export.enable();
		
		view.show();
		view.enable();
		
		data.show();
		data.enable();
		
		analysis.show();
		analysis.enable();
		
		notifyObservers();
	}
	
}
