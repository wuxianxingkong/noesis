package noesis;

//Title:       Network ADT
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.DynamicList;
import ikor.collection.DynamicDictionary;

/**
 * Network ADT implementation using arrays.
 * 
 * @author Fernando Berzal
 */
public class ArrayNetwork<V,E> extends Network<V, E> 
{
	private int size;
	private int nlinks;
	
	private int[][] inLinks;
	private int[][] outLinks;

	private DynamicList<V>   nodes;
	private DynamicList<E>[] content;
	
	private DynamicDictionary<V,Integer> hash;
	
	
	public ArrayNetwork ()
	{
		this.nodes = new DynamicList();
		this.hash = new DynamicDictionary();
	}
	
	@Override
	public final int size() 
	{
		return size;
	}

	@Override
	public final void setSize(int size) 
	{
		int[][] oldInLinks;
		int[][] oldOutLinks;
		
		oldInLinks  = this.inLinks;
		oldOutLinks = this.outLinks;

		this.size  = size;
		this.inLinks  = new int[size][]; 
		this.outLinks = new int[size][]; 
		
		if (oldInLinks!=null)
			System.arraycopy(oldInLinks,0,inLinks,0,oldInLinks.length);
		
		if (oldOutLinks!=null)
			System.arraycopy(oldOutLinks,0,outLinks,0,oldOutLinks.length);
	}

	@Override
	public final int links() 
	{
		return nlinks;
	}



	@Override
	public final int add(V node) 
	{
		int pos = nodes.size();
		
		nodes.add(node);
		hash.set(node, pos);
			
		if (nodes.size()>=size)
			setSize(nodes.size());

		return pos;
	}
	
	
	// e.g. Google web (875k nodes, 5.1M links, 21MB GZIP, 75MB TXT )
	// - Incremental extension (+1):                            
	//     55s = 21s (I/O) + 13s (node creation) + 21s (link creation)
	//     (without additional memory requirements)
	// - Multiplicative extension with Pascal-like arrays (*2):
	//     41s = 21s (I/O) + 13s (node creation) + 7s (link creation)
	//     (2*size() additional integer values, i.e. inLinks[i][0] & outLinks[i][0])
	
	private final static int INITIAL_ARRAY_SIZE = 4;
	private final static int START_INDEX = 1;
	
	private final int[] extend (int[] array, int value)
	{
		int   dim;
		int[] newArray;
		
		if (array==null) {
			
			newArray = new int[INITIAL_ARRAY_SIZE];
			newArray[0] = 1;
			newArray[1] = value;
		
		} else {
			
			dim = array[0]+1;	
			
			if (dim<array.length) {
				newArray = array;
			} else {
				newArray = new int[2*array.length];
				System.arraycopy(array,0,newArray,0,array.length);
			}

			newArray[0] = dim; 
			newArray[dim] = value;	
		}		
		
		return newArray;
	}

	@Override
	public final boolean add(int source, int destination) 
	{
		if (  (source>=0) 
		   && (source<size())
		   && (destination>=0)
		   && (destination<size()) ) {
			
			nlinks++;
			outLinks[source] = extend(outLinks[source], destination);
			inLinks[destination] = extend(inLinks[destination], destination);
			return true;
			
		} else {
			
			return false;
		}
	}

	@Override
	public final boolean add(int sourceIndex, int destinationIndex, E value) 
	{
		boolean ok = add(sourceIndex,destinationIndex);
		
		if (ok) {
			
			if (content==null)
				content = new DynamicList[size()];
			
			if (content[sourceIndex]==null)
				content[sourceIndex] = new DynamicList<E>();
			
			content[sourceIndex].add(value);			
		}

		return ok;
	}

	@Override
	public final V get(int index) 
	{
		if (nodes!=null)
			return nodes.get(index);
		else
			return null;
	}

	@Override
	public final E get(int source, int destination) 
	{
		if ((content!=null) && (content[source]!=null)) {
			
			for (int i=0; i<outLinks[source][0]; i++)
				if (outLinks[source][START_INDEX+i] == destination)
					return content[source].get(i);
			
			return null;
			
		} else {
			
			return null;
		}
	}

	@Override
	public final E get(V source, V destination) 
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		return get(sourceIndex,destinationIndex);
	}

	@Override
	public final boolean contains(V node) 
	{
		return hash.contains(node);
	}

	@Override
	public final int index(V node) 
	{	
		Integer entry = hash.get(node);
		
		if (entry!=null)
			return entry;
		else
			return -1;
	}


	@Override
	public final int inDegree(int node) 
	{
		if ((inLinks!=null) && (inLinks[node]!=null))
			return inLinks[node][0];
		else
			return 0;
	}

	@Override
	public final int outDegree(int node) 
	{
		if ((outLinks!=null) && (outLinks[node]!=null))
			return outLinks[node][0];
		else
			return 0;
	}

	@Override
	public int[] outLinks(int node) 
	{
		int outDegree = outLinks[node][0];
		int[] links = new int[outDegree];
		
		for (int i=1; i<=outDegree; i++)
			links[i] = outLinks[node][i];

		return links;
	}

	
	@Override
	public int[] outLinks(V node) 
	{
		return outLinks(index(node));
	}

	@Override
	public int[] inLinks(int node) 
	{
		int inDegree = inLinks[node][0];
		int[] links = new int[inDegree];
		
		for (int i=1; i<=inDegree; i++)
			links[i] = inLinks[node][i];

		return links;
	}

	@Override
	public int[] inLinks(V node) 
	{
		return inLinks(index(node));
	}


}
