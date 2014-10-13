package noesis.ui.model.actions;

import noesis.Attribute;
import noesis.LinkAttribute;
import noesis.network.attribute.CategoricalLinkAttributeIndexer;
import noesis.network.attribute.NumericalLinkAttributeIndexer;
import ikor.model.data.DataModel;
import ikor.model.data.NumberModel;
import ikor.util.indexer.Indexer;

/**
 * Abstract class for network visualization adjustments based on link attributes.
 * 
 * @author Victor Martinez (fvictor@decsai.ugr.es)
 */

public abstract class LinkAttributeAction extends AttributeAction 
{
	public Indexer createIndexer(Attribute attribute, int size, boolean logScale) 
	{
		Indexer indexer = null;
		DataModel type = attribute.getModel();
		
		if (attribute instanceof LinkAttribute) {
			
			if (type instanceof NumberModel) {  // int, double, decimal...

				indexer = new NumericalLinkAttributeIndexer((LinkAttribute)attribute, size, logScale);

			} else { // string, date...

				indexer = new CategoricalLinkAttributeIndexer((LinkAttribute)attribute, size);
			}
		}
		
		return indexer;
	}
}