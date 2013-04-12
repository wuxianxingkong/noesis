package noesis.ui.model;

import ikor.model.Subject;
import ikor.util.log.Log;

import noesis.Attribute;
import noesis.AttributeNetwork;

// Network model in MVC

class NetworkModel extends Subject<AttributeNetwork>
{
	AttributeNetwork network;
	
	
	public NetworkModel (int K)
	{
		network = new AttributeNetwork();
		
		Attribute<Double> x = new Attribute<Double>("x");
		Attribute<Double> y = new Attribute<Double>("y");
		
		network.addNodeAttribute( x );
		network.addNodeAttribute( y );
		
		for (int i=0; i<K; i++) {
			network.add(i);
			x.set(i, 0.5 + 0.4*Math.cos(i*2*Math.PI/K));
			y.set(i, 0.5 + 0.4*Math.sin(i*2*Math.PI/K));
		}
		
		for (int i=0; i<K; i++) {
			for (int j=i+1; j<K; j++) {
				network.add(i,j);
			}
		}
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
		Log.info("MVC network - "+object+" @ "+subject);
		
		if (object!=null)
			setNetwork( object );
		else
			notifyObservers();
	}
}