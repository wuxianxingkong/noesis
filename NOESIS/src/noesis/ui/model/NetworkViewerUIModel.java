package noesis.ui.model;

import ikor.model.Subject;
import ikor.model.ui.Application;
import ikor.model.ui.Image;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.AttributeNetwork;
import noesis.ui.model.actions.ExitAction;

public class NetworkViewerUIModel  extends UIModel
{
	private NetworkModel  model;
	private NetworkFigure figure;
	
	// Constructor

	public NetworkViewerUIModel (Application app)
	{
		super(app,"NOESIS Network Viewer");

		app.addObserver(this);
		
		// setAlignment( UIModel.Alignment.ADJUST );
		// setAlignment( UIModel.Alignment.LEADING );
		// setAlignment( UIModel.Alignment.TRAILING );
		
		add( new Option("$exit", new ExitAction(app) ) );
		
		add( new Image("$icon", app.url("icon.gif") ) );
		
		add( new Image("$background", app.url("logo.gif") ) );
		
		model = new NetworkModel( (AttributeNetwork) app.get("network") );
		
		figure = new NetworkFigure(model);
		
	    add( figure );
		
		add( new NetworkViewerMenu(this));
	}	
	
	// Startup
	
	@Override
	public void start ()
	{
		figure.hide();
	}
	
	// Updates
	
	@Override
	public void update (Subject subject, String key)
	{
		if ((key!=null) && key.equals("network")) {
			
			AttributeNetwork network = (AttributeNetwork) getApplication().get("network");
			
			if (network!=null) {
				model.setNetwork(network);
				figure.render();
				figure.show();
			}
		}
		
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
