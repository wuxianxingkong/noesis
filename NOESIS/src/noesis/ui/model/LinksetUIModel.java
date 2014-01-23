package noesis.ui.model;

import noesis.AttributeNetwork;

import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.data.DataModel;
import ikor.model.data.Dataset;
import ikor.model.ui.Application;
import ikor.model.ui.DatasetViewer;
import ikor.model.ui.UIModel;

public class LinksetUIModel extends UIModel 
{

	public LinksetUIModel (Application app, NetworkModel data) 
	{
		super(app, "Network links");
		setIcon( app.url("icons/chart.png") );
		
		LinkDataset dataset = new LinkDataset();
		
		DatasetViewer control = new DatasetViewer("Link data viewer", dataset.getModel());
		add(control);
		
		data.addObserver( new LinksetObserver(dataset,control) );		
	}
	
	
	public class LinksetObserver implements Observer<AttributeNetwork>
	{
		private LinkDataset dataset;
		private DatasetViewer control;
		
		public LinksetObserver (LinkDataset dataset, DatasetViewer control)
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
	
	
	public class LinkDataset extends Dataset	
	{
		AttributeNetwork network;
		
		public LinkDataset ()
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
			
			if (column==0)
				return "source";
			else if (column==1)
				return "destination";
			else if ((network!=null) && (column-2<network.getLinkAttributeCount())) 
				return network.getLinkAttribute(column-2).getID();
			else
				return null;
		}
		
		@Override
		public Object get(int row, int column) 
		{
			AttributeNetwork network = getNetwork();
			Object data = null;
		
			if (network!=null)  {
				
				if (column==0) {
					// Source
					data = network.getNodeAttribute("id").get( network.source(row) );
				} else if (column==1) {
					// Destination
					data = network.getNodeAttribute("id").get( network.destination(row) );
				} else if (column-2<network.getLinkAttributeCount()) {
					// Link attribute
					data = network.getLinkAttribute(column-2).get(row);
				}
			}
			
			return data;
		}

		@Override
		public void set(int row, int column, Object object) 
		{
			AttributeNetwork network = getNetwork();

			if ((network!=null) && (column>1))
				network.getLinkAttribute(column-2).set(row, object);
		}

		@Override
		public int getColumnCount() 
		{
			AttributeNetwork network = getNetwork();

			if (network!=null)
				return 2 + network.getLinkAttributeCount();
			else
				return 0;
		}

		@Override
		public int getRowCount() 
		{
			AttributeNetwork network = getNetwork();

			if (network!=null)
				return network.links();
			else
				return 0;
		}
		
		private DataModel ID_MODEL = new ikor.model.data.TextModel();

		@Override
		public DataModel getModel(int column) 
		{
			AttributeNetwork network = getNetwork();

			if ((column==0) || (column==1))
				return ID_MODEL;
			else if (network!=null) 
				return network.getLinkAttribute(column-2).getModel();
			else
				return null;
		}
		
		
	}

}
