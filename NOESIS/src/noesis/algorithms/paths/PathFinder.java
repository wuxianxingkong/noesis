package noesis.algorithms.paths;

import noesis.Network;

public interface PathFinder<V, E> 
{
	public abstract Network<V, E> network();

	public abstract void run();

}