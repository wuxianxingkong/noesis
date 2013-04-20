package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.util.log.Log;

import noesis.AttributeNetwork;
import noesis.io.graphics.NodeRenderer;
import noesis.io.graphics.RadialGradientNodeRenderer;
import noesis.ui.model.NetworkFigure;

public class NodeStyleAction extends Action 
{
	private Application   application;
	private NetworkFigure figure;
	private Class         renderer;

	public NodeStyleAction (Application application, NetworkFigure figure, Class renderer)
	{
		this.application = application;
		this.figure = figure;
		this.renderer = renderer;
	}
	
	public NodeRenderer instantiateNodeRenderer (Class type)
	{
		NodeRenderer renderer = null;
		
		try {
			renderer = (NodeRenderer) type.newInstance();
		} catch (Exception error) {
			Log.error("Unable to instantiate node renderer from "+type);
			renderer = new RadialGradientNodeRenderer();
		}
		
		return renderer;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		
		if (network!=null) {
			
			NodeRenderer nodeRenderer = instantiateNodeRenderer(renderer); 
			
			nodeRenderer.setSize( figure.getRenderer().getNodeRenderer().getSize() );
			
			figure.getRenderer().setNodeRenderer( nodeRenderer );
			
			figure.render();
		}
	}			
	
}	
