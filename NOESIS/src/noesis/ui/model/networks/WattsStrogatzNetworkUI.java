package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.CircularLayout;

import noesis.model.random.WattsStrogatzNetwork;


public class WattsStrogatzNetworkUI extends NewNetworkUI 
{
	Editor<Integer> nodeEditor;
	Editor<Integer> degreeEditor;
	Editor<Double>  probabilityEditor;
	
	public WattsStrogatzNetworkUI (Application app) 
	{
		super(app, "New Watts-Strogatz small world network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(1000);
		
		nodeEditor = new Editor<Integer>("Number of network nodes", nodeCountModel);
		nodeEditor.setIcon( app.url("icons/calculator.png") );
		nodeEditor.setData(50);
		add(nodeEditor);
		
		IntegerModel degreeModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(1000);
		
		degreeEditor = new Editor<Integer>("Mean degree", degreeModel);
		degreeEditor.setIcon( app.url("icons/calculator.png") );
		degreeEditor.setData(4);
		add(degreeEditor);
		
		RealModel probabilityModel = new RealModel();
		probabilityModel.setMinimumValue( 0.0 );
		probabilityModel.setMaximumValue( 1.0 );
		
		probabilityEditor = new Editor<Double>("Link rewiring probability", probabilityModel);
		probabilityEditor.setIcon( app.url("icons/calculator.png") );
		probabilityEditor.setData( 0.1 );
		add(probabilityEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new WattsStrogatzNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class WattsStrogatzNetworkAction extends Action 
	{
		private WattsStrogatzNetworkUI ui;

		public WattsStrogatzNetworkAction (WattsStrogatzNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int nodes = ui.nodeEditor.getData();
			int neighbors = ui.degreeEditor.getData();
			double rewiringProbability = ui.probabilityEditor.getData();
			
			WattsStrogatzNetwork random = new WattsStrogatzNetwork(nodes, neighbors, rewiringProbability);
			AttributeNetwork network = createAttributeNetwork(random, "Watts-Strogatz's small world network", new CircularLayout ());			
			
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
