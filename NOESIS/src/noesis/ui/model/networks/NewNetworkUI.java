package noesis.ui.model.networks;

import noesis.Network;
import noesis.AttributeNetwork;
import noesis.Attribute;
import noesis.algorithms.visualization.NetworkLayout;

import ikor.model.data.RealModel;
import ikor.model.ui.Application;
import ikor.model.ui.UIModel;


public abstract class NewNetworkUI extends UIModel
{
	public NewNetworkUI(Application application, String title) 
	{
		super(application, title);
	}
	
	/**
	 * Create an attribute network from a given network
	 * @param net Given network
	 * @param id Attribute network ID
	 * @param display Initial network layout
	 * @return The (decorated) attribute network
	 */

	protected AttributeNetwork createAttributeNetwork (Network net, String id, NetworkLayout display)
	{
		AttributeNetwork network = new AttributeNetwork(net);
		Attribute idAttribute = new Attribute<String>("id");

		// Network ID
		
		network.setID(id);
		
		// Node IDs
		
		network.addNodeAttribute( idAttribute );
		
		for (int i=0; i<net.size(); i++)
			idAttribute.set(i, "Node "+(i+1));
		
		// Initial network layout
		
		RealModel coordinateModel = new RealModel();
		coordinateModel.setMinimumValue(0.0);
		coordinateModel.setMaximumValue(1.0);
		
		network.addNodeAttribute( new Attribute<Double>("x", coordinateModel) );
		network.addNodeAttribute( new Attribute<Double>("y", coordinateModel) );
		
		display.layout(network);
		
		return network;
	}
}
