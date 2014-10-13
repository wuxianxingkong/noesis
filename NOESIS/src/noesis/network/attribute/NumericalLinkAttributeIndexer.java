package noesis.network.attribute;

import noesis.LinkAttribute;

/**
 * Indexer for numeric attributes
 */
public class NumericalLinkAttributeIndexer extends NumericalAttributeIndexer<Long>
{
	
	public NumericalLinkAttributeIndexer (LinkAttribute attribute, int size)
	{
		super(attribute,size);
	}
	
	public NumericalLinkAttributeIndexer (LinkAttribute attribute, int size, boolean logScale)
	{
		super(attribute,size,logScale);
	}

	@Override
	public int index (Long linkKey) 
	{
		int source = (int) (linkKey>>32);
		int target = (int) (linkKey&0x0000FFFF);
		LinkAttribute attribute = (LinkAttribute) getAttribute();
		Number value = (Number) attribute.get(source, target);
		
		if (value!=null)
			return getValueIndex().index(value.doubleValue());
		else
			return 0;
	}

}
