package noesis.ui.model.actions;

import noesis.Attribute;
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

public abstract class AttributeAction extends Action 
{
	public String getSelectedAttributeID(Selector attributes) 
	{
		Option option = attributes.getSelectedOption();
		
		if (option!=null)
			return option.getId();
		else
			return null;
	}

	public boolean isLogarithmicScale(Editor<Boolean> logScale) 
	{
		Boolean value = logScale.getData();
		 		
		if (value!=null)
			return value;
		else
			return false;
	}
	
	public abstract Indexer createIndexer(Attribute attribute, int size, boolean logScale); 

}