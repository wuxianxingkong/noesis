package noesis.ui.model.actions;

import ikor.model.ui.Action;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.NetworkLayout;
import noesis.ui.model.NetworkViewerUIModel;


public class LayoutAction extends Action 
{
	private NetworkViewerUIModel ui;
	private NetworkLayout algorithm;

	public LayoutAction (NetworkViewerUIModel ui, NetworkLayout algorithm)
	{
		this.ui = ui;
		this.algorithm = algorithm;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) ui.get("network");
		
		if (network!=null) {
			algorithm.layout(network);
			ui.getFigure().render();
		}
	}			
	
}	
