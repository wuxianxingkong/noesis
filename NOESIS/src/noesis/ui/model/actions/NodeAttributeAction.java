package noesis.ui.model.actions;

import noesis.Attribute;

import ikor.model.data.DataModel;
import ikor.model.data.NumberModel;
import ikor.model.ui.Action;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;
import ikor.util.indexer.DictionaryIndexer;
import ikor.util.indexer.Indexer;
import ikor.util.indexer.LogValueIndexer;
import ikor.util.indexer.ValueIndexer;


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
	
	public boolean isLogarithmicScale (Editor<Boolean> logScale)
	{
		Boolean value = logScale.getData();
		 		
		if (value!=null)
			return value;
		else
			return false;
	}


	public Indexer<Integer> createIndexer(Attribute attribute, int size, boolean logScale) 
	{
		Indexer<Integer> indexer;
		DataModel type = attribute.getModel();
		
		if (type instanceof NumberModel) {  // int, double, decimal...
			
			indexer = new NumericAttributeIndexer(attribute, size, logScale);
			
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