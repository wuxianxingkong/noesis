package noesis.io;

import java.io.IOException;

import noesis.Network;

public interface NetworkWriter<V,E>
{
	public void write (Network<V,E> net) throws IOException;
	
	public void close () throws IOException;
}