package noesis.network.filter;

import noesis.Attribute;

public class AttributeFilter<T> implements NetworkFilter 
{
	private Attribute<T> attribute;
	private T value;
	
	public AttributeFilter(Attribute<T> attribute, T value)
	{
		this.attribute = attribute;
		this.value = value;
	}
  
	@Override
	public boolean node(int node) 
	{
		return (attribute.get(node)==value);
	}

	@Override
	public boolean link(int source, int destination) 
	{
		return (attribute.get(source)==value) && (attribute.get(destination)==value);
	}

}
