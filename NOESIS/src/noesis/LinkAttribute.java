package noesis;

/**
 * Network link attribute
 * 
 * @author Fernando Berzal
 *
 * @param <T> Base type
 */

public class LinkAttribute<T> extends Attribute<T> 
{
	AttributeNetwork net;
	
	public LinkAttribute (AttributeNetwork net, String id)
	{
		super(id);
		this.net = net;
	}
	
	private int index (int source, int target)
	{
		return net.index(source,target);
	}
	
	public T get (int source, int target)
	{
	   return get(index(source,target));
	}
	
	public T set (int source, int target, T value)
	{
		return set(index(source,target),value);
	}
	
}
