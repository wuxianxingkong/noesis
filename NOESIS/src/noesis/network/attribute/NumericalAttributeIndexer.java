package noesis.network.attribute;

import ikor.util.indexer.Indexer;
import ikor.util.indexer.LogValueIndexer;
import ikor.util.indexer.ValueIndexer;
import noesis.Attribute;

/**
 * Indexer for numeric attributes
 */
public abstract class NumericalAttributeIndexer<T> extends Indexer<T>
{
	private Attribute       attribute;
	private int             size;
	private Indexer<Double> valueIndex;
	
	public NumericalAttributeIndexer (Attribute attribute, int size)
	{
		this(attribute,size,false);
	}
	
	public NumericalAttributeIndexer (Attribute attribute, int size, boolean logScale)
	{
		this.attribute = attribute;
		this.size = size;
		
		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;
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
	public int min() 
	{
		return 0;
	}

	@Override
	public int max() 
	{
		return size-1;
	}
	
	
	protected Attribute getAttribute() 
	{
		return attribute;
	}
	
	protected int getSize() 
	{
		return size;
	}
	
	protected Indexer<Double> getValueIndex() 
	{
		return valueIndex;
	}
}
