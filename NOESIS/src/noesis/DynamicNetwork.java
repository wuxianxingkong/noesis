package noesis;

import noesis.Network;
import ikor.collection.Dictionary;
import ikor.collection.List;
import ikor.collection.index.Index;

public class DynamicNetwork<V,E> extends Network<V,E> 
{
	private List<V>               nodes;	
	private Dictionary<V,Integer> index;
	private int                   currentNode;
	
	private List<List<E>> 		  content;
	
	private Index         skip;
	private List<Index>   in;
	private List<Index>   out;
	
	private int                   links;
	
	// Constructor

	public DynamicNetwork ()
	{
		nodes = CollectionFactory.createList();
		index = CollectionFactory.createDictionary();;
		currentNode = 0;
		
		skip = CollectionFactory.createIndex();
		in = CollectionFactory.createList();
		out = CollectionFactory.createList();

		links = 0;
	}
	
	public DynamicNetwork(Network<V,E> net)
	{
		this();
		
		for (int i=0; i<net.size(); i++) {
			add(net.get(i));
		}

		for (int i=0; i<net.size(); i++) {
			for (int j=0; j<net.outDegree(i); j++) {
				int target = net.outLink(i,j);
			    add(i, target, net.get(i,target));
			}
		}		
	}

	// Network size
	
	@Override
	public int nodes() 
	{
		return skip.size();
	}

	@Override
	public void setSize (int nodes) 
	{
		while (size()<nodes) {
			add(null);
		}
	}

	@Override
	public int links() 
	{
		return links;
	}

	// Nodes
	
	@Override
	public V get(int index) 
	{
		return nodes.get( skip.get(index) );
	}
	
	@Override
	public void set(int index, V value)
	{
		nodes.set( skip.get(index), value);
	}

	@Override
	public int index (V node) 
	{
		Integer entry = index.get(node);
		
		if (entry!=null)
			return entry;
		else
			return -1;
	}


	// Links
	
	@Override
	public E get(int source, int destination) 
	{
		int index;
		int sourceIndex = skip.get(source);
		int destinationIndex = skip.get(destination);
		
		if ((content!=null) && (sourceIndex>=0) && (sourceIndex<content.size())) {
			
			index = out.get(sourceIndex).index(destinationIndex);
			
			if (index!=-1)
				return content.get(source).get(index);
			else			
				return null;
			
		} else {
			
			return null;
		}
	}

	@Override
	public boolean contains (int source, int destination) 
	{
		int index;
		int sourceIndex = skip.get(source);
		int destinationIndex = skip.get(destination);
		
		if ((sourceIndex>=0) && (sourceIndex<size())) {
			
			index = out.get(sourceIndex).index(destinationIndex);
			
			return (index!=-1);
			
		} else {
			
			return false;
		}
	}


	// Node degrees
	
	@Override
	public int inDegree(int node) 
	{
		int   index = skip.get(node);
		Index inlinks = in.get(index);
		
		if (inlinks!=null)
			return inlinks.size();
		else
			return 0;
	}

	@Override
	public int outDegree(int node) 
	{
		int   index = skip.get(node);
		Index outlinks = out.get(index);
		
		if (outlinks!=null)
			return outlinks.size();
		else
			return 0;
	}

	// Node links

	@Override
	public int outLink(int node, int link) 
	{
		int index = skip.get(node);
		
		return out.get(index).get(link);
	}

	@Override
	public int inLink(int node, int link) 
	{
		int index = skip.get(node);
		
		return in.get(index).get(link);
	}
	

	// Network dynamics
	// ----------------
	
	// Nodes
	
	@Override
	public int add(V node) 
	{
		nodes.add(node);
		index.set(node, currentNode);
		skip.add(currentNode);
		in.add(CollectionFactory.createIndex());
		out.add(CollectionFactory.createIndex());
		currentNode++;
		
		return size()-1;
	}
	
	@Override
	public boolean remove(int nodeIndex) 
	{		
		if ( (nodeIndex>=0) && (nodeIndex<size()) ) {
		
			// Remove links

			int inlink[] = inLinks(nodeIndex);
			
			if (inlink!=null)
			   for (int i=0; i<inlink.length; i++)
				   remove(inlink[i], nodeIndex);

			int outlink[] = outLinks(nodeIndex);
			
			if (outlink!=null)
			   for (int j=0; j<outlink.length; j++)
				   remove(nodeIndex, outlink[j]);
		
			// Remove skip list entries
			
			int pos = skip.get(nodeIndex);

			index.remove(nodes.get(pos));
			nodes.set(pos, null);
			skip.remove(pos);
			in.set(pos,null);
			out.set(pos,null);
						
			return true;
			
		} else {
			return false;
		}
	}

	// Links
	
	@Override
	public boolean add(int sourceIndex, int destinationIndex) 
	{
		int sourceNode = skip.get(sourceIndex);
		int destinationNode = skip.get(destinationIndex);
		
		out.get(sourceNode).add(destinationNode);
		in.get(destinationNode).add(sourceNode);
		
		links++;
		
		return true;
	}

	public boolean remove(int sourceIndex, int destinationIndex)
	{
		int sourceNode = skip.get(sourceIndex);
		int destinationNode = skip.get(destinationIndex);
		
		out.get(sourceNode).removeValue(destinationNode);
		in.get(destinationNode).removeValue(sourceNode);
		
		links--;
		
		return true;
	}

	@Override
	public boolean add(int sourceIndex, int destinationIndex, E value) 
	{
		boolean ok = add(sourceIndex,destinationIndex);

		int sourceNode = skip.get(sourceIndex);
		
		if (ok) {
			
			if (content==null)
				content = CollectionFactory.createList();
			
			if (content.get(sourceNode)==null)
				content.set(sourceNode, CollectionFactory.createList() );
			
			content.get(sourceNode).add(value);			
		}

		return ok;		
	}
	
	@Override
	public boolean remove(int source, int destination, E value)
	{
		int index;
		int sourceIndex = skip.get(source);
		int destinationIndex = skip.get(destination);

		remove(source,destination);
		
		if ((content!=null) && (content.get(sourceIndex)!=null)) {
			
			index = out.get(sourceIndex).index(destinationIndex);
			
			content.get(sourceIndex).set(index,null);
		}
		
		return true;
	}	

}
