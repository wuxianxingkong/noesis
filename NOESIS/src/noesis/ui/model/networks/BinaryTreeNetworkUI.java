package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.Attribute;
import noesis.AttributeNetwork;

import noesis.algorithms.visualization.BinaryTreeLayout;
import noesis.algorithms.visualization.NetworkLayout;

import noesis.model.regular.BinaryTreeNetwork;


public class BinaryTreeNetworkUI extends UIModel 
{
	Editor<Integer> sizeEditor;
	
	public BinaryTreeNetworkUI (Application app) 
	{
		super(app, "New binary tree network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel dimensionModel = new IntegerModel();
		dimensionModel.setMinimumValue(0);
		dimensionModel.setMaximumValue(1000);
		
		sizeEditor = new Editor<Integer>("Number of tree nodes", dimensionModel);
		sizeEditor.setIcon( app.url("icons/calculator.png") );
		sizeEditor.setData(15);
		add(sizeEditor);
	
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new BinaryTreeNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class BinaryTreeNetworkAction extends Action 
	{
		private BinaryTreeNetworkUI ui;

		public BinaryTreeNetworkAction (BinaryTreeNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int size = ui.sizeEditor.getData();
			
			BinaryTreeNetwork tree = new BinaryTreeNetwork(size);
			
			AttributeNetwork network = new AttributeNetwork(tree);
			
			network.addNodeAttribute( new Attribute<Double>("x") );
			network.addNodeAttribute( new Attribute<Double>("y") );
			
			NetworkLayout display = new BinaryTreeLayout();
			
			display.layout(network);
									
			ui.set("network", network);
			ui.exit();
		}			
	}	

}
