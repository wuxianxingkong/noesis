package noesis.ui.model.actions;

import ikor.model.ui.Application;
import ikor.model.ui.Selector;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.io.graphics.ColorMap;
import noesis.io.graphics.Indexer;
import noesis.ui.model.NetworkFigure;

public class NodeAttributeColorAction extends NodeAttributeAction 
{
	private Application   application;
	private NetworkFigure figure;
	private Selector      attributes;
	private ColorMap      map;

	public NodeAttributeColorAction (Application application, NetworkFigure figure, Selector attributes, ColorMap map)
	{
		this.application = application;
		this.figure = figure;
		this.attributes = attributes;
		this.map = map;
	}
	

	
	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		
		if (network!=null) {
			
			String id = getSelectedAttributeID(attributes);
			
			if (id==null) {
				
				application.message("Please, choose an attribute to adjust node colors.");
				
			} else {
				
				Attribute attribute = network.getNodeAttribute(id);
				
				Indexer<Integer> indexer = createIndexer(attribute, map.size());

				figure.getRenderer().getNodeRenderer().setColorMap(map);
				figure.getRenderer().getNodeRenderer().setColorIndexer(indexer);

				figure.refresh();
			}
		}
	}
	
}	
