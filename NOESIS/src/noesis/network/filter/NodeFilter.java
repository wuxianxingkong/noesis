package noesis.network.filter;

public class NodeFilter implements NetworkFilter 
{
	private int index;
	
	public NodeFilter (int index)
	{
		this.index = index;
	}

	@Override
	public boolean node(int node) 
	{
		return (node!=index);
	}

	@Override
	public boolean link(int source, int destination) 
	{
		return (source!=index) && (destination!=index);
	}

}
