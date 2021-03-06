package sandbox.graphs;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;
import ikor.collection.DynamicList;
import ikor.collection.List;
import noesis.Network;

public class CompleteNetwork<V> extends Network<V, Object> 
{
	private List<V>   nodes;
	private Dictionary<V,Integer> hash;

	public CompleteNetwork ()
	{
		this.nodes = new DynamicList();
		this.hash = new DynamicDictionary();
	}

	@Override
	public int nodes() 
	{
		return nodes.size();
	}

	@Override
	public void setSize(int nodes) 
	{
		// nodes.setSize(nodes);
	}

	@Override
	public int links() 
	{
		int n = size();

		return n*(n-1);
	}

	@Override
	public int add(V node) 
	{
		int pos = nodes.size();
		
		nodes.add(node);
		hash.set(node, pos);
			
		if (nodes.size()>=size())
			setSize(nodes.size());

		return pos;
	}

	@Override
	public boolean add(int sourceIndex, int destinationIndex) 
	{
		// Nothing to do in a complete network
		throw new UnsupportedOperationException("New links cannot be added to a complete network.");
	}

	@Override
	public boolean add(int sourceIndex, int destinationIndex, Object content) 
	{
		// Nothing to do in a complete network
		throw new UnsupportedOperationException("New links cannot be added to a complete network.");
	}

	@Override
	public V get(int index) 
	{
		return nodes.get(index);
	}
	
	public void set (int index, V value)
	{
		throw new UnsupportedOperationException("Unsupported operation.");		
	}

	@Override
	public Object get (int source, int destination) 
	{
		// Nothing to return...
		return null;
	}



	@Override
	public boolean contains(V object) 
	{
        return hash.contains(object);
	}

	@Override
	public int index(V node) 
	{
		Integer entry = hash.get(node);
		
		if (entry!=null)
			return entry;
		else
			return -1;
	}

	@Override
	public int inDegree(int node) 
	{
		return size()-1;
	}

	@Override
	public int outDegree(int node) 
	{
		return size()-1;
	}


	public int link (int node, int index) 
	{
		if (index<node)
			return index;
		else
			return index+1;
	}

	@Override
	public int outLink (int node, int index) 
	{
		return link(node,index);
	}


	@Override
	public int inLink (int node, int index) 
	{
		return link(node,index);
	}



	@Override
	public String toString ()
	{
		return "K"+size()+" complete network ("+links()+" links)";
	}
}
