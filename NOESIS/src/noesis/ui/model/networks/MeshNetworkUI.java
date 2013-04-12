package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.model.regular.MeshNetwork;
import noesis.model.regular.RegularNetwork;


public class MeshNetworkUI extends UIModel 
{
	Editor<Integer> rowEditor;
	Editor<Integer> columnEditor;
	
	public MeshNetworkUI (Application app) 
	{
		super(app, "New mesh network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel nodeCountModel = new IntegerModel();
		nodeCountModel.setMinimumValue(0);
		nodeCountModel.setMaximumValue(100);
		
		rowEditor = new Editor<Integer>("Number of mesh rows", nodeCountModel);
		rowEditor.setIcon( app.url("icons/calculator.png") );
		rowEditor.setData(10);
		add(rowEditor);

		columnEditor = new Editor<Integer>("Number of mesh columns", nodeCountModel);
		columnEditor.setIcon( app.url("icons/calculator.png") );
		columnEditor.setData(10);
		add(columnEditor);
		
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new MeshNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class MeshNetworkAction extends Action 
	{
		private MeshNetworkUI ui;

		public MeshNetworkAction (MeshNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int rows = ui.rowEditor.getData();
			int columns = ui.columnEditor.getData();
			
			RegularNetwork regular = new MeshNetwork(rows, columns);
			
			AttributeNetwork network = new AttributeNetwork(regular);
			
			// Initial layout

			Attribute<Double> x = new Attribute<Double>("x");
			Attribute<Double> y = new Attribute<Double>("y");
			
			network.addNodeAttribute( x );
			network.addNodeAttribute( y );
						
			for (int i=0; i<rows; i++) {
				for (int j=0; j<columns; j++) {
					x.set(i*columns+j, 0.05 + (0.9*j)/(columns-1) );
					y.set(i*columns+j, 0.05 + (0.9*i)/(rows-1) );
				}
			}
						
			ui.getApplication().set("network", network);
			ui.getApplication().exit(ui);
		}	
	}	

}
