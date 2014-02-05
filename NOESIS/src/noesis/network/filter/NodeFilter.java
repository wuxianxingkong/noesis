package noesis.network.filter;

import noesis.CollectionFactory;
import ikor.collection.Set;

public class NodeFilter implements NetworkFilter 
{
	private Set<Integer> nodeset;

	public NodeFilter ()
	{
		nodeset = CollectionFactory.createSet();
	}
	
	public NodeFilter (int index)
	{
		this();
		deleteNode(index);
	}

	public void deleteNode (int index)
	{
		nodeset.add(index);
	}
	
	@Override
	public boolean node(int node) 
	{
		return !nodeset.contains(node);
	}

	@Override
	public boolean link(int source, int destination) 
	{
		return !nodeset.contains(source) && !nodeset.contains(destination);
	}

}
