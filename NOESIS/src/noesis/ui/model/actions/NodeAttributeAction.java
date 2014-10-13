package noesis.ui.model.actions;

import noesis.Attribute;
import noesis.network.attribute.CategoricalNodeAttributeIndexer;
import noesis.network.attribute.NumericalNodeAttributeIndexer;
import ikor.model.data.DataModel;
import ikor.model.data.NumberModel;
import ikor.util.indexer.Indexer;

/**
 * Abstract class for network visualization adjustments based on node attributes.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class NodeAttributeAction extends AttributeAction 
{
	public Indexer createIndexer(Attribute attribute, int size, boolean logScale) 
	{
		Indexer indexer;
		DataModel type = attribute.getModel();
		
		if (type instanceof NumberModel) {  // int, double, decimal...
			
			indexer = new NumericalNodeAttributeIndexer(attribute, size, logScale);
			
		} else { // string, date...
			
			indexer = new CategoricalNodeAttributeIndexer(attribute, size);
		}
		
		return indexer;
	}
}