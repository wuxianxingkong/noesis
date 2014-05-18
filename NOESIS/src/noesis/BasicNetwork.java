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
	public int nodes() 
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
	public int links() 
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
		   && (destination<size())
		   && !contains(source,destination) ) {
			
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
		throw new UnsupportedOperationException("Unsupported operation on basic networks");
	}

	@Override
	public Integer get(int index) 
	{
		return index;
	}
	
	@Override
	public void set (int index, Integer value)
	{
		throw new UnsupportedOperationException("Unsupported operation on basic networks");		
	}
	
	
	public int getLinkIndex(int source, int destination)
	{
		if (outLinks[source]!=null)
			for (int i=0; i<outLinks[source][0]; i++)
				if (outLinks[source][START_INDEX+i] == destination)
					return i;
		
		return -1;
	}
	

	@Override
	public Integer get (int source, int destination) 
	{
		if (outLinks[source]!=null)
			for (int i=0; i<outLinks[source][0]; i++)
				if (outLinks[source][START_INDEX+i] == destination)
					return destination;
		
		return null;
	}

	@Override
	public boolean contains (int source, int destination) 
	{
		if (outLinks[source]!=null)
			for (int i=0; i<outLinks[source][0]; i++)
				if (outLinks[source][START_INDEX+i] == destination)
					return true;
		
		return false;
	}
	

	@Override
	public boolean contains(Integer node) 
	{
		return (node>=0) && (node<size);
	}

	@Override
	public int index(Integer node) 
	{	
		return node;
	}


	@Override
	public int inDegree(int node) 
	{
		if ((inLinks!=null) && (inLinks[node]!=null))
			return inLinks[node][0];
		else
			return 0;
	}

	@Override
	public int outDegree(int node) 
	{
		if ((outLinks!=null) && (outLinks[node]!=null))
			return outLinks[node][0];
		else
			return 0;
	}

	
	@Override
	public int outLink (int node, int index)
	{
		return outLinks[node][START_INDEX+index];
	}
	


	@Override
	public int inLink (int node, int index)
	{
		return inLinks[node][START_INDEX+index];
	}


	
	@Override
	public String toString ()
	{
		return "["+super.toString()+"] "+size()+" nodes, "+links()+" links.";
	}

}
