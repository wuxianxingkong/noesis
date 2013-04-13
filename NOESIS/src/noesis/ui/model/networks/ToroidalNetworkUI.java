package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.Attribute;
import noesis.AttributeNetwork;

import noesis.algorithms.visualization.NetworkLayout;
import noesis.algorithms.visualization.ToroidalLayout;

import noesis.model.regular.RegularNetwork;
import noesis.model.regular.ToroidalNetwork;


public class ToroidalNetworkUI extends UIModel 
{
	Editor<Integer> rowEditor;
	Editor<Integer> columnEditor;
	
	public ToroidalNetworkUI (Application app) 
	{
		super(app, "New 2D toroidal network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(100);
		
		rowEditor = new Editor<Integer>("Number of torus rows", nodeCountModel);
		rowEditor.setIcon( app.url("icons/calculator.png") );
		rowEditor.setData(10);
		add(rowEditor);

		columnEditor = new Editor<Integer>("Number of torus columns", nodeCountModel);
		columnEditor.setIcon( app.url("icons/calculator.png") );
		columnEditor.setData(10);
		add(columnEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ToroidalNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class ToroidalNetworkAction extends Action 
	{
		private ToroidalNetworkUI ui;

		public ToroidalNetworkAction (ToroidalNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int rows = ui.rowEditor.getData();
			int columns = ui.columnEditor.getData();
			
			RegularNetwork regular = new ToroidalNetwork(rows, columns);
			
			AttributeNetwork network = new AttributeNetwork(regular);

			network.addNodeAttribute( new Attribute<Double>("x") );
			network.addNodeAttribute( new Attribute<Double>("y") );
			
			NetworkLayout display = new ToroidalLayout(rows,columns);
			
			display.layout(network);
																	
			ui.set("network", network);
			ui.exit();
		}	
	}	

}
