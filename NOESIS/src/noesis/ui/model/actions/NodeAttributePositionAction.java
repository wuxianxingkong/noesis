package noesis.ui.model.actions;

import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Selector;
import ikor.util.indexer.Indexer;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.ui.model.NetworkFigure;
import noesis.algorithms.visualization.NetworkLayout;

public class NodeAttributePositionAction extends NodeAttributeAction 
{
	private Application     application;
	private Selector        attributes;
	private Editor<Boolean> logScale;
	private NetworkFigure   figure;
	private Axis            axis;

	public enum Axis { X, Y };
	public static final int DEFAULT_INDEX_SIZE = 2048;
	
	
	public NodeAttributePositionAction (Application application, NetworkFigure figure, Selector attributes, Editor<Boolean> logScale, Axis axis)
	{
		this.application = application;
		this.figure = figure;
		this.attributes = attributes;
		this.logScale = logScale;
		this.axis = axis;
	}
	
	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		
		if (network!=null) {
		
			String id = getSelectedAttributeID(attributes);
			
			if (id==null) {
				application.message("Please, choose an attribute to adjust node positions.");
			} else {
				
				Attribute attribute = network.getNodeAttribute(id);
				Attribute<Double> coordinate;
				
				Indexer<Integer> indexer = createIndexer(attribute, DEFAULT_INDEX_SIZE, isLogarithmicScale(logScale) );
			
				if (axis==Axis.X) {
					coordinate = network.getNodeAttribute("x");
				} else { // Axis.Y
					coordinate = network.getNodeAttribute("y");
				}
				
				double factor;
				
				if (indexer.range()>0)
					factor = (1.0-2*NetworkLayout.MARGIN)/indexer.range();
				else
					factor = 0;
				
				for (int i=0; i<network.size(); i++)
					coordinate.set(i, NetworkLayout.MARGIN + indexer.index(i)*factor );

				figure.refresh();
			}
		}
	}			
	
	
	
}	
