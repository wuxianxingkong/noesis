package noesis.network.filter;

public class LinkFilter implements NetworkFilter 
{
	private int sourceIndex;
	private int destinationIndex;
	
	public LinkFilter (int sourceIndex, int destinationIndex)
	{
		this.sourceIndex = sourceIndex;
		this.destinationIndex = destinationIndex;
	}
	
	@Override
	public boolean node(int node) 
	{
		return true;
	}

	@Override
	public boolean link(int source, int destination) 
	{
		return (source!=sourceIndex) || (destination!=destinationIndex);
	}

}
