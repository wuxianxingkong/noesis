package noesis.network.filter;

public class DefaultFilter implements NetworkFilter {

	@Override
	public boolean node(int node) 
	{
		return true;
	}

	@Override
	public boolean link(int source, int destination) 
	{
		return true;
	}

}
