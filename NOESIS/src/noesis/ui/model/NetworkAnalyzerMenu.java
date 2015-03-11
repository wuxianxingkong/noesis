package noesis.ui.model;

import ikor.model.graphics.colors.InverseGrayscaleColorMap;
import ikor.model.graphics.colors.JetColorMap;
import ikor.model.ui.Application;
import ikor.model.ui.Menu;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;

import java.awt.event.KeyEvent;

import noesis.algorithms.communities.hierarchical.divisive.NewmanGirvanCommunityDetector;
import noesis.algorithms.communities.hierarchical.divisive.RadicchiCommunityDetector;
import noesis.algorithms.communities.hierarchical.agglomerative.AverageLinkCommunityDetector;
import noesis.algorithms.communities.hierarchical.agglomerative.CompleteLinkCommunityDetector;
import noesis.algorithms.communities.hierarchical.agglomerative.SingleLinkCommunityDetector;
import noesis.algorithms.communities.modularity.FastGreedyCommunityDetector;
import noesis.algorithms.communities.modularity.MultiStepGreedyCommunityDetector;
import noesis.algorithms.communities.overlapping.BigClamCommunityDetector;
import noesis.algorithms.communities.partitioning.KernighanLinCommunityDetector;
import noesis.algorithms.communities.spectral.EIG1CommunityDetector;
import noesis.algorithms.communities.spectral.NJWCommunityDetector;
import noesis.algorithms.communities.spectral.UKMeansCommunityDetector;
import noesis.algorithms.visualization.BinaryTreeLayout;
import noesis.algorithms.visualization.CircularLayout;
import noesis.algorithms.visualization.FruchtermanReingoldLayout;
import noesis.algorithms.visualization.HierarchicalLayout;
import noesis.algorithms.visualization.HypercubeLayout;
import noesis.algorithms.visualization.KamadaKawaiLayout;
import noesis.algorithms.visualization.LinearLayout;
import noesis.algorithms.visualization.MeshLayout;
import noesis.algorithms.visualization.RadialLayout;
import noesis.algorithms.visualization.RandomLayout;
import noesis.algorithms.visualization.StarLayout;
import noesis.algorithms.visualization.ToroidalLayout;
import noesis.analysis.structure.*;
import noesis.io.graphics.ColorMapNodeRenderer;
import noesis.io.graphics.LinearGradientNodeRenderer;
import noesis.io.graphics.RadialGradientNodeRenderer;
import noesis.ui.model.actions.CommunityDetectionAction;
import noesis.ui.model.actions.ExitAction;
import noesis.ui.model.actions.FlipAction;
import noesis.ui.model.actions.ForwardAction;
import noesis.ui.model.actions.LayoutAction;
import noesis.ui.model.actions.LinkStyleAction;
import noesis.ui.model.actions.LinkStyleResetAction;
import noesis.ui.model.actions.LinkWidthAction;
import noesis.ui.model.actions.LinkScoreAction;
import noesis.ui.model.actions.NodeScoreAction;
import noesis.ui.model.actions.NodeMultiScoreAction;
import noesis.ui.model.actions.NodeSizeAction;
import noesis.ui.model.actions.NodeStyleAction;
import noesis.ui.model.actions.NodeStyleResetAction;
import noesis.ui.model.actions.AnalyzerOpenAction;
import noesis.ui.model.actions.AnalyzerSaveAction;
import noesis.ui.model.actions.AnalyzerCloseAction;
import noesis.ui.model.actions.StatsAction;
import noesis.ui.model.networks.AnchoredRandomNetworkUI;
import noesis.ui.model.networks.BarabasiAlbertNetworkUI;
import noesis.ui.model.networks.BinaryTreeNetworkUI;
import noesis.ui.model.networks.CompleteNetworkUI;
import noesis.ui.model.networks.ConnectedRandomNetworkUI;
import noesis.ui.model.networks.ErdosRenyiNetworkUI;
import noesis.ui.model.networks.GilbertNetworkUI;
import noesis.ui.model.networks.HypercubeNetworkUI;
import noesis.ui.model.networks.IsolateNetworkUI;
import noesis.ui.model.networks.MeshNetworkUI;
import noesis.ui.model.networks.PriceCitationNetworkUI;
import noesis.ui.model.networks.RingNetworkUI;
import noesis.ui.model.networks.StarNetworkUI;
import noesis.ui.model.networks.TandemNetworkUI;
import noesis.ui.model.networks.ToroidalNetworkUI;
import noesis.ui.model.networks.WattsStrogatzNetworkUI;

public class NetworkAnalyzerMenu extends Menu 
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

	public NetworkAnalyzerMenu (NetworkAnalyzerUIModel ui)
	{
		super("NOESIS Network Viewer Menu");
		
		Application app = ui.getApplication();
		
		net = createNetMenu(app,ui); 
		view = createViewMenu(app, ui.getFigure());
		data = createDataMenu(app, ui.getModel());
		analysis = createAnalysisMenu(app, ui.getModel(), ui.getFigure());
		help = new HelpMenu(app);
		
		this.add(net);
		this.add(view);
		this.add(data);
		this.add(analysis);
		this.add(help);
	}

	// Net menu
	// --------

	public Menu createNetMenu (Application app, NetworkAnalyzerUIModel ui)
	{
		Menu net = new Menu("Network");

		net.setIcon( app.url("icons/download.png") );
		
		// New network...

		Menu newNetwork = createNewMenu(app);
		newNetwork.setIcon( app.url("icons/new.png") );
		net.add( newNetwork );

		// File operations
		
		open = new Option("Open", new AnalyzerOpenAction(ui), KeyEvent.VK_F3 );
		open.setIcon( app.url("icons/open.png") );
		net.add( open );

		close = new Option("Close", new AnalyzerCloseAction(ui), KeyEvent.VK_F4 );
		close.setIcon( app.url("icons/close.png") );
		net.add( close );
	
		net.add( new Separator() );

		save = new Menu("Save");
		save.setIcon( app.url("icons/save.png") );
		net.add( save );

		Option saveGraphML =new Option("GraphML network", new AnalyzerSaveAction(ui, "graphml") );
		saveGraphML.setIcon( app.url("icons/save.png") );
		save.add(saveGraphML);

		Option saveGML =new Option("GML network", new AnalyzerSaveAction(ui, "gml") );
		saveGML.setIcon( app.url("icons/save.png") );
		save.add(saveGML);

		Option saveGDF =new Option("GDF network", new AnalyzerSaveAction(ui, "gdf") );
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
		
		Option saveSVG =new Option("SVG image", new AnalyzerSaveAction(ui, "svg") );
		saveSVG.setIcon( app.url("icons/kiviat.png") );
		export.add(saveSVG);

		Option savePNG=new Option("PNG image", new AnalyzerSaveAction(ui, "png") );
		savePNG.setIcon( app.url("icons/kiviat.png") );
		export.add(savePNG);
	    
		Option saveJPG=new Option("JPG image", new AnalyzerSaveAction(ui, "jpg") );
		saveJPG.setIcon( app.url("icons/kiviat.png") );
		export.add(saveJPG);

		net.add( new Separator() );
/*
		Option email = new Option("E-mail", new LogAction("Email..."), KeyEvent.VK_F12 );
		email.setIcon( app.url("email.png") );
		email.disable();
		file.add( email );

		Option print = new Option("Print", new LogAction("Print..."), KeyEvent.VK_F11 );
		print.setIcon( app.url("print.png") );
		print.disable();
		file.add( print );
		
		file.add( new Separator() );
*/
		Option exit = new Option("Exit", new ExitAction(app) );
		exit.setIcon( app.url("icons/exit.png") );
		net.add(exit);

		return net;
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

		newRandomNetwork.add( new Separator() );

		Option newWS = new Option("Watts-Strogatz small world network", new ForwardAction( new WattsStrogatzNetworkUI(app) ) );
		newWS.setIcon( app.url("icons/kiviat.png") );
		newRandomNetwork.add(newWS);
		
		newRandomNetwork.add( new Separator() );
		
		Option newBA = new Option("Barabasi-Albert preferential attachment network", new ForwardAction( new BarabasiAlbertNetworkUI(app) ) );
		newBA.setIcon( app.url("icons/kiviat.png") );
		newRandomNetwork.add(newBA);

		Option newPrice = new Option("Price's citation network", new ForwardAction( new PriceCitationNetworkUI(app) ) );
		newPrice.setIcon( app.url("icons/kiviat.png") );
		newRandomNetwork.add(newPrice);
		
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

		view.setIcon( app.url("icons/kiviat.png") );

		Option layoutFR = new Option("Fruchterman-Reingold layout", new LayoutAction( app, figure, new FruchtermanReingoldLayout() ) );
		layoutFR.setIcon( app.url("icons/spiral.png") );
		view.add(layoutFR);

		Option layoutKK = new Option("Kamada-Kawai layout", new LayoutAction( app, figure, new KamadaKawaiLayout() ) );
		layoutKK.setIcon( app.url("icons/spiral.png") );
		view.add(layoutKK);

		Option layoutHierarchical = new Option("Hierarchical layout", new LayoutAction( app, figure, new HierarchicalLayout() ) );
		layoutHierarchical.setIcon( app.url("icons/spiral.png") );
		view.add(layoutHierarchical);

		Option layoutRadial = new Option("Radial layout", new LayoutAction( app, figure, new RadialLayout() ) );
		layoutRadial.setIcon( app.url("icons/spiral.png") );
		//view.add(layoutRadial);

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

		Menu nodeSize = new Menu("Node size");
		nodeSize.setIcon( app.url("icons/size.png") );
		view.add(nodeSize);
		
		Option nodeSizeIncrease = new Option ("Increase node size", new NodeSizeAction(app,figure,+1), KeyEvent.VK_F6 );
		nodeSizeIncrease.setIcon( app.url("icons/size.png") );
		nodeSize.add(nodeSizeIncrease);

		Option nodeSizeDecrease = new Option ("Decrease node size", new NodeSizeAction(app,figure,-1), KeyEvent.VK_F5 );
		nodeSizeDecrease.setIcon( app.url("icons/size-flip.png") );
		nodeSize.add(nodeSizeDecrease);

		Menu nodeStyle = new Menu("Node style");
		nodeStyle.setIcon( app.url("icons/paint.png") );
		view.add(nodeStyle);

		Option nodeStyle3D = new Option ("3D", new NodeStyleAction(app,figure, RadialGradientNodeRenderer.class) );
		nodeStyle3D.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyle3D);

		Option nodeStyleFlat = new Option ("Flat", new NodeStyleAction(app,figure, LinearGradientNodeRenderer.class) );
		nodeStyleFlat.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleFlat);

		Option nodeStyleSolid = new Option ("Solid", new NodeStyleAction(app,figure, ColorMapNodeRenderer.class) );
		nodeStyleSolid.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleSolid);
		
		nodeStyle.add(new Separator());

		Option nodeStyleJet = new Option ("Color", new NodeStyleAction(app,figure, new JetColorMap(256)) );
		nodeStyleJet.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleJet);

		Option nodeStyleGrayscale = new Option ("Grayscale", new NodeStyleAction(app,figure, new InverseGrayscaleColorMap(256)) );
		nodeStyleGrayscale.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleGrayscale);

		Option nodeStyleReset = new Option ("Reset", new NodeStyleResetAction(app, figure) );
		nodeStyleReset.setIcon( app.url("icons/paint.png") );
		nodeStyle.add(nodeStyleReset);
				
		view.add ( new Separator() );
		
		Option linkAttributes = new Option("Link attributes...", new ForwardAction (new LinkAttributesUIModel(app, figure) ));
		linkAttributes.setIcon( app.url("icons/palette.png") );
		view.add(linkAttributes);
				
		Menu linkWidth = new Menu("Link width");
		linkWidth.setIcon( app.url("icons/size.png") );
		view.add(linkWidth);

		Option linkWidthIncrease = new Option ("Increase link width", new LinkWidthAction(app,figure,+1), KeyEvent.VK_F8 );
		linkWidthIncrease.setIcon( app.url("icons/size.png") );
		linkWidth.add(linkWidthIncrease);

		Option linkWidthDecrease = new Option ("Decrease link width", new LinkWidthAction(app,figure,-1), KeyEvent.VK_F7 );
		linkWidthDecrease.setIcon( app.url("icons/size-flip.png") );
		linkWidth.add(linkWidthDecrease);
		
		Menu linkStyle = new Menu("Link style");
		linkStyle.setIcon( app.url("icons/paint.png") );
		view.add(linkStyle);
		
		Option linkStyleJet = new Option ("Color", new LinkStyleAction(app, figure, new JetColorMap(256)) );
		linkStyleJet.setIcon( app.url("icons/paint.png") );
		linkStyle.add(linkStyleJet);

		Option linkStyleGrayscale = new Option ("Grayscale", new LinkStyleAction(app, figure, new InverseGrayscaleColorMap(256)) );
		linkStyleGrayscale.setIcon( app.url("icons/paint.png") );
		linkStyle.add(linkStyleGrayscale);

		Option linkStyleReset = new Option ("Reset", new LinkStyleResetAction(app, figure) );
		linkStyleReset.setIcon( app.url("icons/paint.png") );
		linkStyle.add(linkStyleReset);
		
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

		data.setIcon( app.url("icons/chart.png") );
		
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
	
	public Menu createAnalysisMenu (Application app, NetworkModel model, NetworkFigure figure)
	{
		Menu analysis = new Menu("Analysis");

		analysis.setIcon( app.url("icons/microscope.png") );
		
		// Node degree
		
		Menu degree = new Menu("Degree");
		degree.setIcon( app.url("icons/microscope.png") );
		analysis.add(degree);
		
		Option inDegree = new Option("In-degree", new NodeScoreAction(app, model, InDegree.class) );
		inDegree.setIcon( app.url("icons/microscope.png") );
		degree.add(inDegree);

		Option outDegree = new Option("Out-degree", new NodeScoreAction(app, model, OutDegree.class) );
		outDegree.setIcon( app.url("icons/microscope.png") );
		degree.add(outDegree);

		Option totalDegree = new Option("Total degree (in+out)", new NodeScoreAction(app, model, Degree.class) );
		totalDegree.setIcon( app.url("icons/microscope.png") );
		degree.add(totalDegree);

		Option inDegreeNormalized = new Option("Normalized in-degree", new NodeScoreAction(app, model, NormalizedInDegree.class) );
		inDegreeNormalized.setIcon( app.url("icons/microscope.png") );
		degree.add(inDegreeNormalized);

		Option outDegreeNormalized = new Option("Normalized out-degree", new NodeScoreAction(app, model, NormalizedOutDegree.class) );
		outDegreeNormalized.setIcon( app.url("icons/microscope.png") );
		degree.add(outDegreeNormalized);
		
		Option normalizedDegree = new Option("Normalized total degree", new NodeScoreAction(app, model, NormalizedDegree.class) );
		normalizedDegree.setIcon( app.url("icons/microscope.png") );
		degree.add(normalizedDegree);

		Option localAssortativity = new Option("Degree assortativity", new NodeScoreAction(app, model, DegreeAssortativity.class) );
		localAssortativity.setIcon( app.url("icons/microscope.png") );
		degree.add(localAssortativity);		
		
		Option unbiasedLocalAssortativity = new Option("Unbiased degree assortativity", new NodeScoreAction(app, model, UnbiasedDegreeAssortativity.class) );
		unbiasedLocalAssortativity.setIcon( app.url("icons/microscope.png") );
		degree.add(unbiasedLocalAssortativity);		
		
		// Node reachability
		
		Menu reachability = new Menu("Reachability");
		reachability.setIcon( app.url("icons/microscope.png") );
		analysis.add(reachability);
		
		Option eccentricity = new Option("Eccentricity", new NodeScoreAction(app, model, Eccentricity.class) );
		eccentricity.setIcon( app.url("icons/microscope.png") );
		reachability.add(eccentricity);

		Option avl = new Option("Average path length", new NodeScoreAction(app, model, AveragePathLength.class) );
		avl.setIcon( app.url("icons/microscope.png") );
		reachability.add(avl);

		Option closeness = new Option("Closeness", new NodeScoreAction(app, model, Closeness.class) );
		closeness.setIcon( app.url("icons/microscope.png") );
		reachability.add(closeness);		

		Option adjustedCloseness = new Option("Adjusted closeness", new NodeScoreAction(app, model, AdjustedCloseness.class) );
		adjustedCloseness.setIcon( app.url("icons/microscope.png") );
		reachability.add(adjustedCloseness);		
		
		Option decay = new Option("Decay", new NodeScoreAction(app, model, Decay.class) ); 
		decay.setIcon( app.url("icons/microscope.png") );
		reachability.add(decay);
		
		Option normalizedDecay = new Option("Normalized decay", new NodeScoreAction(app, model, NormalizedDecay.class) );
		normalizedDecay.setIcon( app.url("icons/microscope.png") );
		reachability.add(normalizedDecay);

		
		// Node betweenness
		
		Menu betweenness = new Menu("Betweenness" );
		betweenness.setIcon( app.url("icons/microscope.png") );
		analysis.add(betweenness);
		
		Option adjustedBetweenness = new Option("Betweenness", new NodeScoreAction(app, model, AdjustedBetweenness.class) );
		adjustedBetweenness.setIcon( app.url("icons/microscope.png") );
		betweenness.add(adjustedBetweenness);		

		Option totalBetweenness = new Option("Betweenness score", new NodeScoreAction(app, model, Betweenness.class) );
		totalBetweenness.setIcon( app.url("icons/microscope.png") );
		betweenness.add(totalBetweenness);		

		Option normalizedBetweenness = new Option("Normalized betweenness", new NodeScoreAction(app, model, NormalizedBetweenness.class) );
		normalizedBetweenness.setIcon( app.url("icons/microscope.png") );
		betweenness.add(normalizedBetweenness);		

		// Node influence
		
		Menu influence = new Menu("Influence" );
		influence.setIcon( app.url("icons/microscope.png") );
		analysis.add(influence);
		
		Option pagerank = new Option("PageRank", new NodeScoreAction(app, model, PageRank.class) );
		pagerank.setIcon( app.url("icons/microscope.png") );
		influence.add(pagerank);		

		Option hits = new Option("HITS: Hubs & Authorities", new NodeMultiScoreAction(app, model, HITS.class) );
		hits.setIcon( app.url("icons/microscope.png") );
		influence.add(hits);		

		Option eigenvector = new Option("Eigenvector centrality", new NodeScoreAction(app, model, EigenvectorCentrality.class) );
		eigenvector.setIcon( app.url("icons/microscope.png") );
		influence.add(eigenvector);		

		Option katz = new Option("Katz centrality", new NodeScoreAction(app, model, KatzCentrality.class) );
		katz.setIcon( app.url("icons/microscope.png") );
		influence.add(katz);		

		Option diffCentrality = new Option("Diffusion centrality", new NodeScoreAction(app, model, DiffusionCentrality.class) );
		diffCentrality.setIcon( app.url("icons/microscope.png") );
		influence.add(diffCentrality);

		
		// Links
		
		Menu links = new Menu("Links");
		links.setIcon( app.url("icons/microscope.png") );
		analysis.add(links);
		
		Option linkBetweenness = new Option("Link betweenness", new LinkScoreAction(app, model, LinkBetweenness.class) );
		linkBetweenness.setIcon( app.url("icons/microscope.png") );
		links.add(linkBetweenness);		

		Option linkEmbeddedness = new Option("Link embeddedness", new LinkScoreAction(app, model, LinkEmbeddedness.class) );
		linkEmbeddedness.setIcon( app.url("icons/microscope.png") );
		links.add(linkEmbeddedness);		
		
		Option linkNeighborhoodOverlap = new Option("Link neighborhood overlap", new LinkScoreAction(app, model, LinkNeighborhoodOverlap.class) );
		linkNeighborhoodOverlap.setIcon( app.url("icons/microscope.png") );
		links.add(linkNeighborhoodOverlap);		
	
		Option linkNeighborhoodSize = new Option("Link neighborhood size", new LinkScoreAction(app, model, LinkNeighborhoodSize.class) );
		linkNeighborhoodSize.setIcon( app.url("icons/microscope.png") );
		links.add(linkNeighborhoodSize);		

		Option linkRays = new Option("Link rays", new LinkScoreAction(app, model, LinkRays.class) );
		linkRays.setIcon( app.url("icons/microscope.png") );
		links.add(linkRays);		
		
		
		// Communities
		
		Menu communities = new Menu("Communities");
		communities.setIcon( app.url("icons/microscope.png") );
		analysis.add(communities);

		Option scc = new Option("Connected components", new NodeMultiScoreAction(app, model, ConnectedComponents.class) );
		scc.setIcon( app.url("icons/spiral.png") );
		communities.add(scc);		

		communities.add( new Separator() );

		Option cdKL = new Option("Kernighan-Lin partitioning", new CommunityDetectionAction(app, figure, model, KernighanLinCommunityDetector.class) );
		cdKL.setIcon( app.url("icons/spiral.png") );
		communities.add(cdKL);		

		Option cdNewmanGirvan = new Option("Newman-Girvan's community detection", new CommunityDetectionAction(app, figure, model, NewmanGirvanCommunityDetector.class) );
		cdNewmanGirvan.setIcon( app.url("icons/spiral.png") );
		communities.add(cdNewmanGirvan);		

		Option cdRadicchi = new Option("Radicchi's community detection", new CommunityDetectionAction(app, figure, model, RadicchiCommunityDetector.class) );
		cdRadicchi.setIcon( app.url("icons/spiral.png") );
		communities.add(cdRadicchi);		
		
		communities.add( new Separator() );

		Option cdSLink = new Option("Single-link hierarchical community detection", new CommunityDetectionAction(app, figure, model, SingleLinkCommunityDetector.class) );
		cdSLink.setIcon( app.url("icons/spiral.png") );
		communities.add(cdSLink);		

		Option cdALink = new Option("Average-link hierarchical community detection", new CommunityDetectionAction(app, figure, model, AverageLinkCommunityDetector.class) );
		cdALink.setIcon( app.url("icons/spiral.png") );
		communities.add(cdALink);		

		Option cdCLink = new Option("Complete-link hierarchical community detection", new CommunityDetectionAction(app, figure, model, CompleteLinkCommunityDetector.class) );
		cdCLink.setIcon( app.url("icons/spiral.png") );
		communities.add(cdCLink);		
		
		communities.add( new Separator() );

		Option cdFastGreedy = new Option("Fast greedy community detection", new CommunityDetectionAction(app, figure, model, FastGreedyCommunityDetector.class) );
		cdFastGreedy.setIcon( app.url("icons/spiral.png") );
		communities.add(cdFastGreedy);		

		Option cdMultiStepGreedy = new Option("Multi-step greedy community detection", new CommunityDetectionAction(app, figure, model, MultiStepGreedyCommunityDetector.class) );
		cdMultiStepGreedy.setIcon( app.url("icons/spiral.png") );
		communities.add(cdMultiStepGreedy);		
		
		communities.add( new Separator() );

		Option cdEIG1 = new Option("EIG1 spectral community detection", new CommunityDetectionAction(app, figure, model, EIG1CommunityDetector.class) );
		cdEIG1.setIcon( app.url("icons/spiral.png") );
		communities.add(cdEIG1);		

		Option cdKNSC1 = new Option("KNSC1 spectral community detection", new CommunityDetectionAction(app, figure, model, NJWCommunityDetector.class) );
		cdKNSC1.setIcon( app.url("icons/spiral.png") );
		communities.add(cdKNSC1);		

		Option cdUKMeans = new Option("UKMeans spectral community detection", new CommunityDetectionAction(app, figure, model, UKMeansCommunityDetector.class) );
		cdUKMeans.setIcon( app.url("icons/spiral.png") );
		communities.add(cdUKMeans);		

		communities.add( new Separator() );

		Option cdBigClam = new Option("BigClam community detection", new CommunityDetectionAction(app, figure, model, BigClamCommunityDetector.class) );
		cdBigClam.setIcon( app.url("icons/spiral.png") );
		communities.add(cdBigClam);		
		
		// Clustering coefficients
		
		Option cc = new Option("Clustering coefficient", new NodeScoreAction(app, model, ClusteringCoefficient.class) );
		cc.setIcon( app.url("icons/microscope.png") );
		analysis.add(cc);	
		
		// Network statistics
		
		Option statistics = new Option ("Network statistics", new StatsAction(app) );
		statistics.setIcon( app.url("icons/microscope.png") );
		analysis.add(statistics);		
		
		return analysis;
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
