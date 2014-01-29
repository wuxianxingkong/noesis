package noesis.network;

import noesis.LinkAttribute;

public class FilteredLinkAttribute<T> extends LinkAttribute<T> 
{
	private LinkAttribute<T> original;
	private FilteredNetwork  net;

	public FilteredLinkAttribute (FilteredNetwork net, LinkAttribute<T> original) 
	{
		super (net,original.getID());
		
		this.original = original;
		this.net = net;
	}
	
	
	/* (non-Javadoc)
	 * @see noesis.LinkAttribute#index(int, int)
	 */
	@Override
	protected int index(int source, int target) 
	{
		return net.index(source, target);
	}

	/* (non-Javadoc)
	 * @see ikor.collection.DynamicList#get(int)
	 */
	@Override
	public T get(int source, int target) 
	{
		return original.get( net.get(source), net.get(target) );
	}

	/* (non-Javadoc)
	 * @see ikor.collection.DynamicList#size()
	 */
	@Override
	public int size() 
	{
		return net.links();
	}

}
