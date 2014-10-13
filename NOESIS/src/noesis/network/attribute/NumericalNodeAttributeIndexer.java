package noesis.network.attribute;

import noesis.Attribute;

/**
 * Indexer for numeric attributes
 */
public class NumericalNodeAttributeIndexer extends NumericalAttributeIndexer<Integer>
{
	
	public NumericalNodeAttributeIndexer (Attribute attribute, int size)
	{
		super(attribute,size);
	}
	
	public NumericalNodeAttributeIndexer (Attribute attribute, int size, boolean logScale)
	{
		super(attribute,size,logScale);
	}

	@Override
	public int index (Integer node) 
	{
		Number value = (Number) getAttribute().get(node);
		
		if (value!=null)
			return getValueIndex().index(value.doubleValue());
		else
			return 0;
	}
	
}
