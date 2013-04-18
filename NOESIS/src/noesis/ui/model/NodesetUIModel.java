package noesis.ui.model;

import noesis.AttributeNetwork;

import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.data.DataModel;
import ikor.model.data.Dataset;
import ikor.model.ui.Application;
import ikor.model.ui.DatasetViewer;
import ikor.model.ui.UIModel;

public class NodesetUIModel extends UIModel 
{

	public NodesetUIModel (Application app, NetworkModel data) 
	{
		super(app, "Network nodes");
		setIcon( app.url("icons/chart.png") );
		
		NodeDataset dataset = new NodeDataset();
		
		DatasetViewer control = new DatasetViewer("Node data viewer", dataset.getModel());
		add(control);
		
		data.addObserver( new NodesetObserver(dataset,control) );		
	}
	
	
	public class NodesetObserver implements Observer<AttributeNetwork>
	{
		private NodeDataset dataset;
		private DatasetViewer control;
		
		public NodesetObserver (NodeDataset dataset, DatasetViewer control)
		{
			this.dataset = dataset;
			this.control = control;
		}

		@Override
		public void update (Subject subject, AttributeNetwork network) 
		{
			dataset.setNetwork(network);
			
			// Update UI control
			
			control.setModel(dataset.getModel());

			control.clearHeaders();
			
			for (int i=0; i<dataset.getColumnCount(); i++)
				control.addHeader( dataset.getName(i));
			
			control.setData(dataset);
			
			control.notifyObservers(dataset);
		}
	}
	
	
	public class NodeDataset extends Dataset	
	{
		AttributeNetwork network;
		
		public NodeDataset ()
		{
			this.network = null;
		}
		
		public AttributeNetwork getNetwork()
		{
			return network;
		}
		
		public void setNetwork (AttributeNetwork network)
		{
			this.network = network;
		}

		public String getName(int column) 
		{
			AttributeNetwork network = getNetwork();
			
			if (network!=null)
				return network.getNodeAttribute(column).getID();
			else
				return null;
		}
		
		@Override
		public Object get(int row, int column) 
		{
			AttributeNetwork network = getNetwork();
			Object data = null;
		
			if ((network!=null) && (column<network.getNodeAttributeCount()) && (row<network.size()))
				data = network.getNodeAttribute(column).get(row);
			
			return data;
		}

		@Override
		public void set(int row, int column, Object object) 
		{
			AttributeNetwork network = getNetwork();

			if (network!=null)
				network.getNodeAttribute(column).set(row, object);
		}

		@Override
		public int getColumnCount() 
		{
			AttributeNetwork network = getNetwork();

			if (network!=null)
				return network.getNodeAttributeCount();
			else
				return 0;
		}

		@Override
		public int getRowCount() 
		{
			AttributeNetwork network = getNetwork();

			if (network!=null)
				return network.size();
			else
				return 0;
		}

		@Override
		public DataModel getModel(int column) 
		{
			AttributeNetwork network = getNetwork();

			if (network!=null)
				return network.getNodeAttribute(column).getModel();
			else
				return null;
		}
		
		
	}

}
