package noesis.io;

import java.io.IOException;

import noesis.Network;

public interface NetworkReader<V,E> 
{
  public Network<V,E> read () throws IOException;
}
