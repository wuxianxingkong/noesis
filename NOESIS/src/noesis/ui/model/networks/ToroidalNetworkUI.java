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
			
			// Initial layout

			Attribute<Double> x = new Attribute<Double>("x");
			Attribute<Double> y = new Attribute<Double>("y");
			
			network.addNodeAttribute( x );
			network.addNodeAttribute( y );
						
			for (int i=0; i<rows; i++) {
				for (int j=0; j<columns; j++) {
					x.set(i*columns+j, 0.1 + (0.8*j)/(columns-1) );
					y.set(i*columns+j, 0.1 + (0.8*i)/(rows-1) );
					
					if ((i==0) || (i==rows-1)) 
						x.set(i*columns+j, x.get(i*columns+j) + Math.min(0.05, 0.4/(columns-1)) );					
					
					if ((j==0) || (j==columns-1))
						y.set(i*columns+j, y.get(i*columns+j) - Math.min(0.05, 0.4/(rows-1)) );
				}
			}
						
			ui.getApplication().set("network", network);
			ui.getApplication().exit(ui);
		}	
	}	

}
