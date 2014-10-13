package noesis.network.attribute;

import ikor.util.indexer.DictionaryIndexer;
import ikor.util.indexer.Indexer;

import noesis.Attribute;

/**
 * Indexer for categorical attributes
 */
public abstract class CategoricalAttributeIndexer<T> extends Indexer<T>
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

	protected double factor ()
	{
		double range = valueIndex.max();
		
		if (range>0)
			return max()/range;
		else
			return 0; 
	}
	
	protected Attribute getAttribute() 
	{
		return attribute;
	}
	
	protected int getSize() 
	{
		return size;
	}
	
	protected DictionaryIndexer<String> getValueIndex() 
	{
		return valueIndex;
	}
	
}
