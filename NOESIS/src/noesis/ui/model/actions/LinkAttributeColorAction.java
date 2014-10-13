package noesis.ui.model.actions;

import ikor.model.graphics.colors.ColorMap;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Selector;
import ikor.util.indexer.Indexer;
import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.ui.model.NetworkFigure;

/**
 * Link color visualization based on link attributes.
 * 
 * @author Victor Martinez (fvictor@decsai.ugr.es)
 */

public class LinkAttributeColorAction extends LinkAttributeAction 
{
	private Application     application;
	private NetworkFigure   figure;
	private Selector        attributes;
	private Editor<Boolean> logScale;
	private ColorMap        map;

	public LinkAttributeColorAction (Application application, NetworkFigure figure, Selector attributes, Editor<Boolean> logScale, ColorMap map)
	{
		this.application = application;
		this.figure = figure;
		this.attributes = attributes;
		
		this.logScale = logScale;
		this.map = map;
	}
		
	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		
		if (network!=null) {
			
			String id = getSelectedAttributeID(attributes);
			
			if (id==null) {
				
				application.message("Please, choose an attribute to adjust link colors.");
				
			} else {
				
				Attribute attribute = network.getLinkAttribute(id);

				Indexer<Long> indexer = createIndexer(attribute, map.size(), isLogarithmicScale(logScale) );

				figure.getRenderer().getLinkRenderer().setColorMap(map);
				figure.getRenderer().getLinkRenderer().setColorIndexer(indexer);

				figure.refresh();
			}
		}
	}
}	
