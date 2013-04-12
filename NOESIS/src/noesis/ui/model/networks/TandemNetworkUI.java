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
import noesis.model.regular.TandemNetwork;


public class TandemNetworkUI extends UIModel 
{
	Editor<Integer> nodeCountEditor;
	
	public TandemNetworkUI (Application app) 
	{
		super(app, "New tandem network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(100);
		
		nodeCountEditor = new Editor<Integer>("Number of tandem nodes", nodeCountModel);
		nodeCountEditor.setIcon( app.url("icons/calculator.png") );
		nodeCountEditor.setData(15);
		add(nodeCountEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new TandemNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class TandemNetworkAction extends Action 
	{
		private TandemNetworkUI ui;

		public TandemNetworkAction (TandemNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int nodes = ui.nodeCountEditor.getData();
			
			RegularNetwork regular = new TandemNetwork(nodes);
			
			AttributeNetwork network = new AttributeNetwork(regular);
			
			// Initial layout

			Attribute<Double> x = new Attribute<Double>("x");
			Attribute<Double> y = new Attribute<Double>("y");
			
			network.addNodeAttribute( x );
			network.addNodeAttribute( y );
						
			for (int i=0; i<network.size(); i++) {
				x.set(i, 0.1 + (0.8*i)/(nodes-1) );
				y.set(i, 0.5 );
			}
						
			ui.getApplication().set("network", network);
			ui.getApplication().exit(ui);
		}	
	}	

}
