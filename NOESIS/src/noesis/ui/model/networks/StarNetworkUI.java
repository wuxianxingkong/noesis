package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.StarLayout;

import noesis.model.regular.RegularNetwork;
import noesis.model.regular.StarNetwork;


public class StarNetworkUI extends NewNetworkUI 
{
	Editor<Integer> nodeCountEditor;
	
	public StarNetworkUI (Application app) 
	{
		super(app, "New star network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(101);
		
		nodeCountEditor = new Editor<Integer>("Number of network nodes", nodeCountModel);
		nodeCountEditor.setIcon( app.url("icons/calculator.png") );
		nodeCountEditor.setData(15);
		add(nodeCountEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new StarNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class StarNetworkAction extends Action 
	{
		private StarNetworkUI ui;

		public StarNetworkAction (StarNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int nodes = ui.nodeCountEditor.getData();
			
			RegularNetwork regular = new StarNetwork(nodes);
			AttributeNetwork network = createAttributeNetwork(regular, "Star network", new StarLayout ());			
						
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
