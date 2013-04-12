package noesis.ui.model.actions;

import noesis.ui.model.NetworkViewerUIModel;
import ikor.model.ui.Action;

public class ViewerCloseAction extends Action 
{
	private NetworkViewerUIModel ui;
	
	public ViewerCloseAction (NetworkViewerUIModel ui)
	{
		this.ui = ui;
	}
		
	@Override
	public void run() 
	{
		ui.getFigure().hide();
	}	
}
