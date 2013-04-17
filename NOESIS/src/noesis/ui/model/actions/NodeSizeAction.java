package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;

import noesis.AttributeNetwork;
import noesis.io.graphics.NodeRenderer;
import noesis.ui.model.NetworkFigure;

public class NodeSizeAction extends Action 
{
	private Application   application;
	private NetworkFigure figure;
	private int           change;

	public NodeSizeAction (Application application, NetworkFigure figure, int change)
	{
		this.application = application;
		this.figure = figure;
		this.change = change;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		NodeRenderer renderer;
		
		if (network!=null) {
			
			renderer = figure.getRenderer().getNodeRenderer();
			
			renderer.setSize( renderer.getSize()+change );
			
			figure.render();
		}
	}			
	
}	
