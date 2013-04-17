package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;

import noesis.AttributeNetwork;
import noesis.io.graphics.NodeRenderer;
import noesis.ui.model.NetworkFigure;

public class NodeStyleAction extends Action 
{
	private Application   application;
	private NetworkFigure figure;
	private NodeRenderer  renderer;

	public NodeStyleAction (Application application, NetworkFigure figure, NodeRenderer renderer)
	{
		this.application = application;
		this.figure = figure;
		this.renderer = renderer;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		
		if (network!=null) {
			
			renderer.setSize( figure.getRenderer().getNodeRenderer().getSize() );
			
			figure.getRenderer().setNodeRenderer(renderer);
			
			figure.render();
		}
	}			
	
}	
