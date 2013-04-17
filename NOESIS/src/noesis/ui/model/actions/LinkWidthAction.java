package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;

import noesis.AttributeNetwork;
import noesis.io.graphics.LinkRenderer;
import noesis.ui.model.NetworkFigure;

public class LinkWidthAction extends Action 
{
	private Application   application;
	private NetworkFigure figure;
	private int           change;

	public LinkWidthAction (Application application, NetworkFigure figure, int change)
	{
		this.application = application;
		this.figure = figure;
		this.change = change;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		LinkRenderer renderer;
		
		if (network!=null) {
			
			renderer = figure.getRenderer().getLinkRenderer();
			
			renderer.setWidth( renderer.getWidth()+change );
			
			figure.render();
		}
	}			
	
}	
