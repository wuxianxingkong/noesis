package noesis.ui.model;

import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.graphics.colors.JetColorMap;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;
import ikor.model.ui.UIModel;
import noesis.AttributeNetwork;
import noesis.ui.model.actions.LinkAttributeColorAction;
import noesis.ui.model.actions.LinkAttributeWidthAction;

/**
 * Network visualization adjustments based on link attributes.
 * 
 * @author Victor Martinez (fvictor@decsai.ugr.es)
 */

public class LinkAttributesUIModel extends UIModel
{
	NetworkFigure figure;
	Selector      attributes;
	UIModel       buttons;
	
	public LinkAttributesUIModel (Application app, NetworkFigure figure)
	{
		super(app, "Link attributes...");
		
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
		
		Option color = new Option("Adjust link colors");
		color.setIcon( app.url("icon.gif") );
		color.setAction( new LinkAttributeColorAction(app, figure, attributes, logScale, new JetColorMap(256) ) );
		buttons.add(color);
		
		Option width = new Option("Adjust link widths");
		width.setIcon( app.url("icon.gif") );
		width.setAction( new LinkAttributeWidthAction(app, figure, attributes, logScale ) );
		buttons.add(width);
		
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
				
			} else if (network.getLinkAttributeCount()!=attributes.getOptions().size()) {
				
				attributes.clear();
				attributes.setMultipleSelection(false);
				
				for (int i=0; i<network.getLinkAttributeCount(); i++) {
					Option option = new Option(network.getLinkAttribute(i).getID());  // ID vs. Description
					option.setIcon(app.url("icons/kiviat.png"));
					attributes.add(option);
				}
			}
		}
	}	
	
}
