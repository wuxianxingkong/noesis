package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.CircularLayout;

import noesis.model.random.PriceCitationNetwork;


public class PriceCitationNetworkUI extends NewNetworkUI 
{
	Editor<Integer> nodeCountEditor;
	Editor<Integer> degreeEditor;
	Editor<Integer>  freeEditor;
	
	public PriceCitationNetworkUI (Application app) 
	{
		super(app, "New Price's citation network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(1000);
		
		nodeCountEditor = new Editor<Integer>("Number of network nodes", nodeCountModel);
		nodeCountEditor.setIcon( app.url("icons/calculator.png") );
		nodeCountEditor.setData(50);
		add(nodeCountEditor);
		
		IntegerModel degreeModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(1000);
		
		degreeEditor = new Editor<Integer>("Citations per paper (mean out-degree)", degreeModel);
		degreeEditor.setIcon( app.url("icons/calculator.png") );
		degreeEditor.setData(4);
		add(degreeEditor);
		
		freeEditor = new Editor<Integer>("Random citations ('free' citations)", degreeModel);
		freeEditor.setIcon( app.url("icons/calculator.png") );
		freeEditor.setData(1);
		add(freeEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new PriceCitationNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class PriceCitationNetworkAction extends Action 
	{
		private PriceCitationNetworkUI ui;

		public PriceCitationNetworkAction (PriceCitationNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int nodes = ui.nodeCountEditor.getData();
			int citations = ui.degreeEditor.getData();
			int free = ui.freeEditor.getData();
			
			PriceCitationNetwork random = new PriceCitationNetwork(nodes, citations, free);
			AttributeNetwork network = createAttributeNetwork(random, "Price's citation network", new CircularLayout ());			
			
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
