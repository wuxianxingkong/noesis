package noesis.network;

import noesis.Attribute;

public class FilteredAttribute<T> extends Attribute<T> 
{
	private Attribute<T>    original;
	private FilteredNetwork net;

	public FilteredAttribute (FilteredNetwork net, Attribute<T> original) 
	{
		super (original.getID());
		
		this.original = original;
		this.net = net;
	}

	/* (non-Javadoc)
	 * @see ikor.collection.DynamicList#get(int)
	 */
	@Override
	public T get(int index) 
	{
		return original.get( net.get(index) );
	}

	/* (non-Javadoc)
	 * @see ikor.collection.DynamicList#size()
	 */
	@Override
	public int size() 
	{
		return net.nodes();
	}
		

}
