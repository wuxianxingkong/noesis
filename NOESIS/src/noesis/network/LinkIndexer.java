package noesis.network;

public interface LinkIndexer 
{
	/**
	 * Nodes in the underlying network.
	 * @return Number of nodes in the network. 
	 */
	public abstract int nodes();

	/**
	 * Links in the underlying network.
	 * @return Number of links in the network.
	 */
	public abstract int links();

	/**
	 * Index of a given link.
	 * @param source Source node index.
	 * @param destination Destination node index.
	 * @return Link index (0..m-1), -1 if link does not exist.
	 */
	public abstract int index(int source, int destination);

	/**
	 * Source node of a given link.
	 * @param link Link index
	 * @return Source node of the corresponding link
	 */
	public abstract int source(int link);

	/**
	 * Destination node of a given link, O(log n)
	 * @param link Link index
	 * @return Destination node
	 */
	public abstract int destination(int link);

}