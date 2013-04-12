package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.model.regular.HypercubeNetwork;
import noesis.model.regular.RegularNetwork;


public class HypercubeNetworkUI extends UIModel 
{
	Editor<Integer> dimensionEditor;
	Editor<Integer> columnEditor;
	
	public HypercubeNetworkUI (Application app) 
	{
		super(app, "New hypercube network...");
		
		setIcon( app.url("icon.gif") );
		
		IntegerModel dimensionModel = new IntegerModel();
		dimensionModel.setMinimumValue(0);
		dimensionModel.setMaximumValue(10);
		
		dimensionEditor = new Editor<Integer>("Hypercube dimension", dimensionModel);
		dimensionEditor.setIcon( app.url("icons/calculator.png") );
		dimensionEditor.setData(3);
		add(dimensionEditor);
	
		Option ok = new Option("Create");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new HypercubeNetworkAction(this) );
		add(ok);		
	}
	
	// Action
	
	public class HypercubeNetworkAction extends Action 
	{
		private HypercubeNetworkUI ui;

		public HypercubeNetworkAction (HypercubeNetworkUI ui)
		{
			this.ui = ui;
		}

		@Override
		public void run() 
		{
			int dimension = ui.dimensionEditor.getData();
			
			RegularNetwork regular = new HypercubeNetwork(dimension);
			
			AttributeNetwork network = new AttributeNetwork(regular);
			
			// Initial layout

			Attribute<Double> x = new Attribute<Double>("x");
			Attribute<Double> y = new Attribute<Double>("y");
			
			network.addNodeAttribute( x );
			network.addNodeAttribute( y );
			
			double maxX = xcoord(network.size()-1, dimension);
			double maxY = ycoord(network.size()-1, dimension);
						
			for (int i=0; i<network.size(); i++) {
				x.set(i, 0.1 + 0.8*xcoord(i,dimension)/maxX );
				y.set(i, 0.1 + 0.8*ycoord(i,dimension)/maxY );
			}
						
			ui.getApplication().set("network", network);
			ui.getApplication().exit(ui);
		}	
		
		public double xcoord (int i, int d)
		{
			return alternativeBits(i,d) + ((d%2==1)?0:msb(i,d));
		}
		
		public double ycoord (int i, int d)
		{
			return alternativeBits(i>>1,d) + ((d%2==0)?0:msb(i,d));
		}
		
		public int alternativeBits (int i, int d)
		{
			if (i==0)
				return 0;
			else
				return (i%2)*(1<<(d/2)) + alternativeBits(i>>2,d-2);
		}
		
		public int msb (int i, int d)
		{
			return i>>(d-1);
		}
		
		
	}	

}
