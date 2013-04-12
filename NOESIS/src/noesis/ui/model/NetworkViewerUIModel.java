package noesis.ui.model;

import ikor.model.ui.Application;
import ikor.model.ui.Image;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.ui.model.actions.ExitAction;

public class NetworkViewerUIModel  extends UIModel
{
	private NetworkModel  model;
	private NetworkFigure figure;
	
	// Constructor

	public NetworkViewerUIModel (Application app)
	{
		super(app,"NOESIS Network Viewer");
		
		// setAlignment( UIModel.Alignment.ADJUST );
		// setAlignment( UIModel.Alignment.LEADING );
		// setAlignment( UIModel.Alignment.TRAILING );
		
		add( new Option("$exit", new ExitAction(app) ) );
		
		add( new Image("$icon", app.url("icon.gif") ) );
		
		add( new Image("$background", app.url("logo.gif") ) );
		
		model = new NetworkModel(15);
		figure = new NetworkFigure(model);
		
		
	    add( figure );
		
		add( new NetworkViewerMenu(this));
	}	
	
	// Startup
	
	public void start ()
	{
		figure.hide();
	}
	
	
	// Getters & setters
	
	public NetworkModel getModel() 
	{
		return model;
	}
	
	public void setModel(NetworkModel model) 
	{
		this.model = model;
	}

	public NetworkFigure getFigure() 
	{
		return figure;
	}

	public void setFigure(NetworkFigure figure) 
	{
		this.figure = figure;
	}
	

}
