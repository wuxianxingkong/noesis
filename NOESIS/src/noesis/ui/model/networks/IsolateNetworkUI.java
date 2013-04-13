package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.algorithms.visualization.CircularLayout;
import noesis.algorithms.visualization.NetworkLayout;
import noesis.model.regular.IsolateNetwork;


public class IsolateNetworkUI extends UIModel 
{
	Editor<Integer> nodeCountEditor;
	
	public IsolateNetworkUI (Application app) 
	{
		super(app, "New isolate network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(100);
		
		nodeCountEditor = new Editor<Integer>("Number of isolated nodes", nodeCountModel);
		nodeCountEditor.setIcon( app.url("icons/calculator.png") );
		nodeCountEditor.setData(15);
		add(nodeCountEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new IsolateNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class IsolateNetworkAction extends Action 
	{
		private IsolateNetworkUI ui;

		public IsolateNetworkAction (IsolateNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int nodes = ui.nodeCountEditor.getData();
			
			IsolateNetwork regular = new IsolateNetwork(nodes);
			
			AttributeNetwork network = new AttributeNetwork(regular);
			
			network.addNodeAttribute( new Attribute<Double>("x") );
			network.addNodeAttribute( new Attribute<Double>("y") );
			
			NetworkLayout display = new CircularLayout ();
			
			display.layout(network);
						
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
