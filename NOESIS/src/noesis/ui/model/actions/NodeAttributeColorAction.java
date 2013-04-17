package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;

import noesis.AttributeNetwork;
import noesis.ui.model.NetworkFigure;

public class NodeAttributeColorAction extends Action 
{
	private Application   application;
	private NetworkFigure figure;
	private Selector      attributes;

	public NodeAttributeColorAction (Application application, NetworkFigure figure, Selector attributes)
	{
		this.application = application;
		this.figure = figure;
		this.attributes = attributes;
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
			
			// TODO Adjust node colors according to attribute
			
			application.message("Adjust node color according to "+ getSelectedAttributeID());
			
			figure.render();
		}
	}			
	
}	
