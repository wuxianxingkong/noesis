package noesis.ui.model;

import noesis.AttributeNetwork;

import noesis.ui.model.actions.NodeAttributeColorAction;
import noesis.ui.model.actions.NodeAttributePositionAction;
import noesis.ui.model.actions.NodeAttributeSizeAction;

import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.graphics.colors.JetColorMap;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;
import ikor.model.ui.UIModel;

/**
 * Network visualization adjustments based on node attributes.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class NodeAttributesUIModel extends UIModel
{
	NetworkFigure figure;
	Selector      attributes;
	UIModel       buttons;
	
	public NodeAttributesUIModel (Application app, NetworkFigure figure)
	{
		super(app, "Node attributes...");
		
		setIcon( app.url("icon.gif") );

		this.figure = figure;
		
		// Nested panels
		
		UIModel panel = new UIModel(app, "Container panel");

		panel.setAlignment( UIModel.Alignment.TRAILING );
		
		attributes = new Selector();
		panel.add( attributes );

		buttons = new UIModel(app, "Button bar");
		panel.add( buttons );
		
		this.add(panel);
		
		// Buttons
		
		buttons.setAlignment( UIModel.Alignment.ADJUST );
	
		Editor<Boolean> logScale = new Editor<Boolean>("Logarithmic scale", Boolean.class);
		buttons.add(logScale);
		
		Option color = new Option("Adjust node colors");
		color.setIcon( app.url("icon.gif") );
		color.setAction( new NodeAttributeColorAction(app, figure, attributes, logScale, new JetColorMap(256) ) );
		buttons.add(color);
		
		Option size = new Option("Adjust node sizes");
		size.setIcon( app.url("icon.gif") );
		size.setAction( new NodeAttributeSizeAction(app, figure, attributes, logScale) );
		buttons.add(size);

		Option x = new Option("Adjust X coordinates");
		x.setIcon( app.url("icon.gif") );
		x.setAction( new NodeAttributePositionAction(app, figure, attributes, logScale, NodeAttributePositionAction.Axis.X) );
		buttons.add(x);

		Option y = new Option("Adjust Y coordinates");
		y.setIcon( app.url("icon.gif") );
		y.setAction( new NodeAttributePositionAction(app, figure, attributes, logScale, NodeAttributePositionAction.Axis.Y) );
		buttons.add(y);
		
		// Observer
		
		figure.addObserver( new AttributeObserver(figure,attributes) );
	}


	// Observer design pattern
	
	public class AttributeObserver implements Observer<AttributeNetwork>
	{
		private NetworkFigure figure;
		private Selector attributes;
		
		public AttributeObserver (NetworkFigure figure, Selector control)
		{
			this.figure = figure;
			this.attributes = control;
		}

		@Override
		public void update (Subject subject, AttributeNetwork object) 
		{
			Application app = getApplication();
			AttributeNetwork network = figure.getNetwork();
			
			if (network==null) {
				
				attributes.clear();
				
			} else if (network.getNodeAttributeCount()!=attributes.getOptions().size()) {
				
				attributes.clear();
				attributes.setMultipleSelection(false);
				
				for (int i=0; i<network.getNodeAttributeCount(); i++) {
					Option option = new Option(network.getNodeAttribute(i).getID());  // ID vs. Description
					option.setIcon(app.url("icons/kiviat.png"));
					attributes.add(option);
				}
			}
		}
	}	
	
}
