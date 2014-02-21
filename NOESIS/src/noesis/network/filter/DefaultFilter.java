package noesis.network.filter;

public class DefaultFilter implements NetworkFilter {

	@Override
	public boolean node(int node) 
	{
		return true;
	}

	@Override
	public boolean link(int node, int link) 
	{
		return true;
	}

}
