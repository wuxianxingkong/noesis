package noesis.network.filter;

public class MaskFilter implements NetworkFilter 
{
	private boolean mask[];

	public MaskFilter (boolean mask[])
	{
		this.mask = mask;
	}
	
	@Override
	public boolean node(int node) 
	{
		return mask[node];
	}

	@Override
	public boolean link(int source, int destination) 
	{
		return mask[source] && mask[destination];
	}

}
