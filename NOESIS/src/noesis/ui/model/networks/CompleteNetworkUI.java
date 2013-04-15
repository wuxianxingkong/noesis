package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.CircularLayout;

import noesis.model.regular.CompleteNetwork;
import noesis.model.regular.RegularNetwork;


public class CompleteNetworkUI extends NewNetworkUI 
{
	Editor<Integer> nodeCountEditor;
	
	public CompleteNetworkUI (Application app) 
	{
		super(app, "New complete network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(100);
		
		nodeCountEditor = new Editor<Integer>("Number of network nodes", nodeCountModel);
		nodeCountEditor.setIcon( app.url("icons/calculator.png") );
		nodeCountEditor.setData(15);
		add(nodeCountEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new CompleteNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class CompleteNetworkAction extends Action 
	{
		private CompleteNetworkUI ui;

		public CompleteNetworkAction (CompleteNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int nodes = ui.nodeCountEditor.getData();
			
			RegularNetwork regular = new CompleteNetwork(nodes);
			AttributeNetwork network = createAttributeNetwork(regular, "Complete network", new CircularLayout ());			
										
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
