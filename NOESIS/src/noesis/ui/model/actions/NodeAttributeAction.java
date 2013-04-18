package noesis.ui.model.actions;

import noesis.Attribute;
import noesis.io.graphics.DictionaryIndexer;
import noesis.io.graphics.Indexer;
import noesis.io.graphics.ValueIndexer;

import ikor.model.data.DataModel;
import ikor.model.data.NumberModel;
import ikor.model.ui.Action;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;


/**
 * Abstract class for network visualization adjustments based on node attributes.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class NodeAttributeAction extends Action 
{
	

	public String getSelectedAttributeID (Selector attributes) 
	{
		Option option = attributes.getSelectedOption();
		
		if (option!=null)
			return option.getId();
		else
			return null;
	}


	public Indexer<Integer> createIndexer(Attribute attribute, int size) 
	{
		Indexer<Integer> indexer;
		DataModel type = attribute.getModel();
		
		if (type instanceof NumberModel) {  // int, double, decimal...
			
			indexer = new NumericAttributeIndexer(attribute, size);
			
		} else { // string, date...
			
			indexer = new StringAttributeIndexer(attribute, size);
		}
		
		return indexer;
	}

	
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

	/**
	 * Indexer for categorical attributes
	 */
	public class StringAttributeIndexer extends Indexer<Integer>
	{
		private Attribute  attribute;
		private int size;
		private DictionaryIndexer<String> valueIndex;
		
		public StringAttributeIndexer (Attribute attribute, int size)
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
	
}