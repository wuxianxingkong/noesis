package noesis;

//Title:       Network base class
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

public abstract class Network<V, E> 
{
	private String id;
	
	public String getID()
	{
		return id;
	}

	public void setID(String id)
	{
		this.id = id;
	}
	
	
	public abstract int size();

	public abstract void setSize(int nodes);

	public abstract int links();
	

	
	public abstract int add(V node);

	public abstract boolean add(int sourceIndex, int destinationIndex);

	public abstract boolean add(int sourceIndex, int destinationIndex, E content);

	
	public abstract V get(int index);

	public abstract E get(int source, int destination);

	public abstract E get(V source, V destination);


	public abstract boolean contains(V object);
	
	public abstract int index(V node);

	
	
	public abstract int inDegree(int node);

	public final int inDegree(V node) 
	{
		return inDegree ( index(node) );
	}
	
	public abstract int outDegree(int node);

	public final int outDegree(V node) 
	{
		return outDegree ( index(node) );
	}	
	

}