package noesis.ui.model.networks;

import ikor.model.data.IntegerModel;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.HypercubeLayout;

import noesis.model.regular.HypercubeNetwork;
import noesis.model.regular.RegularNetwork;


public class HypercubeNetworkUI extends NewNetworkUI 
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
			AttributeNetwork network = createAttributeNetwork(regular, "Hypercube network", new HypercubeLayout ());			
			
			ui.set("network", network);
			ui.exit();
		}			
		
	}	

}
