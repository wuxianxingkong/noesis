package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import noesis.ui.model.NetworkFigure;

public class NodeStyleResetAction extends Action 
{
	private NetworkFigure figure;

	public NodeStyleResetAction (Application application, NetworkFigure figure)
	{
		this.figure = figure;
	}

	@Override
	public void run() 
	{
		if (figure!=null) {
			figure.getRenderer().setNodeRenderer( NetworkFigure.defaultNodeRenderer() );
			figure.render();
		}
	}			
	
}	
