package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;

import noesis.AttributeNetwork;
import noesis.ui.model.NetworkFigure;

public class NodeAttributePositionAction extends Action 
{
	private Application   application;
	private NetworkFigure figure;
	private Selector      attributes;
	private Axis          axis;

	public enum Axis { X, Y };
	
	public NodeAttributePositionAction (Application application, NetworkFigure figure, Selector attributes, Axis axis)
	{
		this.application = application;
		this.figure = figure;
		this.attributes = attributes;
		this.axis = axis;
	}
	
	public String getSelectedAttributeID ()
	{
		Option option = attributes.getSelectedOption();
		
		if (option!=null)
			return option.getId();
		else
			return null;
	}
	

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		
		if (network!=null) {
		
			// TODO Adjust node position according to selected attribute

			application.message("Adjust node position according to "+getSelectedAttributeID());
			
			if (axis==Axis.X) {
				
			} else { // Axis.Y
				
			}
			
			figure.render();
		}
	}			
	
}	
