package noesis.ui.model;

import noesis.AttributeNetwork;

import noesis.ui.model.actions.NodeAttributeColorAction;
import noesis.ui.model.actions.NodeAttributePositionAction;
import noesis.ui.model.actions.NodeAttributeSizeAction;

import ikor.model.ui.Application;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;
import ikor.model.ui.UIModel;


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
	
		Option color = new Option("Set node colors");
		color.setIcon( app.url("icon.gif") );
		color.setAction( new NodeAttributeColorAction(app,figure,attributes) );
		buttons.add(color);
		
		Option size = new Option("Set node sizes");
		size.setIcon( app.url("icon.gif") );
		size.setAction( new NodeAttributeSizeAction(app,figure,attributes) );
		buttons.add(size);

		Option x = new Option("Set node X coordinates");
		x.setIcon( app.url("icon.gif") );
		x.setAction( new NodeAttributePositionAction(app,figure,attributes,NodeAttributePositionAction.Axis.X) );
		buttons.add(x);

		Option y = new Option("Set node Y coordinates");
		y.setIcon( app.url("icon.gif") );
		y.setAction( new NodeAttributePositionAction(app,figure,attributes,NodeAttributePositionAction.Axis.Y) );
		buttons.add(y);
	}


	public void start ()
	{
		Application app = getApplication();
		AttributeNetwork network = figure.getNetwork();
		
		attributes.clear();
		attributes.setMultipleSelection(false);
		
		for (int i=0; i<network.getNodeAttributeCount(); i++) {
			Option option = new Option(network.getNodeAttribute(i).getID());  // ID vs. Description
			option.setIcon(app.url("icons/kiviat.png"));
			attributes.add(option);
		}
		
		
	}
	
}
