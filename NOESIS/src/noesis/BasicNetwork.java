package noesis;

// Title:       Network ADT
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Basic network ADT implementation using arrays
 * 
 * @author Fernando Berzal
 */
public class BasicNetwork extends Network<Integer,Integer> 
{
	private int size;
	private int nlinks;
	
	private int[][] inLinks;
	private int[][] outLinks;

	
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
	public final int add(Integer node) 
	{
		int pos = node;
					
		if (pos>=size)
			setSize(pos+1);

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
	public boolean add(int source, int destination) 
	{
		if (  (source>=0) 
		   && (source<size())
		   && (destination>=0)
		   && (destination<size()) ) {
			
			nlinks++;
			outLinks[source] = extend(outLinks[source], destination);
			inLinks[destination] = extend(inLinks[destination], source);
			return true;
			
		} else {
			
			return false;
		}
	}

	@Override
	public final boolean add(int sourceIndex, int destinationIndex, Integer value) 
	{
		throw new UnsupportedOperationException("Unsupported operation on simple networks");
	}

	@Override
	public final Integer get(int index) 
	{
		return index;
	}
	
	
	public final int getLinkIndex(int source, int destination)
	{
		if (outLinks[source]!=null)
			for (int i=0; i<outLinks[source][0]; i++)
				if (outLinks[source][START_INDEX+i] == destination)
					return i;
		
		return -1;
	}

	@Override
	public final Integer get(int source, int destination) 
	{
		if (outLinks[source]!=null)
			for (int i=0; i<outLinks[source][0]; i++)
				if (outLinks[source][START_INDEX+i] == destination)
					return destination;
		
		return null;
	}

	@Override
	public final Integer get(Integer source, Integer destination) 
	{
		return get((int)source, (int)destination);
	}

	@Override
	public final boolean contains(Integer node) 
	{
		return (node<size);
	}

	@Override
	public final int index(Integer node) 
	{	
		return node;
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
		int   outDegree = outDegree(node);
		int[] links     = null;
		
		if (outDegree>0) {
			
			links = new int[outDegree];
		
			for (int i=0; i<outDegree; i++)
				links[i] = outLinks[node][START_INDEX+i];
		}

		return links;
	}

	
	@Override
	public int[] outLinks(Integer node) 
	{
		return outLinks((int)node);
	}

	@Override
	public int[] inLinks(int node) 
	{
		int   inDegree = inDegree(node);
		int[] links    = null;
		
		if (inDegree>0) {
			
			links = new int[inDegree];
		
			for (int i=0; i<inDegree; i++)
				links[i] = inLinks[node][START_INDEX+i];
		}

		return links;
	}

	@Override
	public int[] inLinks(Integer node) 
	{
		return inLinks((int)node);
	}

	
	@Override
	public String toString ()
	{
		return "["+super.toString()+"] "+size()+" nodes, "+links()+" links.";
	}

}
