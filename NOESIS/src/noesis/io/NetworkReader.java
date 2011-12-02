package noesis.io;

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
	
	
	public final void setType (Class type) //  ? extends Network<V,E>
	{
		if (Network.class.isAssignableFrom(type))
			this.type = type;
	}
	
	public final Network<V,E> createNetwork ()
		throws IOException
	{
		Network<V,E> net = null;
		
		try {
			net = (Network<V,E>) type.newInstance();
		} catch (Exception error) {
			throw new IOException("Unable to instantiate "+type, error);
		}
		
		return net;
	}
	
	// Abstract method
	
	public abstract Network<V, E> read () throws IOException;
}
