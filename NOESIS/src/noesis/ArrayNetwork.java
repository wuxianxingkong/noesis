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
	public int size() 
	{
		return size;
	}

	@Override
	public void setSize(int size) 
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
	public int links() 
	{
		return nlinks;
	}



	@Override
	public int add(V node) 
	{
		boolean ok;
				
		ok = nodes.add(node);
		
		if (ok) {
			hash.set(node, nodes.size()-1);
			
			if (nodes.size()>size)
				setSize(nodes.size());
		}
		
		if (ok)
			return nodes.size()-1;
		else
			return -1;
	}
	
	
	private int[] extend (int[] array, int value)
	{
		int   dim;
		int[] newArray;
		
		if (array!=null)
			dim = array.length;
		else
			dim = 0;
		
		newArray = new int[dim+1];
		
		if (array!=null) {
			System.arraycopy(array,0,newArray,0,array.length);
		}
		
		newArray[dim] = value;
		
		return newArray;
	}

	@Override
	public boolean add(int source, int destination) 
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
	public boolean add(int sourceIndex, int destinationIndex, E value) 
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
	public V get(int index) 
	{
		if (nodes!=null)
			return nodes.get(index);
		else
			return null;
	}

	@Override
	public E get(int source, int destination) 
	{
		if ((content!=null) && (content[source]!=null)) {
			
			for (int i=0; i<outLinks[source].length; i++)
				if (outLinks[source][i] == destination)
					return content[source].get(i);
			
			return null;
			
		} else {
			
			return null;
		}
	}

	@Override
	public E get(V source, V destination) 
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		return get(sourceIndex,destinationIndex);
	}

	@Override
	public boolean contains(V node) 
	{
		return hash.contains(node);
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
		if ((inLinks!=null) && (inLinks[node]!=null))
			return inLinks[node].length;
		else
			return 0;
	}

	@Override
	public int outDegree(int node) 
	{
		if ((outLinks!=null) && (outLinks[node]!=null))
			return outLinks[node].length;
		else
			return 0;
	}


}
