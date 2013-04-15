package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.LinearLayout;

import noesis.model.regular.RegularNetwork;
import noesis.model.regular.TandemNetwork;


public class TandemNetworkUI extends NewNetworkUI 
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
			AttributeNetwork network = createAttributeNetwork(regular, "Tandem network", new LinearLayout ());			
						
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
