package noesis.ui.model;

import ikor.model.Subject;

import noesis.AttributeNetwork;

// Network model in MVC

class NetworkModel extends Subject<AttributeNetwork>
{
	AttributeNetwork network;
	
	
	public NetworkModel (AttributeNetwork network)
	{
		this.network = network;
	}
	
	public AttributeNetwork getNetwork() 
	{
		return network;
	}

	public void setNetwork (AttributeNetwork network) 
	{
		this.network = network;
		notifyObservers(network);
	}
	
	@Override
	public void update (Subject subject, AttributeNetwork object) 
	{
		if (object!=null)
			setNetwork( object );
		else
			notifyObservers(network);
	}
}