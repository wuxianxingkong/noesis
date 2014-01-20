package noesis.network.filter;

public interface NetworkFilter 
{
	public boolean node (int node);
	
	public boolean link (int source, int destination);
}
