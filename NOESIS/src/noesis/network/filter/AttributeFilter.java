package noesis.network.filter;

import noesis.Attribute;
import noesis.Network;

public class AttributeFilter<T> implements NetworkFilter 
{
	private Network net;
	private Attribute<T> attribute;
	private T value;
	
	public AttributeFilter (Network net, Attribute<T> attribute, T value)
	{
		this.net = net;
		this.attribute = attribute;
		this.value = value;
	}
  
	@Override
	public boolean node (int node) 
	{
		return (attribute.get(node)==value);
	}

	@Override
	public boolean link (int node, int link) 
	{
		int target = net.outLink(node, link);
		
		return (attribute.get(node)==value) && (attribute.get(target)==value);
	}

}
