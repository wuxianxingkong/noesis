package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.Attribute;
import noesis.AttributeNetwork;
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
			
			// Initial layout

			Attribute<Double> x = new Attribute<Double>("x");
			Attribute<Double> y = new Attribute<Double>("y");
			
			network.addNodeAttribute( x );
			network.addNodeAttribute( y );
			
			x.set(0, 0.5);
			y.set(0, 0.1);
			
			int depth = tree.height();
			
			for (int i=1; i<network.size(); i++) {
				int level = tree.height(i);
				int levelNodes = 1<<level;
				double space = 1.0/(2*levelNodes);
				x.set(i, 0.1 + 0.8 * ( space + ((double)(i - levelNodes + 1)) / levelNodes ) );
				y.set(i, 0.1 + (0.8*tree.height(i))/depth );
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
