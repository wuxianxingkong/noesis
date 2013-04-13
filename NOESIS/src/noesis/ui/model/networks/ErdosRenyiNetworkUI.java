package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.Attribute;
import noesis.AttributeNetwork;

import noesis.algorithms.visualization.NetworkLayout;
import noesis.algorithms.visualization.RandomLayout;

import noesis.model.random.ErdosRenyiNetwork;


public class ErdosRenyiNetworkUI extends UIModel 
{
	Editor<Integer> nodeCountEditor;
	Editor<Double>  probabilityEditor;
	
	public ErdosRenyiNetworkUI (Application app) 
	{
		super(app, "New Erdös-Renyi network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(1000);
		
		nodeCountEditor = new Editor<Integer>("Number of network nodes", nodeCountModel);
		nodeCountEditor.setIcon( app.url("icons/calculator.png") );
		nodeCountEditor.setData(100);
		add(nodeCountEditor);
		
		RealModel probabilityModel = new RealModel();
		probabilityModel.setMinimumValue( 0.0 );
		probabilityModel.setMaximumValue( 1.0 );
		
		probabilityEditor = new Editor<Double>("Link probability", probabilityModel);
		probabilityEditor.setIcon( app.url("icons/calculator.png") );
		probabilityEditor.setData( 0.1 );
		add(probabilityEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ErdosRenyiNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class ErdosRenyiNetworkAction extends Action 
	{
		private ErdosRenyiNetworkUI ui;

		public ErdosRenyiNetworkAction (ErdosRenyiNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int nodes = ui.nodeCountEditor.getData();
			int links = (int) ( ui.probabilityEditor.getData() * nodes * (nodes-1))/2;
			
			ErdosRenyiNetwork random = new ErdosRenyiNetwork(nodes,links);
			
			AttributeNetwork network = new AttributeNetwork(random);
			
			network.addNodeAttribute( new Attribute<Double>("x") );
			network.addNodeAttribute( new Attribute<Double>("y") );
			
			NetworkLayout display = new RandomLayout ();
			
			display.layout(network);
									
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
