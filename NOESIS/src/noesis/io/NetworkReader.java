package noesis.io;

import ikor.math.Decimal;

import java.io.IOException;

import noesis.Network;

public abstract class NetworkReader<V,E> 
{

	// Network type

	private Class type = noesis.GraphNetwork.class; // noesis.ArrayNetwork.class;
	
	public final Class getType ()
	{
		return type;
	}
	
	public final void setType (Class type)
	{
		if (Network.class.isAssignableFrom(type))
			this.type = type;
	}
	
	public final Network<String,Decimal> createNetwork ()
		throws IOException
	{
		Network net = null;
		
		try {
			net = (Network) type.newInstance();
		} catch (Exception error) {
			throw new IOException("Unable to instantiate "+type, error);
		}
		
		return net;
	}
	
	// Abstract method
	
	public abstract Network<V, E> read () throws IOException;
}
