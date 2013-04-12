package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.model.regular.CompleteNetwork;
import noesis.model.regular.RegularNetwork;


public class CompleteNetworkUI extends UIModel 
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
			
			AttributeNetwork network = new AttributeNetwork(regular);
			
			// Initial layout

			Attribute<Double> x = new Attribute<Double>("x");
			Attribute<Double> y = new Attribute<Double>("y");
			
			network.addNodeAttribute( x );
			network.addNodeAttribute( y );
			
			for (int i=0; i<network.size(); i++) {
				x.set(i, 0.5 + 0.45*Math.cos(i*2*Math.PI/nodes));
				y.set(i, 0.5 + 0.45*Math.sin(i*2*Math.PI/nodes));
			}
						
			ui.getApplication().set("network", network);
			ui.getApplication().exit(ui);
		}	
	}	

}
