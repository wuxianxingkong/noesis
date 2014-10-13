package noesis.ui.model.actions;

import ikor.model.graphics.colors.ColorMap;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.util.log.Log;
import noesis.AttributeNetwork;
import noesis.io.graphics.ColorMapLinkRenderer;
import noesis.io.graphics.LinkRenderer;
import noesis.ui.model.NetworkFigure;

public class LinkStyleAction extends Action 
{
	private Application   application;
	private NetworkFigure figure;
	private Class         renderer;
	private ColorMap      map;

	public LinkStyleAction (Application application, NetworkFigure figure, Class renderer)
	{
		this.application = application;
		this.figure = figure;
		this.renderer = renderer;
	}

	public LinkStyleAction (Application application, NetworkFigure figure, ColorMap map)
	{
		this.application = application;
		this.figure = figure;
		this.renderer = null;
		this.map = map;
	}
	
	public LinkRenderer instantiateLinkRenderer (Class type)
	{
		LinkRenderer renderer = null;
		
		try {
			renderer = (LinkRenderer) type.newInstance();
		} catch (Exception error) {
			Log.error("Unable to instantiate link renderer from "+type);
			renderer = new ColorMapLinkRenderer();
		}
		
		return renderer;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		
		if (network!=null) {
			
			if (renderer!=null) {
				LinkRenderer linkRenderer = instantiateLinkRenderer(renderer); 
				LinkRenderer figureRenderer = figure.getRenderer().getLinkRenderer();

				linkRenderer.setWidth( figureRenderer.getWidth() );
				linkRenderer.setColorMap( figureRenderer.getColorMap() );
				linkRenderer.setWidthIndexer( figureRenderer.getWidthIndexer());
				linkRenderer.setColorIndexer( figureRenderer.getColorIndexer());

				figure.getRenderer().setLinkRenderer( linkRenderer );
			}
			
			if (map!=null)
				figure.getRenderer().getLinkRenderer().setColorMap(map);
			
			figure.render();
		}
	}			
	
}	
