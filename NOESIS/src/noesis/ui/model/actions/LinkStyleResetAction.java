package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import noesis.ui.model.NetworkFigure;

public class LinkStyleResetAction extends Action 
{
	private NetworkFigure figure;

	public LinkStyleResetAction (Application application, NetworkFigure figure)
	{
		this.figure = figure;
	}

	@Override
	public void run() 
	{
		if (figure!=null) {
			figure.getRenderer().setLinkRenderer( NetworkFigure.defaultLinkRenderer() );
			figure.render();
		}
	}				
}	
