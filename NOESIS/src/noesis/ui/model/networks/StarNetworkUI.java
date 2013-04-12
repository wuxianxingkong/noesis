package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.model.regular.RegularNetwork;
import noesis.model.regular.StarNetwork;


public class StarNetworkUI extends UIModel 
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
			
			AttributeNetwork network = new AttributeNetwork(regular);
			
			// Initial layout

			Attribute<Double> x = new Attribute<Double>("x");
			Attribute<Double> y = new Attribute<Double>("y");
			
			network.addNodeAttribute( x );
			network.addNodeAttribute( y );
			
			x.set(0, 0.5);
			y.set(0, 0.5);
			
			for (int i=1; i<network.size(); i++) {
				x.set(i, 0.5 + 0.45*Math.cos(i*2*Math.PI/(nodes-1)));
				y.set(i, 0.5 + 0.45*Math.sin(i*2*Math.PI/(nodes-1)));
			}
						
			ui.getApplication().set("network", network);
			ui.getApplication().exit(ui);
		}	
	}	

}
