package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.FruchtermanReingoldLayout;

import noesis.model.random.BarabasiAlbertNetwork;


public class BarabasiAlbertNetworkUI extends NewNetworkUI 
{
	Editor<Integer> nodeEditor;
	Editor<Integer> linkEditor;
	
	public BarabasiAlbertNetworkUI (Application app) 
	{
		super(app, "New Barabasi-Albert network...");
		
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
		
		linkEditor = new Editor<Integer>("Links for each new node", degreeModel);
		linkEditor.setIcon( app.url("icons/calculator.png") );
		linkEditor.setData(2);
		add(linkEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new BarabasiAlbertNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class BarabasiAlbertNetworkAction extends Action 
	{
		private BarabasiAlbertNetworkUI ui;

		public BarabasiAlbertNetworkAction (BarabasiAlbertNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int nodes = ui.nodeEditor.getData();
			int links = ui.linkEditor.getData();
			
			BarabasiAlbertNetwork random = new BarabasiAlbertNetwork(nodes, links);
			AttributeNetwork network = createAttributeNetwork(random, "Barabasi-Albert's preferential attachment network", new FruchtermanReingoldLayout() );			
			
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
