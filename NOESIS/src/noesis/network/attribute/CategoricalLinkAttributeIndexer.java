package noesis.network.attribute;

import noesis.LinkAttribute;

/**
 * Indexer for categorical link attributes
 */
public class CategoricalLinkAttributeIndexer<T> extends CategoricalAttributeIndexer<Long>
{
	
	public CategoricalLinkAttributeIndexer (LinkAttribute attribute, int size)
	{
		super(attribute, size);
	}

	@Override
	public int index (Long linkKey) 
	{
		int source = (int) (linkKey>>32);
		int target = (int) (linkKey&0x0000FFFF);
		LinkAttribute attribute = (LinkAttribute) getAttribute();
		Object value = attribute.get(source, target);
		
		if (value!=null)
			return (int) ( factor() * getValueIndex().index(value.toString()) );
		else
			return 0;
	}

}
