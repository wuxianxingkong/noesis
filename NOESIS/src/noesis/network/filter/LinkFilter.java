package noesis.network.filter;

import ikor.collection.Set;
import noesis.CollectionFactory;

public class LinkFilter implements NetworkFilter 
{
	private Set<Long> linkset;
	
	public LinkFilter ()
	{
		linkset = CollectionFactory.createSet();
	}
	
	public LinkFilter (int sourceIndex, int destinationIndex)
	{
		this();
		deleteLink (sourceIndex, destinationIndex);
	}
	
	public void deleteLink (int sourceIndex, int destinationIndex)
	{
		linkset.add ( key(sourceIndex,destinationIndex));
	}
	
	private long key (int source, int destination)
	{
		return ((long)source<<32) | destination;
	}
	
	@Override
	public boolean node(int node) 
	{
		return true;
	}

	@Override
	public boolean link(int source, int destination) 
	{
		return !linkset.contains( key(source,destination) );
	}

}
