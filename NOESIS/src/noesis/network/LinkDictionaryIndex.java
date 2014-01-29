package noesis.network;

// Title:       Dictionary-based link index for networks
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.Dictionary;
import ikor.collection.util.Pair;

import noesis.CollectionFactory;
import noesis.Network;

/**
 * Link index. Given a network, map its links to integers.
 * 
 * Implementation using dictionaries:
 * - Access to link index: O(1).
 * - Access to link information given its index: O(1).
 * 
 * Allows link and node addition, but not their removal.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class LinkDictionaryIndex implements LinkIndexer 
{
	private Network net;	
	
	private Dictionary<Pair<Integer,Integer>, Integer> index;
	private Dictionary<Integer, Pair<Integer,Integer>> reverse;
	

	/**
	 * Constructor, O(n).
	 * @param net Underlying network.
	 */
	public LinkDictionaryIndex (Network net)
	{
		this.net = net;
		this.index = CollectionFactory.createDictionary();
		this.reverse = CollectionFactory.createDictionary();
	}

	/**
	 * Underlying network, O(1).
	 * @return The network the index is based on.
	 */
	public Network network ()
	{
		return net;
	}
	
	/**
	 * Nodes in the underlying network, O(1).
	 * @return Number of nodes in the network when the index was built. 
	 * @see noesis.network.LinkIndexer#nodes()
	 */
	@Override
	public int nodes ()
	{
		return net.nodes();
	}
	
	/**
	 * Links in the underlying network, O(1).
	 * @return Number of links in the network when the index was built.
	 * @see noesis.network.LinkIndexer#links()
	 */
	@Override
	public int links ()
	{
		return net.links();
	}
	
	
	/**
	 * Add a new entry to the index, O(1).
	 */
	
	public void add (int source, int destination)
	{
		int position = links();
		Pair<Integer,Integer> edge = new Pair<Integer,Integer>(source,destination);
		
		index.set( edge, position);
		reverse.set (position, edge);
	}
	
	/**
	 * Index of a given link, O(1).
	 * @param source Source node index.
	 * @param destination Destination node index.
	 * @return Link index (0..m-1), -1 if link does not exist.
	 * @see noesis.network.LinkIndexer#index(int, int)
	 */
	@Override
	public int index (int source, int destination)
	{
		Pair<Integer,Integer> key = new Pair<Integer,Integer>(source,destination);
		Integer pos = index.get(key);
		
		if (pos!=null)
			return pos;
		else
			return -1;
	}
	
	
	/**
	 * Source node of a given link, O(1).
	 * @param link Link index
	 * @return Source node index of the corresponding link
	 * @see noesis.network.LinkIndexer#source(int)
	 */
	@Override
	public int source (int link)
	{
		Pair<Integer,Integer> value = reverse.get(link);

		if (value!=null)
			return value.first();
		else 
			return -1;
	}
	
	
	/**
	 * Destination node of a given link, O(1).
	 * @param link Link index
	 * @return Destination node index
	 * @see noesis.network.LinkIndexer#destination(int)
	 */
	@Override
	public int destination (int link)
	{
		Pair<Integer,Integer> value = reverse.get(link);

		if (value!=null)
			return value.second();
		else 
			return -1;
	}
	
	
}
