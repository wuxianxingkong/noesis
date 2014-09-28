package noesis.network.attribute;

import ikor.util.indexer.Indexer;
import ikor.util.indexer.LogValueIndexer;
import ikor.util.indexer.ValueIndexer;
import noesis.Attribute;

/**
 * Indexer for numeric attributes
 */
public class NumericAttributeIndexer extends Indexer<Integer>
{
	private Attribute       attribute;
	private int             size;
	private Indexer<Double> valueIndex;
	
	public NumericAttributeIndexer (Attribute attribute, int size)
	{
		this(attribute,size,false);
	}
	
	public NumericAttributeIndexer (Attribute attribute, int size, boolean logScale)
	{
		this.attribute = attribute;
		this.size = size;
		
		double min = Double.MAX_VALUE;
		double max = -Double.MIN_VALUE;
		Number object;
		double value;
		
		for (int i=0; i<attribute.size(); i++) {
			
			object = (Number) attribute.get(i);
			
			if (object!=null) {
				
				value = object.doubleValue();
			
				if (value<min)
					min = value;

				if (value>max)
					max = value;
			}
		}
		
		if (logScale)
			valueIndex = new LogValueIndexer(min, max, size-1);
		else
			valueIndex = new ValueIndexer(min, max, size-1);
	}

	@Override
	public int index (Integer node) 
	{
		Number value = (Number) attribute.get(node);
		
		if (value!=null)
			return valueIndex.index(value.doubleValue());
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
