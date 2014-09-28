package noesis.network.attribute;

import ikor.util.indexer.DictionaryIndexer;
import ikor.util.indexer.Indexer;

import noesis.Attribute;

/**
 * Indexer for categorical attributes
 */
public class CategoricalAttributeIndexer extends Indexer<Integer>
{
	private Attribute  attribute;
	private int size;
	private DictionaryIndexer<String> valueIndex;
	
	public CategoricalAttributeIndexer (Attribute attribute, int size)
	{
		this.attribute = attribute;
		this.size = size;
		
		valueIndex = new DictionaryIndexer<String>();
		
		for (int i=0; i<attribute.size(); i++) {
			Object object = attribute.get(i);
			
			if (object!=null)
				valueIndex.add( object.toString() );
		}
	}
	

	private double factor ()
	{
		double range = valueIndex.max();
		
		if (range>0)
			return max()/range;
		else
			return 0; 
	}
	
	@Override
	public int index (Integer node) 
	{
		Object value = attribute.get(node);
		
		if (value!=null)
			return (int) ( factor() * valueIndex.index(value.toString()) );
		else
			return 0;
	}

	@Override
	public int min() 
	{
		return 0;
	}

	@Override
	public int max() 
	{
		return size-1;
	}
}
