package noesis.ui.model.actions;

import ikor.model.graphics.colors.ColorMap;
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
	private ColorMap      map;

	public NodeStyleAction (Application application, NetworkFigure figure, Class renderer)
	{
		this.application = application;
		this.figure = figure;
		this.renderer = renderer;
	}

	public NodeStyleAction (Application application, NetworkFigure figure, ColorMap map)
	{
		this.application = application;
		this.figure = figure;
		this.renderer = null;
		this.map = map;
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
			
			if (renderer!=null) {

				NodeRenderer nodeRenderer = instantiateNodeRenderer(renderer); 
				NodeRenderer figureRenderer = figure.getRenderer().getNodeRenderer();

				nodeRenderer.setSize( figureRenderer.getSize() );
				nodeRenderer.setColorMap( figureRenderer.getColorMap() );
				nodeRenderer.setSizeIndexer( figureRenderer.getSizeIndexer() );
				nodeRenderer.setColorIndexer( figureRenderer.getColorIndexer() );

				figure.getRenderer().setNodeRenderer( nodeRenderer );
			}
			
			if (map!=null)
				figure.getRenderer().getNodeRenderer().setColorMap(map);
			
			figure.render();
		}
	}			
	
}	
