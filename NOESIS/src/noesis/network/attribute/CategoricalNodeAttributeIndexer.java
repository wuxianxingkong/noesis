package noesis.network.attribute;

import noesis.Attribute;

/**
 * Indexer for categorical attributes
 */
public class CategoricalNodeAttributeIndexer extends CategoricalAttributeIndexer<Integer>
{
	
	public CategoricalNodeAttributeIndexer (Attribute attribute, int size)
	{
		super(attribute, size);
	}
	
	@Override
	public int index (Integer node) 
	{
		Object value = getAttribute().get(node);
		
		if (value!=null)
			return (int) ( factor() * getValueIndex().index(value.toString()) );
		else
			return 0;
	}
	
}
