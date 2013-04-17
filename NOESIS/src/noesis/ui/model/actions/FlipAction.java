package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;

import noesis.AttributeNetwork;
import noesis.Attribute;
import noesis.ui.model.NetworkFigure;

public class FlipAction extends Action 
{
	public enum Mode { HORIZONTAL, VERTICAL };
	
	private Application   application;
	private NetworkFigure figure;
	private Mode          mode;

	public FlipAction (Application application, NetworkFigure figure, Mode mode)
	{
		this.application = application;
		this.figure = figure;
		this.mode = mode;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		Attribute<Double> coordinate;
		
		if (network!=null) {
			
			if (mode==Mode.HORIZONTAL)
				coordinate = network.getNodeAttribute("x");
			else // Mode.VERTICAL
				coordinate = network.getNodeAttribute("y");

			for (int i=0; i<network.size(); i++) {
				coordinate.set(i, 1.0-coordinate.get(i));
			}
						
			figure.render();
		}
	}			
	
}	
