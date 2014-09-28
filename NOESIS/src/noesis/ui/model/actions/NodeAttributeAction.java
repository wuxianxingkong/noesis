package noesis.ui.model.actions;

import noesis.Attribute;
import noesis.network.attribute.CategoricalAttributeIndexer;
import noesis.network.attribute.NumericAttributeIndexer;
import ikor.model.data.DataModel;
import ikor.model.data.NumberModel;
import ikor.model.ui.Action;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;
import ikor.util.indexer.Indexer;


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
			
			indexer = new CategoricalAttributeIndexer(attribute, size);
		}
		
		return indexer;
	}
}