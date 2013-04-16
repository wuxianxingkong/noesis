package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;

import noesis.AttributeNetwork;

import noesis.algorithms.visualization.NetworkLayout;

import noesis.ui.model.NetworkFigure;


public class LayoutAction extends Action 
{
	private Application   application;
	private NetworkFigure figure;
	private NetworkLayout algorithm;

	public LayoutAction (Application application, NetworkFigure figure, NetworkLayout algorithm)
	{
		this.application = application;
		this.figure = figure;
		this.algorithm = algorithm;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		
		if (network!=null) {
			algorithm.layout(network);
			figure.render();
		}
	}			
	
}	
