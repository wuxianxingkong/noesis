package noesis.ui.model.actions;

import ikor.model.ui.Action;

import noesis.ui.model.NetworkViewerUIModel;


public class ViewerStartAction extends Action 
{
	private NetworkViewerUIModel ui;

	public ViewerStartAction (NetworkViewerUIModel ui)
	{
		this.ui = ui;
	}

	@Override
	public void run() 
	{
		ui.getFigure().hide();
	}	
}
